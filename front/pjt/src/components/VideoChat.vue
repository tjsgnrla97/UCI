<template>
  <div>
    <div
      class="chat"
    >
      <div 
        class="chat_header"
      />

      <div
        id="chat_b"
        class="chat_b"
      >
        <div
          v-for="(item, idx) in recvList"
          :key="idx"
        >
          <div
            v-if="item.userNickname === newCredentials.username"
            class="my_msg"
          >
            <p 
              class="msg_time"
            >
              {{ now }}
            </p>
            <p class="my_msg_content">
              {{ item.content }}
            </p>
          </div>
          <div
            v-else
          >
            <p class="user_name">
              {{ item.userNickname }}님의 메시지
            </p>
            <div class="user_msg_box">
              <p class="user_msg_content">
                {{ item.content }}
              </p>
              <p class="msg_time">
                {{ now }}
              </p>
            </div>
          </div>
        </div>
      </div>
      <div 
        class="input-div"
      >
        <v-text-field
          v-model="message"
          solo
          append-icon="mdi-send"
          placeholder="메시지를 입력하세요."
          type="text"
          color="teal"
          @keyup="sendMessage"
        >
          <button
            class="input-button"
            @click="sendMessage"
          >
            전송
          </button>
        </v-text-field>
      </div>
    </div>
  </div>
</template>

<script>
// import { mapState } from 'vuex'
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client'

export default {
  name:"VideoChat",
  props: {
    sessionInfo: Object
  },
  data() {
    return {
      message: "",
      type: "",
      newCredentials: {
        email: JSON.parse(localStorage.newCredentials).email,
        username: JSON.parse(localStorage.newCredentials).username,
        department: JSON.parse(localStorage.newCredentials).department,
        position: JSON.parse(localStorage.newCredentials).position,
      },
    }
  },
  computed: {
    // ...mapState(['newCredentials']),
    recvList () {
      return this.sessionInfo.message
    },
    userName () {
      return this.sessionInfo.userName
    },
    sessionId () {
      return this.sessionInfo.sessionId
    },
    now () {
      let today = new Date()
      let hours = today.getHours();
      let minutes = today.getMinutes();
      let seconds = today.getSeconds();

      return hours+":"+minutes+":"+seconds
    },
    stompClient () {
      return this.sessionInfo.stompClient
    },},
        
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // this.getMessage()
    
  },
   mounted() {
    this.$socket.on("chat", (data) => {
      this.pushMsgData(data);
      setTimeout(() => {
        const element = document.getElementById("chat__body");
        element.scrollTop = element.scrollHeight;
      }, 0);
    });
   },
  methods: {
    // newc() {
    //   console.log('newCredentials')
    // },
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.type = "chat"
        this.$emit('send-message',this.type,this.message,this.userName)
        // this.send()
        this.message = ''
      }
      setTimeout(() => {
        const element = document.getElementById("chat__body");
        element.scrollTop = element.scrollHeight;
      }, 0);
    },
  }
}
</script>

<style>
  *{margin: 0; padding: 0;}
  #chat-box { 
    display: flex; 
    flex-direction: column; 
    height: 550px;
      /* height: 100%; */
    overflow: auto;
    }
  .chat_header{

    background:linear-gradient(to bottom right, rgb(36, 199, 191), rgb(79, 195, 210));
    text-align: center;
    box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.05);
    border-radius: 24px 24px 0px 0px;
    padding: 1.8rem;
    font-size: 18px;
    color: gray;
    font-weight: 700;
    }
  .input-div{
    /* position: static; 
    background-color:white; 
    margin: 0px; 

    border-top: 5px solid #81d8cf; */

    position: static;
    margin: 0px;
    bottom: 1px;
    padding: 0px 5px;
    border-radius: 10px;
  
}
.chat {
    display: flex;
    flex-direction: column;
    justify-content: space-between;


  /* margin: 0 5px; */
    }



  .my_msg {
      display: flex;
      justify-content: right;
      align-items: flex-end;
      margin: 0;
      min-height: 40px;
      line-break: anywhere;
  }
  .my_msg_content {
      margin: 0.4rem 0 0 1rem;
      border-radius: 20px 20px 0px 20px;
      max-width: 180px;
      background-color: #81d8cf ;
      color: #ffffff;
      padding: 0.8rem;
      font-size: 14px;
  }
  .chat__first {
      margin-top: 2rem;
  }
  .user_msg {
      display: flex;
  }

  .user_name {
      font-size: 14px;
      font-weight: 700;
      color: #292929;
      margin-top: 0;
      margin-block-end: 0rem;
  }
  .user_msg_box {
    display: flex;
    align-items: flex-end;
    line-break: anywhere;
  }
  .user_msg_content {
    margin: 0.4rem 1rem 0 0;
    border-radius: 0px 20px 20px 20px;
    background-color: #f3f3f3;
    max-width: 180px;
    color: #414141;
    padding: 0.8rem;
    font-size: 14px;
  }
  .msg_time {
    margin: 0;
    font-size: 12px;
    color: #9c9c9c;
  }
  .chat_b {
    overflow: auto;
    scroll-behavior: smooth;
    background: rgb(255, 255, 255, 0.5);
    min-height: 540px;
    max-height: 540px;
  }
  .chat_b::-webkit-scrollbar {
    display: none;
  }
</style>



