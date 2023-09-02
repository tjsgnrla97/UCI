<template>
  <div>
    <v-container
      v-if="test"
      fluid
    >
      <v-responsive
        max-width="400"
        class="mx-auto mb-4"
      />
      <v-card 
        fluid
        class="mx-auto"
        style="background-color:rgba(0, 128, 128, 0.3)"
      >
        <v-toolbar
          dark
          color="teal lighten-2"
          class="mb-1 rounded-pill"
        >
          <!-- <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-spacer />
            <v-select
              v-model="sortBy"
              flat
              solo-inverted
              hide-details
              label="Sort by"
            />
            <v-spacer />
          </template> -->

          <v-text-field
            v-model="search"
            clearable
            flat
            solo-inverted
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="검색"
          />
        </v-toolbar>
        
        <v-btn 
          id="refresh-button"
          color="#f7f7ee"
          @click="[loadRoomList(),loadRoomData()]"
        >
          <v-icon
            mr-1
          >
            mdi-refresh
          </v-icon>
          방 정보 갱신
        </v-btn>
        <v-row>
          <v-col
            v-for="room in roomfilter"
            :key="room.ID"
            cols="12"
            sm="6"
            md="4"
            lg="3"
          >
            <room-item 
              :room-info="room"
              @remove-product-list="removeProductList"
            />
          </v-col>
        </v-row>

        <v-footer
          color="teal lighten-4"
          dark
        >
          <v-row
            justify="center"
          >
            <v-dialog
              v-model="dialog"
              width="500"
            >
              <template #activator="{ on, attrs }">
                <v-btn
                  v-show="!credentials.session"
                  v-bind="attrs"
                  width="170"
                  class="mb-3 room_btn"
                  v-on="on"                  
                >
                  방 생성하기
                </v-btn>
              </template>
              <v-card>
                <v-card-title style="display: flex; justify-content: center;">
                  <span class="text-h5">방 만들기 🔑</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-text-field
                        v-model="credentials.mySessionId"
                        :counter="20"
                        label="제목"
                        required
                      />
                      <v-text-field
                        v-model="description"
                        :counter="100"
                        label="설명"
                        required
                      />
                      <v-btn
                        class="mr-4 my-4"
                        @click="[checkOverlap(credentials.mySessionId)]"
                      >
                        Join!!
                      </v-btn>      
                    </v-row>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-dialog>
          </v-row>
        </v-footer>
      </v-card>
    </v-container>
    <!-- ------------------------------------------------ -->
    <!-- 비디오 영역과 방 리스트 영역 경계선 -->
    <!-- ------------------------------------------------ -->
    <video-view
      v-if="!test"
      :video-credentials="credentials"
      @video-leave="leaveSession"
    />
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browser';
import RoomItem from '@/components/RoomList/RoomItem.vue'
import axios from "axios"
import VideoView from '../videos/VideoView.vue';
import { mapActions, mapGetters } from 'vuex';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://i7b304.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "UCI";
const OPENVIDUAPP_HEADER = btoa("OPENVIDUAPP:"+`${OPENVIDU_SERVER_SECRET}`);


export default {
  components: {
      RoomItem,
      VideoView,
  },
  data () {
    return {
      credentials : {
        OV: undefined,
        session: undefined,
        mainStreamManager: undefined,
        publisher: undefined,
        subscribers: [],
        mySessionId: '',
        myUserName: JSON.parse(localStorage.newCredentials).username,
        sessionHeader: { Authorization : `Basic ${(OPENVIDUAPP_HEADER)}` },
      },     
      page:1,
      test:true,
      dialog:false,
      roomList: [],
      search:'',
      description: ''
		}},

  computed: {
    ...mapGetters(['getRoomsData','isLoggedIn']),
    roomDBdata () {
      return {
        hostNickname: this.credentials.myUserName,
        title: this.credentials.mySessionId,
        description: this.description,
        active: false,
        joinNumber: 1,
      }
    },
    roomfilter() {
      return this.roomList.filter(room => room.customSessionId.toLowerCase().includes(this.search.toLowerCase()))
    }
  },

  created () {
    setTimeout(() => this.loadRoomList(), 2000)
    setTimeout(() => this.loadRoomData(), 4000)
    setTimeout(() => this.resignHost(), 5000)
  },

  methods: {
    ...mapActions(["assignHost","resignHost","createRoomData","loadRoomData", "getRoomInfo", "getAndDeleteRoom", "deleteRoomData"]),
    changeModal () {
			this.dialog = !this.dialog
      this.test = !this.test
		},
    removeProductList (mySessionId) {
      this.test = !this.test
      this.credentials.mySessionId = mySessionId
      this.joinSession()
    },
    loadRoomList () {
      axios({
        url:`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/`,
        method: 'get',
        headers: this.credentials.sessionHeader								
      }).then(res => {
        this.roomList = res.data.content
        console.log(this.roomList)
      } ).catch(err => {
        console.log(err)
      })
    },
    checkOverlap (sessionId) {
      const rooms = this.roomList
      
      for (let i=0; i<rooms.length; i++) {
        if (rooms[i].sessionId === sessionId) {
          return alert("동일한 방 제목이 있습니다. 다른 이름을 사용해주세요")          
        }}
      this.assignHost()
      this.joinSession()
      this.createRoomData(this.roomDBdata)
      this.changeModal()
        
    },
    joinSession () {
			// --- Get an OpenVidu object ---
			this.credentials.OV = new OpenVidu();

			// --- Init a session ---
			this.credentials.session = this.credentials.OV.initSession();

      // On every new Stream received...
			this.credentials.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.credentials.session.subscribe(stream);
				this.credentials.subscribers.push(subscriber);
				console.log("새로온 사람의 정보",subscriber)
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.credentials.mySessionId).then(token => {
				this.credentials.session.connect(token, { clientData: this.credentials.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.credentials.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: false,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: false,  	// Whether you want to start publishing with your video enabled or not
							resolution: '300x150',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false,       	// Whether to mirror your local video or not 
						});

						this.credentials.mainStreamManager = publisher;
						this.credentials.publisher = publisher;

						// --- Publish your stream ---

						this.credentials.session.publish(this.credentials.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});
		},
    getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},
    

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
      var connectionProperties = {
        kurentoOptions: {
            allowedFilters: ["GStreamerFilter", "FaceOverlayFilter"]
        }
      }
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, JSON.stringify(connectionProperties), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
    leaveSession () {
      // this.getRoomInfo(this.credentials.mySessionId).then(this.getAndDeleteRoom)
      // .then(this.deleteRoomData).then(function () {

        // --- Leave the session by calling 'disconnect' method over the Session object ---
        if (this.credentials.session) this.credentials.session.disconnect();
        this.credentials.session = undefined;
        this.credentials.mainStreamManager = undefined;
        this.credentials.publisher = undefined;
        this.credentials.subscribers = [];
        this.credentials.OV = undefined;
        this.test = !this.test
        window.removeEventListener('beforeunload');
        this.$router.go()
      },
		// )},
    
	}
}

</script>

<style>
  .card {
    border: 4px dashed #bcbcbc !important;
  }
  .btn2 {
    background-color: rgb(213, 219, 218)!important;
  }
  .room_btn {
    font-size: 15px !important;
    background:linear-gradient(rgb(228, 74, 123), rgb(240, 96, 142));;
  }

  #refresh-button {
    margin-top: 16px;
    margin-bottom: 12px;
    margin-left: 4px;
  }

</style>
