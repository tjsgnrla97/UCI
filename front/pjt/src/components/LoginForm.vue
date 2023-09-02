<template>
  <v-form
    id="login-form-part"
    @submit.prevent="login(credentials)"
  >
    <v-container>
      <v-row>
        <v-text-field
          v-model="credentials.email"
          label="아이디"
          solo
        />
        <v-text-field
          v-model="credentials.password"
          label="비밀번호"
          type="password"
          solo
        />
        <v-btn
          id="login-btn"
          type="submit" 
          block
        >
          로그인
        </v-btn>
        <a
          id="move-to-signup"
          href="#"
          @click.prevent="moveToSignup"
        >아직 계정이 없으신가요?</a>
        <v-card-actions>
          <v-spacer />
          <v-btn
            color="blue darken-1"
            text
            @click="[closeSignup(),resetAnimation(),resetLoginForm()]"
          >
            닫기
          </v-btn>
        </v-card-actions>        
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name:'LoginForm',

  data () {
    return {
      credentials:{
        email : '',
        password : ''
      }
    }
  },
  methods: {
    ...mapActions(['login','closeSignup']),
    resetAnimation () {
      this.$emit('reset-ani')
      this.$emit('move-previous')
    },
    resetLoginForm () {
      this.credentials.email = '',
      this.credentials.password = ''
    },
    moveToSignup() {
      this.$emit('move-signup')
    }
  }

}
</script>


<style>
#login-form-part {
  margin: 2rem;
  margin-bottom: 0.5rem;
}

#login-form-part > div > div > div:nth-child(1) > div > div.v-input__slot {
  background: #F2EFEF;
}
#login-form-part > div > div > div:nth-child(2) > div > div.v-input__slot {
  background: #F2EFEF;
}

#login-btn {
  background-color: #6AE0C4;
  border: 0px;
  color: #2E6155;
  width: 100% ;
  margin-top: 1rem;
  font-weight: bold;
  margin-top: -10px;  
}

#move-to-signup {
  color: gray;
  font-size: 0.5rem;
  margin: 1rem 3rem 0rem 0rem;
  padding: 0rem !important;
}
</style>