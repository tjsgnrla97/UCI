export const strict = false;
export default{
   state :{
        alert : false,
        alert_data: {
            emoji: String,
            type: '',
            title:'',
            text: '',
    }

    },
  
    getters: {
        GET_ALERT(state) {
            return state.alert;
        },
        GET_ALERT_DATA(state) {
            return state.alert_data;
        },
    },

    mutations:{
        SET_ALERT(state, payload) {
        state.alert = payload.alert;
        state.alert_data.emoji = payload.emoji;
        state.alert_data.type = payload.type;
        state.alert_data.title = payload.title;
        state.alert_data.text = payload.text;
        },

    },

    actions: {
        updateAlert({commit}, params) {
        commit('SET_ALERT', params);
        console.log('완료')
    }
    }
    } 
