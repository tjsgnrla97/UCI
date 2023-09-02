import Vue from 'vue'
import Vuex from 'vuex'

import accounts from './modules/accounts.js'
import userprofile from './modules/userprofile.js'
import createPersistedState from 'vuex-persistedstate'
import videos from './modules/videos.js'
import alert from './modules/alert.js'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    accounts,
    userprofile,
    videos,
    alert,
  },
  plugins: [
    createPersistedState()
  ],
})
