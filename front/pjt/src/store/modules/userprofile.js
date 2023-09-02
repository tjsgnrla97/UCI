// import axios from 'axios'
// import router from '@/router'
// export default {
//   state: {
//     ImageUrl: '',
//     email: localStorage.getItem('email'),
//     authErr: null,
//   },
//   getters: {
//   },
//   mutations: {
//     UPDATE_PROFILE(state, credentials) {
//         state.currentUser.email = credentials.email
//         state.currentUser.userName = credentials.username
//         state.currentUser.department = credentials.department
//         state.currentUser.position = credentials.position
//     },
//     UPDATE_IMG(state, payload) {
//       if (payload.profileImageUrl != null) {
//           state.currentuser.profileImageUrl = payload.profileImageUrl
//         }
//     },
//     AUTH_ERROR (state, err) {
//       state.authErr = err
//     },
//   },
//   actions: {
//     updateProfile({ commit }, credentials, headers) {
//         axios({
//           url: 'http://localhost:8080/user/me/h@naver.com',
//           method: 'put',
//           data: credentials,
//           headers: headers,
//         })
//         .then(() => {
//           alert('성공')
//           commit('UPDATE_PROFILE', credentials)
//           router.push('/')
//         })
//         .catch(err => {
//           alert('실패')
//           console.error(err.response.data)
//           commit('AUTH_ERROR', err.response.data)
//         })

//     },
//     updateProfileImage({ commit }, updateImage) {
//       axios({
//         url: `http://localhost:8080/user/imgurl/${getters.currentUser.seq}`,
//         method: 'POST',
//         params: updateImage,
//         headers: headers,
//       })
//       .then(() => {
//         commit('UPDATE_IMAGE', updateImage)
//       })
//       .catch(err => {
//         console.error(err.response.data)
//       })

//     }
//   },
//   getImage({commit}, getImage) {
//     axios({
//       url: `http://localhost:8080/user/me/${this.state.email}`,
//       method: 'PUT',
//       params: updateImage,
//       headers: headers,
//     })
//     .then(() => {
//       commit('UPDATE_IMAGE', getImage)
//     })
//     .catch(err => {
//       console.error(err.response.data)
//     })
//   }
// }