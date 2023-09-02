<template>
  <v-row>
    <v-dialog
      v-model="signupDialog"
      persistent
      max-width="1000px"
      style="position:fixed;"
    > 
      <v-card id="login-signup-card">
        <v-row>
          <div
            id="cover-page"
          >
            <v-col class="left-side">
              <span class="box login-box3" />
              <span class="box login-box1" />
              <span class="box login-box2" />
              <img
                src="@/assets/image/logo.png"
                alt=""
                class="logo"
              >
            </v-col>
          </div>
          <v-col id="signup-area">
            <p class="login-title">
              회원가입
            </p>
            <v-card-text>
              <v-form
                v-show="signupPage_one"
                v-model="valid"
                @submit.prevent="moveToNextPage"
              >
                <v-container>
                  <v-row>
                    <v-text-field
                      v-model="credentials.email"
                      :rules="idRules"
                      :counter="20"
                      label="사용할 아이디"
                      required
                      class="signup"
                    />
                    <v-text-field
                      v-model="credentials.password"
                      type="password"
                      :rules="pwRules"
                      :counter="12"
                      label="사용할 비밀번호"
                      required
                      class="signup"
                    />
                    <v-text-field
                      v-model="credentials.password2"
                      type="password"
                      :rules="pw2Rules"
                      label="비밀번호 확인"
                      required
                      class="signup"
                    />
                    <a
                      id="move-to-login"
                      href="#"
                      @click.prevent="moveToLogin"
                    >이미 계정이 있다면 바로 로그인하세요!</a>
                    <v-btn
                      class="mr-4 my-4"
                      type="submit"
                      :disabled="!valid"
                    >
                      다음
                    </v-btn>                
                  </v-row>
                </v-container>
              </v-form>
              <v-form
                v-show="signupPage_two"
                v-model="valid"
                @submit.prevent="[signup(credentials),moveToLogin(),moveToPreviousPage(),resetSignUpForm()]"
              >
                <v-container>
                  <v-row>
                    <v-text-field
                      v-model="credentials.username"
                      :rules="nameRules"
                      :counter="10"
                      label="이름"
                      required
                      class="signup"
                    />

                    <v-text-field
                      v-model="credentials.nickname"
                      :rules="nicknameRules"
                      :counter="10"
                      label="닉네임"
                      required
                      class="signup"
                    />
                    <v-text-field
                      v-model="credentials.department"
                      :rules="nicknameRules"
                      :counter="10"
                      label="부서"
                      required
                      class="signup"
                    />
                    <v-text-field
                      v-model="credentials.position"
                      :rules="nicknameRules"
                      :counter="10"
                      label="직책"
                      required
                      class="signup"
                    />
                    <a
                      id="move-to-login"
                      href="#"
                      @click.prevent="moveToLogin"
                    >이미 계정이 있다면 바로 로그인하세요!</a>
                    <v-row>
                      <v-col class="d-flex justify-content-center">
                        <v-btn
                          class="signup-btn"
                          color="error"
                          :disabled="!valid"
                          @click.prevent="moveToPreviousPage"
                        >
                          뒤로
                        </v-btn>
                        <v-btn
                          class="signup-btn"
                          type="submit"
                          :disabled="!valid"
                          :color="primary"
                        >
                          가입
                        </v-btn>
                      </v-col>              
                    </v-row>  
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn
                color="blue darken-1"
                text
                @click="[closeSignup(),moveToLogin(),moveToPreviousPage(),resetSignUpForm()]"
              >
                닫기
              </v-btn>
            </v-card-actions>
          </v-col>
          <v-col
            id="login-area"
            class="align-center"
          >
            <v-card-text id="login-card">
              <v-container>
                <v-row>
                  <v-col>
                    <p class="login-title">
                      로그인
                    </p>
                    <login-form
                      @reset-ani="resetAnimation"
                      @move-previous="moveToPreviousPage"
                      @move-signup="moveToSignup"
                    />
                    <!-- <a
                      id="move-to-signup"
                      href="#"
                      @click.prevent="moveToSignup"
                    >아직 계정이 없으신가요?</a>
                    <v-card-actions>
                      <v-spacer />
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="[closeSignup(),resetAnimation(),moveToPreviousPage(),resetLoginForm()]"
                      >
                        Close
                      </v-btn>
                    </v-card-actions>   -->
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
          </v-col>
        </v-row>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import LoginForm from "@/components/LoginForm.vue"


