import axios from 'axios'
import api from '@/api/apis'
import router from '@/router'
import { OpenVidu } from 'openvidu-browser';

axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'

const OPENVIDU_SERVER_URL = "https://i7b304.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "UCI";
const OPENVIDUAPP_HEADER = btoa("OPENVIDUAPP:"+`${OPENVIDU_SERVER_SECRET}`);

export default {
  state: {
    dongSangDescribe : "이번 게임은 일심동체 게임입니다. \n 주어진 단어를 보고 생각나는 포즈를 취하세요! \n 결과를 보고 가장 포즈가 다른 사람을 한명 지목하세요!",
    isHost: false,
    rooms: [],
    token: localStorage.getItem('token') || ''
  },
  getters: {
    dongSangDescribe: state => state.dongSangDescribe,
    isHost: state => state.isHost,
    getAuthHeader: state => ({ Authorization : `${state.token}` }),
    getRoomsData: state => state.rooms
  },
  mutations: {
    IS_HOST : state => state.isHost = true,
    IS_NOT_HOST : state => state.isHost = false,
    SET_ROOMS : (state,data) => state.rooms = data
  },
  actions: {
    assignHost ({commit}) {
      commit("IS_HOST")
    },
    resignHost ({commit}) {
      commit("IS_NOT_HOST")
    },
    createRoomData ({getters},roomData) {
      axios({
        url: api.rooms.create(),
        method: 'post',
        data: roomData,
        headers: getters.getAuthHeader
      })
      .then(res => {
        console.log(res,"방db저장완료") 
      })
      .catch(err => {
        console.log(err)
      })
    },
    loadRoomData ({commit, getters}) {
      console.log(getters.getAuthHeader)
      axios({
        url: api.rooms.rooms(),
        method: 'get',
        headers: getters.getAuthHeader
      })
      .then(res => {
        commit('SET_ROOMS',res)
      })
      .catch(err => {
        console.log(err)
      })
    },
    getRoomInfo ({},sessionId) {
      return new Promise((resolve, reject) => {
        axios({
          url:`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}`,
          method: 'get',
          headers: { Authorization : `Basic ${(OPENVIDUAPP_HEADER)}` }								
        })
        .then(res => {
          resolve(res.data) 
        })
        .catch(error => reject(error.response));
			});
    },
    getRoomSeq ({getters},roomTitle) {
      return new Promise((resolve, reject) => {
        axios({
          url: api.rooms.seq(roomTitle),
          method: 'get',
          headers: getters.getAuthHeader
        })
        .then(res => resolve(res.data.roomSeq))
        .catch(error => reject(error.response));
      })
    },
    deleteRoomData ({getters},roomId) {
      return new Promise((resolve, reject) => {
        axios({
          url: api.rooms.delete(roomId),
          method: 'delete',
          headers: getters.getAuthHeader
        })
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
      })
    },
    changeActive ({dispatch,getters},roomTitle) {
      dispatch('getRoomSeq',roomTitle)
      .then((res) => {
        axios({
          url: api.rooms.active(res),
          method: 'put',
          headers: getters.getAuthHeader
        })})
        .then(res => dispatch('loadRoomData'))
        .catch(err=>console.log(err))
      }
  }
}