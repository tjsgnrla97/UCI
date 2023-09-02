<template>
  <div>
    <!-- ---------------navbar 영역--------------- -->
    <meeting-navbar 
      @leave-button="videoLeave('first')"
      @room-detail="roomDetail(mySessionId)"
    />
    <!-- ----------------------------------------- -->
    <div
      id="session"
    >
      <div id="session-header">
        <h1 id="session-title">
          {{ mySessionId }}
        </h1>
        <!-- <div>
          <v-btn
            id="buttonLeaveSession"
            color="error"
            @click="applyFilter"
          >
            <v-icon>mdi-power</v-icon>
            필터 적용
          </v-btn>
          <v-btn>
            <v-icon>mdi-information</v-icon>
            방 상세정보				
          </v-btn>
        </div> -->
      </div>
    </div>
    <div>
      <v-row
        mt-5
        justify="center"
      >
        <!-- --------게임 선택 바--------- -->
        <v-col
          id="test"
          cols="2"
        >
          <v-row>
            <profile-card />
          </v-row>
          <v-row
            class="mt-10"
          >
            <v-btn
              rounded-pill
              align="center"
              class="dong"
              width="300"
              x-large
              dark
              @click="[send('game','changeDescribe','dongSang')]"
            >
              일심동체💁🏻‍♀️
            </v-btn>
          </v-row>
        </v-col>
        <!-- ---------------------------- -->
        <!-- -------비디오 영역-------- -->

        <v-col
          id="video-container"
          cols="8"
          class="d-flex flex-wrap justify-content-center align-items-center mx-auto grey lighten-2"
        >
          <user-video
            name="user-video"
            class="mx-2"
            :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)"
          />
          <user-video
            v-for="sub in subscribers"
            id="other-screen"
            :key="sub.stream.connection.connectionId"
            name="user-video"
            class="mx-2"
            :stream-manager="sub" 
            @click.native="updateMainVideoStreamManager(sub)"
          />
          <v-col
            cols="12"
            align="center"
            class="align-self-end"
          >
            <v-btn-toggle
              
              dark
              multiple
              large
            >
              <v-btn
                dark
                large
                class="buttons" 
                @click="audioMute"
              >
                오디오              
                <v-icon
                  v-if="audioEnabled === true"
                  size="30"               
                >
                  mdi-microphone
                </v-icon>
                <v-icon
                  v-else
                  size="30"
                >
                  mdi-microphone-off
                </v-icon>  
              </v-btn>

              <v-btn
                large
                dark
                class="buttons"
                @click="videoMute" 
              >
                카메라
                <v-icon
                  v-if="videoEnabled === true"
                  size="35"
                >
                  mdi-video
                </v-icon>
                <v-icon
                  v-else
                  size="35"
                >
                  mdi-video-off
                </v-icon>
              </v-btn>

              <!-- <v-btn
                class="buttons"
                large
              >
                <v-icon
                  size="30"
                >
                  mdi-forum
                </v-icon>|
                <v-icon
                  size="35"
                >
                  mdi-accounts
                </v-icon>
              </v-btn> 
              <v-btn
                class="buttons"
                large
              >
                <v-icon>mdi-cog</v-icon>
              </v-btn> -->
            </v-btn-toggle>
          </v-col>
        </v-col>
        <!-- ---------------------------- -->
        <!-- -------- 채팅 박스 영역 --------- -->

        <v-col
          id="chat-box"
          cols="2"
        >
          <div>
            <video-chat
              :session-info="toChatInfo"
              @send-message="send"
            />
          </div>
        </v-col>
        <!-- <v-col
          align="center"
        >
          <video-footer />
        </v-col>           -->

        <!-- ---------------------------- -->

        <v-col
          v-show="rule"
          id="rule"
          cols="8"
          class="justify-center text-center mt-5 rounded-2"
        >
          <pre id="describe">
            {{ describe }}
          </pre>
          <v-btn
            v-show="isHost"
            id="start-button"
            x-large
            dark
            class="start_btn"
            @click="[send('game','gameStart',quizWord),changeActive(mySessionId),removeFilter()]"
          >
            START
          </v-btn>
        </v-col>
          
        <v-col
          v-show="!rule"
          id="dongSangWord"
          cols="8"
          offset="2"
          class="justify-center text-center mt-1 rounded-2"
        >
          {{ topicWord }}
        </v-col>
      </v-row>
    </div>

    <!-- -----------------투표 영역---------------- -->
    
    <div id="modal-back">
      <v-row
        id="vote-modal"
      >
        <v-col
          id="vote-picture"
          cols="9"
        />
        <v-col
          id="vote-buttons"
          cols="3"
          justify-center
        >
          <p id="vote-mention">
            자세가 가장 다른 사람을 골라 투표하세요!
          </p>  
          <v-container
            class="px-0"
            fluid
          >
            <v-radio-group>
              <v-radio
                v-for="n in players"
                :key="n"
                :label="`${n}`"
                :value="n"
              />
            </v-radio-group>

            <v-btn
              color="green darken-1"
              text
              @click="completVote"
            >
              Submit
            </v-btn>
          </v-container>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import UserVideo from '@/components/videolist/UserVideo.vue';