export default {
  components: {
    LoginForm
  },
  data () {
    return {
      credentials:{
        email: '',
        password: '',
        username: '',
        nickname: '',
        department: '',
        position: '',
      },

      signupPage_one: true,
      signupPage_two: false,
      valid: false,
      idRules: [
        v => !!v || '필수 항목입니다.',
        v => v.length <= 20 || '아이디는 20자를 초과할 수 없습니다.',
      ],
      pwRules: [
        v => !!v || '필수 항목입니다.',
        v => v.length <= 12 || '비밀번호는 12자를 초과할 수 없습니다.',
      ],             
      pw2Rules: [
        v => v === this.credentials.password || '비밀번호가 서로 다릅니다.',
      ], 
      nameRules: [
        v => !!v || '필수 항목입니다.',
        v => v.length <= 10 || '이름은 10자를 초과할 수 없습니다.',
      ],
      nicknameRules: [
        v => !!v || '필수 항목입니다.',
        v => v.length <= 10 || '닉네임은 10자를 초과할 수 없습니다.',
      ],                       
    }
  },
  computed: {
    ...mapGetters(['signupDialog']),
  },
  methods: {
    ...mapActions(['signup', 'closeSignup']),
    resetSignUpForm () {
      this.credentials.email= '',
      this.credentials.password= '',
      this.credentials.password2= '',
      this.credentials.username= '',
      this.credentials.nickname= '',
      this.credentials.department= '',
      this.credentials.position= ''
    },

    moveToNextPage () {
      this.signupPage_one = false
      this.signupPage_two = true
      let leftSide = document.querySelector("#cover-page")
    },
    moveToPreviousPage () {
      this.signupPage_one = true
      this.signupPage_two = false
    },
    moveToSignup () {
      let leftSide = document.querySelector("#cover-page");
      leftSide.style.animation = "slide-to-signup 0.5s ease-out forwards";
      console.log(leftSide)
    },
    moveToLogin () {
      let leftSide = document.querySelector("#cover-page");
      leftSide.style.animation = "slide-to-login 0.5s ease-out forwards";
    },
    resetAnimation () {
      let leftSide = document.querySelector("#cover-page");
      leftSide.style.animation = "";
    },
  },
}
</script>

<style>
  .left-side {
    background-color: #D8E8E6;
    height: 100%;
    padding: 0px !important;
    position: absolute;
    box-sizing: inherit;

  }
  #login-signup-card {
    overflow-x: hidden;
    overflow-y: hidden;
  }
  .signup {
    margin-bottom: 1rem;
  }
  #cover-page{
    width:50%; 
    height: 100%;
    position: absolute; 
    padding: 0px; 
    z-index: 1;
    object-fit: fill;
  }
  @-webkit-keyframes slide-to-signup {
    from {
      left: 0%;
    }
    to {
      left: 50%;
    }
  }
  @-webkit-keyframes slide-to-login {
    from {
      right: 0%;
    }
    to {
      right: 50%;
    }
  }
  .main-side {
    /* padding: 15% 30%; */
  }
  .modal-body {
    background-color: white;
    padding: 0px !important;
  }
  .modal-content {
    border: 0px !important;
  }
  .box {
    width: 30%;
    padding-bottom: 30%;
    position: absolute;
  }
  .login-box1 {
    left: 100%;
    top: 100%;
    transform: rotate(270deg) translate(100%,-100%);
    background-color: #54F8D1;
    clip-path: polygon(0 66%, 85% 41%, 100% 100%, 0% 100%);
    }
  .login-box2 {
    left: 0%;
    bottom: 0%;
    /* transform: rotate(25de g); */
    background-color: #6AE0C4;
    clip-path: polygon(0 35%, 69% 15%, 89% 100%, 0% 100%);

  }
  .login-box3 {
    /* transform: rotate(180deg); */
    left: 0%;
    background-color: #40CC95;
    clip-path: polygon(0 0, 84% 0, 43% 89%, 0 67%);
  }
  .logo {
    width: 90%;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-55%);
  }
  .login-title {
    text-align: center;
    font-weight: bold;
    color: #47B5AC;
    font-size: 1rem;
    width: 100%;
    margin-top: 10%;

  }
  .login-form-box {
    margin-top: 2rem;
  }
  .close-button {
    text-align: end;
    cursor: pointer;
    margin: 0.5rem;
  }

   #move-to-login {
    color: gray;
    font-size: 0.5rem;
    margin: 0rem 0rem;
  }

  /**/
  #login-card {
    padding-left: 0px;
    padding-bottom: 0px;
  }
  .signup-btn {
    margin: 1rem 2rem;
  }

</style>