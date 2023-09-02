/*
로그인 시
1. 아이디와 비밀번호를 data와 model을 활용하여 받는다.
2. form 전송시 login 함수를 실행시킨다.
3. login 함수는 axios로 서버에 request를 보낸다
4. 응답 받으면 토큰을 로컬스토리지와 state와 각각 save 해준다.
5. 현재 유저의 정보를 받아준다.
6. router를 이용하여 원하는 페이지로 이동시킨다.
*/

import router from '@/router'
import axios from 'axios'
import api from '@/api/apis'

export default {
  state: {
    isLoginModal: false,
    token: localStorage.getItem('token') || '',
    email: localStorage.getItem('email') || '',
    newCredentials: localStorage.getItem('newCredentials') || '',
    currentUser: {},
    profile:{},
    authErr: null,
    signupDialog: false,
    ImageUrl: '',
    myProfileDialog:false,
    alert : false,
        alert_data: {
            type: '',
            title:'',
            text: ''
  },},
    getters: {
    isLoginModal: state => state.isLoginModal,
    isLoggedIn: state => !!state.token,
    getEmail: state => state.email,
    token: state => state.token,
    getCurrentUser: state => state.currentUser,
    profile: state => state.profile,
    authErr: state => state.authErr,
    authHeader: state => ({ Authorization : `${state.token}` }),
    signupDialog: state => state.signupDialog,
    newCredentials: state => state.newCredentials,
    myProfileDialog: state => state.myProfileDialog,
    GET_ALERT(state) {
        return state.alert;
    },
    GET_ALERT_DATA(state) {
        return state.alert_data;
    },

  },

  mutations: {
    OPEN_LOGIN (state) {
      state.isLoginModal = !state.isLoginModal
      console.log(state.isLoginModal)
    },
    CLOSE_LOGIN (state) {
      if (state.isLoginModal) {
      state.isLoginModal = !state.isLoginModal }
      console.log(state.isLoginModal)
    },
    SAVE_TOKEN (state, token) {
      state.token = token
    },
    REMOVE_TOKEN (state) {
      state.token = ''
    },
    SET_CURRENT_USER (state,user) {
      state.currentUser = user
    },
    AUTH_ERR (state, err) {
      state.authErr = err
    },
    OPEN_SIGNUP (state) {
      state.signupDialog = !state.signupDialog
      console.log("무테이션도착")
      console.log(state.signupDialog)
    },
    CLOSE_SIGNUP (state) {
      state.signupDialog = false
    },
    OPEN_MYPROFILE (state) {
      state.myProfileDialog = !state.myProfileDialog
  },
  
    UPDATE_PROFILE(state, credentials) {
      state.currentUser.email = credentials.email
      state.currentUser.userName = credentials.username
      state.currentUser.department = credentials.department
      state.currentUser.position = credentials.position
    },
    UPDATE_IMG(state, payload) {
      if (payload.profileImageUrl != null) {
          state.currentuser.profileImageUrl = payload.profileImageUrl
        }
      },
    AUTH_ERROR (state, err) {
      state.authErr = err
    },
    SET_ALERT(state, payload) {
      state.alert = payload.alert;
      state.alert_data.type = payload.type;
      state.alert_data.title = payload.title;
      state.alert_data.text = payload.text;
    },
  },
  
  actions: {
    openSignup ({commit}) {
      commit('OPEN_SIGNUP')
      console.log("액션도착")
    },
    closeSignup ({commit}) {
      commit('CLOSE_SIGNUP')
    },
    openLoginModal ({commit}) {
      commit('OPEN_LOGIN')
    },
    closeLoginModal ({commit}) {
      commit('CLOSE_LOGIN')
    },
    saveToken ({commit}, token) {
      commit('SAVE_TOKEN', token)
      localStorage.setItem('token', token)
    },
    removeToken ({commit}) {
      commit('REMOVE_TOKEN')
      localStorage.setItem('token', '')
      localStorage.setItem('newCredentials', '')
      localStorage.setItem('email', '')
    },
    openMyprofile ({commit}) {
      commit('OPEN_MYPROFILE')
    },
    updateAlert({commit}, params) {
      commit('SET_ALERT', params);
    },
    // api/v1/user => 현재 로그인해있는 유저의 정보
    fetchCurrentUser ({commit, getters, dispatch},email) {
      if (getters.isLoggedIn) {
        axios({
          url: api.accounts.getUser(email),
          method: 'get',
          headers: getters.authHeader
        })
        .then(res => {
          localStorage.setItem('newCredentials',JSON.stringify(res.data))        
          // commit('SET_CURRENT_USER', res.data)
        })
        .catch(err => {
          console.log(`${this.state.email}`)
          if (err.response.status === 401) {
            dispatch('removeToken')
            router.push({ name: 'login'})
          }
        })
    }
    },

    login ({commit, dispatch}, credentials) {
      axios({
        url: api.accounts.login(),
        method: 'post',
        data: credentials        
      })
      .then (res => {
        const token = res.headers.authorization
        dispatch('saveToken', token)
        localStorage.setItem('email',credentials.email)
        dispatch('fetchCurrentUser', credentials.email)
        dispatch('updateAlert', {alert: true, type: 'success', title: '로그인 성공', text: '로그인 성공'}, {root: true})
        dispatch('closeSignup')
        router.push({ path: 'home'})
        // router.go()
      })
      .catch (err => {
        commit('AUTH_ERROR', err.response.data)
        alert("잘못된 ID 혹은 Password입니다!")
        dispatch('updateAlert', {alert: true, type: 'error', title: '로그인 실패', text: '로그인 실패', color: 'red'}, {root: true})
      })
        
    },     
    signup({ dispatch, commit }, credentials) {
      axios({
        url: api.accounts.signup(),
        method: 'post',
        data: credentials
      })

      .then(() => {      
        dispatch('updateAlert', {alert: true, type: 'success', title: '가입 성공', text: '가입 성공', color: 'orange darken-2'}, {root: true})
        // const loginInfo = {
        //   email: credentials.email,
        //   password: credentials.password
        // }
        // dispatch('login',loginInfo)
      })
      .catch(err => {
        console.error(err.response.data)

        commit('AUTH_ERROR', err.response.data)
        dispatch('updateAlert', {alert: true, type: 'error', title: '가입 실패', text: '가입 실패'}, {root: true})
      })
    },
    logout ({dispatch}) {
      dispatch('removeToken')
      router.push({ name: 'home'})
      router.go();

    },

    // 프로필 페이지 구현

    getProfile() {
      localStorage.setItem('currentUser', state.currentUser)
    },
    updateProfile({ commit, getters }, credentials) {
      const email = localStorage.getItem('email')
      axios({
        url: api.accounts.getUser(email),
        method: 'put',
        data: credentials,
        headers: getters.authHeader,
         
      })
      .then(() => {
        commit('UPDATE_PROFILE', credentials)
        localStorage.setItem('newCredentials', JSON.stringify(credentials))
      })
      .catch(err => {
        console.error(err.response.data)
        commit('AUTH_ERROR', err.response.data)
      })

  },
    updateProfileImage({ commit, state, getters }, updateImage) {
      console.log(state.newCredentials)
      state.newCredentials = JSON.parse(state.newCredentials)
      axios({
      url: `http://localhost:8080/user/me/imgurl/${state.newCredentials.seq}`,
      method: 'POST',
      data: updateImage,
    // headers: getters.authHeader,
    })
    .then(() => {
      commit('UPDATE_IMAGE', updateImage)
    })
    .catch(err => {
      console.error(err.response.data)
    })

  },
  getImage({commit}, getImage) {
    axios({
      url: api.accounts.getUser(this.state.email),
      method: 'PUT',
      params: updateImage,
      headers: headers,
    })
    .then(() => {
      commit('UPDATE_IMAGE', getImage)
    })
    .catch(err => {
      console.error(err.response.data)
    })
  },

}
  }