import VideoChat from '@/components/VideoChat.vue'
import MeetingNavbar from '@/components/MeetingPage/MeetingNavBar.vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import html2canvas from 'html2canvas'
import { mapGetters, mapActions } from "vuex"
// import VideoFooter from '@/components/videolist/VideoFooter.vue'
import ProfileCard from '@/components/ProfileCard.vue' 
export default {
	name: 'VideoView',

	components: {
		UserVideo,
    VideoChat,
    MeetingNavbar,
    ProfileCard
    // VideoFooter
	},
	props: {
		videoCredentials : Object
	},
	data () {
		return {
			gameTitle:'',
      message:'',
      messageList: [],
      describe:'UCI에 오신 것을 환영합니다! \n  좌측 게임 선택박스에서 게임을 선택하여 진행하세요!',
      afterGameDescribe: '게임이 끝났습니다. \n 좌측 게임 선택박스에서 다시 게임을 선택해주세요!',
      serverUrl: "http://localhost:8080/socket",
      rule: true,
      time: 5,
      voteModal: false,
      voteList: [],
      videoEnabled: true,
      audioEnabled: true,
      players:[],
      words:["산","야구","하트","빵야빵야"],
      topicWord:'',
		}
	},
	computed: {
    ...mapGetters(['dongSangDescribe','isHost','getRoomsData']),
		OV () { 
			return this.videoCredentials.OV 
		},
		session () {
			return this.videoCredentials.session
		},
		mainStreamManager () {
			return this.videoCredentials.mainStreamManager
		},
		publisher () {
			return this.videoCredentials.publisher
		},
		subscribers () {
			return this.videoCredentials.subscribers
		},
		mySessionId () {
			return this.videoCredentials.mySessionId
		},
		myUserName () {
			return this.videoCredentials.myUserName
		},
    toChatInfo () {
      return { "sessionId" : this.mySessionId, "userName" : this.myUserName, "message" : this.messageList }
    },
    toCheckHostInfo () {
      return { "sessionId" : this.mySessionId, "userName" : this.myUserName }
    },
    thisRoomDB () {
        let rooms = this.getRoomsData.data
        const data = rooms.filter(e => e.title === this.roomInfo.sessionId)
      return data[0]
    },
    quizWord () {
      return this.words[Math.floor(Math.random() * this.words.length)];
    }
	},
  created () {
      this.loadRoomData()
      this.connect()
      this.rule = true
    },
	methods: {
    ...mapActions(['getRoomInfo','getRoomSeq','deleteRoomData','resignHost','changeActive','loadRoomData']),
    countPlayers () {
      let playerName = []
      this.getRoomInfo(this.mySessionId).then(function (res) {
        let player = res.connections.content
        for (let i=0; i<player.length; i++) {
          playerName.push(JSON.parse(player[i].clientData).clientData)
        }
      })
      this.players = playerName
    },

		videoLeave (event) {
      if (this.isHost === true && event == 'first') {
        this.send('video','hostLeave',this.mySessionId)
      } else {
        this.send('video','leaveSession',this.videoCredentials.session.connection.connectionId)
        this.$emit('video-leave')
      }
    },
		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},
    audioMute () {
      this.audioEnabled = !this.audioEnabled
      this.publisher.publishAudio(this.audioEnabled);
    },
    videoMute () {
      this.videoEnabled = !this.videoEnabled
      this.publisher.publishVideo(this.videoEnabled);
    },
    send(type,message,userName) {
      console.log("Send message:" + message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          type: type,
          userNickname: userName,
          content: message, 
        };
        this.stompClient.send(`/receive/${this.mySessionId}`, JSON.stringify(msg), {});
      }
    },      
    connect() {
      window.addEventListener("beforeunload",() => this.videoLeave("first"), { once: true })

      const serverURL = this.serverUrl
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          // this.getMessage()
          this.getMessage()
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );  
    },
    getMessage () {
      this.stompClient.subscribe(`/send/${this.mySessionId}`, res => {
            console.log(res)
            console.log('구독으로 받은 메시지 입니다.', res.body);
            let result = JSON.parse(res.body)
            if (result.type === "chat") {
              console.log("채팅입니다.")
              // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
              this.messageList.push(result)
            } else if (result.type === "video" && result.content === "hostLeave") {

              this.$store.dispatch('updateAlert', {
                    alert: true,
                    type: 'error',
                    title: '미팅이 종료 되었습니다.',
                    text: '미팅이 종료 되었습니다.',
                  })
              if (this.isHost === true) {
                this.getRoomSeq(result.userNickname)
                .then((res) => this.deleteRoomData(res))
                .then(() => this.videoLeave("second"))
              } else {
                this.videoLeave("second")
              }             

              // 미팅의 종료 조건 == 방장이 나가야함 == 모두가 나가야함
              // 한번만 실행시켜야됨.
            } else if (result.type === "video" && result.content === "leaveSession") {
              let sub = this.subscribers
              // window.removeEventListener("beforeunload",this.videoLeave("first"))
              for (let i=0; i<sub.length; i++ ) {
                if (sub[i].stream.connection.connectionId === result.userNickname) {
                  sub.splice(i, 1);
                  break;
                }
              }
            } else if (result.type === "game" && result.content === "changeDescribe") {
              this.dongSang()
            } else if (result.type === "game" && result.content === "gameStart") {
              this.topicWord = result.userNickname
              this.countPlayers()
              this.dongSangStart().then(this.dongSangCapture)
            } else if (result.type === "game" && result.content === "voteResult") {
              if (this.voteList.length === this.players.length-1) {
                this.voteList.push(result.userNickname)
                console.log(this.voteList, "함수 시작전")

                function looser (array) {
                  const voteResult = array.reduce((acc, cur) => { 
                    acc[cur] = (acc[cur] || 0) +1
                    return acc }, {});
                  console.log(voteResult,"투표결과")  
                  const keys = Object.keys(voteResult)
                  let mode = keys[0]
                  keys.forEach((key) => {
                    if (voteResult[key] > voteResult[mode]) {
                        mode = key
                        console.log(mode,"mode")
                    }
                  })
                  return mode
                }
                const penalty = looser(this.voteList)
                alert(`${penalty} 님이 벌칙에 당첨되셨습니다!`)
                if (this.myUserName === penalty) {
                  this.applyFilter("Pitch")
                }
                this.finishDongSang()
              } else {
                this.voteList.push(result.userNickname)
              }
            }
          });
    },
    dongSang() {
      this.describe = this.dongSangDescribe
      const startButton = document.querySelector("#start-button")
      startButton.style.visibility = "visible"
    },
    dongSangStart() {
      const myScreen = document.querySelector("#my-screen")
      let otherScreen = document.querySelectorAll("#other-screen video")
      otherScreen.forEach((screen) => {
          screen.style.visibility = "hidden"
      })
      let otherScreenBackGround = document.querySelectorAll("#other-screen")
      this.rule = !this.rule
      otherScreenBackGround.forEach((screen) => {
        screen.style.backgroundColor="black"
        let timer = document.createElement('p')
        timer.setAttribute('class','timer-text')
        screen.appendChild(timer)
      })
      const timers = document.querySelectorAll(".timer-text")
    
      return new Promise(function(resolve,reject) {
        timers.forEach((el) => {
          let time = 5;
          let x = setInterval(() => {
            let sec = time
            el.innerText = sec;
            time--;
          if (time<0) {
            clearInterval(x)
            resolve(1)
          }},1000)
          })
        })  
       },

    dongSangCapture() {
      let otherScreenBackGround = document.querySelectorAll("#other-screen")
      let allScreen = document.querySelectorAll("video")

      otherScreenBackGround.forEach((screen) => {
        screen.style.backgroundColor="#f7f7ee"
        let timer = document.querySelector(".timer-text")
        screen.removeChild(timer)
      })
      for ( const screen of allScreen ) {
        screen.style.visibility = "visible"
      }
      return new Promise(function(resolve,reject){
        setTimeout(()=> {
          console.log("대기끝")
          resolve(1)
      },1000)
      })
      .then(() => { 
        const videosCapture = document.querySelector("#video-container")
        html2canvas(videosCapture).then(canvas => {
          console.log(canvas)
          canvas.setAttribute("id","capture-file")
          const voteBody = document.querySelector("#modal-back")
          const captureBody = document.querySelector("#vote-picture") 
          captureBody.appendChild(canvas)
          voteBody.style.display = "block"
        });
      })},
    completVote () {
      const selected = document.querySelector('.v-item--active div input');
      alert(`${selected.value} 님에게 투표하셨습니다!`);
      this.send("game","voteResult",selected.value)
      const voteBody = document.querySelector("#modal-back")
      const captureBody = document.querySelector("#vote-picture")
      const captureFile = document.querySelector("#capture-file") 
      captureBody.removeChild(captureFile)
      voteBody.style.display = "None"
      },
    finishDongSang() {
      this.rule = !this.rule
      this.describe = this.afterGameDescribe
      const startButton = document.querySelector("#start-button")
      startButton.style.visibility = "hidden"
      const captureFile = document.querySelector("#capture-file") 
      captureFile.remove()
      const timer = document.querySelectorAll(".timer-text")
      timer.remove()
      const sessionId = this.mySessionId
      this.changeActive(sessionId)
      this.voteList = []

    },
    removeFilter() {
      this.publisher.stream.removeFilter()
        .then(() => {
            console.log("Filter removed");
        })
        .catch(error => {
            console.error(error);
        });
    },
    applyFilter(typeName) {
      var filter = { type: '', options: {} };
      // var type = 'Pitch'
      switch (typeName) {
        case 'Grayscale':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "videobalance saturation=0.0" };
          break;
        case 'Rotation':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "videoflip method=vertical-flip" };
          break;
        case 'Faceoverlay':
          filter.type = 'FaceOverlayFilter';
          filter.options = {};
          break;
        case 'Audioecho':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "audioecho delay=40000000 intensity=0.7 feedback=0.4" };
          break;
        case 'Normal':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "pitch pitch=1.0" };
          break;
        case 'Pitch':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "pitch pitch=1.7" };
          break;
        case 'Videobox':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": "videobox fill=black top=-30 bottom=-30 left=-30 right=-30" };
          break;
        case 'Text':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": 'textoverlay text="Embedded text!" valignment=top halignment=right font-desc="Cantarell 25" draw-shadow=false' };
          break;
        case 'Time':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": 'timeoverlay valignment=bottom halignment=right font-desc="Sans, 20"' };
          break;
        case 'Clock':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": 'clockoverlay valignment=bottom halignment=right shaded-background=true font-desc="Sans, 20"' };
          break;
        case 'Chroma':
          filter.type = 'GStreamerFilter';
          filter.options = { "command": 'chromahold target-r=0 target-g=0 target-b=255 tolerance=90' };
          break;
      }
      this.publisher.stream.applyFilter(filter.type, filter.options)
        .then(f => {
          if (f.type === 'FaceOverlayFilter') {
            f.execMethod(
              "setOverlayedImage",
              {
                "uri": "https://cdn.pixabay.com/photo/2017/09/30/09/29/cowboy-hat-2801582_960_720.png",
                "offsetXPercent": "-0.1F",
                "offsetYPercent": "-0.8F",
                "widthPercent": "1.5F",
                "heightPercent": "1.0F"
              });
          }
        });
    }
    }  
}
</script>
<style>
  #session-header{
    margin-bottom: 10px;
  }
	#video-container {
		min-height: 700px;
    border-radius: 10px;
    background-color: rgba(128,128,128, 0.2) !important;
    text-align: center;
    padding: 8% 0% 0% 0%;

	}
	#rule {
		border: 2px solid #81d8cf;
		margin-left: 15%;
		margin-right: 15%;
		background-color: #f7f7ee;

	}
	#test {
		/* width: 100px;
		height: 300px; */
    border-radius: 10px;
		background-color:rgba(129, 216, 207, 0.1);
	}
  #chat-box {
		/* width: 100px;
		height: 300px; */
    height: 700px;
    border-radius: 10px;
		background-color: rgba(247, 247, 238, 1);
	}
  #start-button {
    visibility: hidden;
  }
  .turnOffScreen {
    background-color: black;
  }
  [name = "user-video"] {
    background-color: #f7f7ee;
    border-radius: 15px;
  }
  .timer-text {
    font-size: 250%;
    font-weight: bolder;
    text-align: center;
    color: white;
    z-index: 1;

  }
  #modal-back {
    width: 100%;
    height: 100%;
    display: none;
    position: absolute;
    bottom: 0px;
    background-color: rgba(0, 0, 0, 0.7);
  }
  #vote-modal {
    margin: auto;
    background-color: #f7f7ee;
    width: 70%;
    left: 15%;
    margin-top: 10%;
    border-radius: 2%;
    z-index: 1;
  }
  .buttons {
    font-size: 15px !important;
    background:linear-gradient(to bottom right, rgb(36, 199, 191), rgb(79, 195, 210));
  }
  .dong {
    background:linear-gradient(to bottom right, rgb(36, 199, 191), rgb(79, 195, 210));
    height: 60;
    font-size: 20px  !important;
    margin: 0 auto;
  }
  .start_btn {
    font-size: 20px  !important;
    background:linear-gradient(rgb(235, 35, 98), rgb(240, 96, 142));;
  }
  #dongSangWord {
    margin: auto;
    height: 10%;
    width: 300px;
    height: 150px;
    border: 2px solid #81d8cf;
    background-color: #f7f7ee;
    font-size: 52px;
    box-sizing: content-box;
    text-align: center;
    line-height: 150px;
    font-family: 'Gamja Flower', 'cursive';
  }
  #describe {
		background-color: #f7f7ee;
    font-family: 'Gamja Flower', 'cursive';
    font-size: 32px;
    font-weight: bolder;
  }
  #capture-file {
    max-width:100%;
    max-height:600px;
  }
  #vote-mention {
    text-align: center;
    font-family: 'Gamja Flower', 'cursive';
    font-size: 16px;
    font-weight: bold
  }
  #vote-buttons {
    padding-top: 20%;
  }
</style>
