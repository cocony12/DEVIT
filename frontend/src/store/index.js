import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : sessionStorage.getItem('token'),
    email: localStorage.getItem('email') ? localStorage.getItem('email') : sessionStorage.getItem('email'),
    profile: localStorage.getItem('profile') ? localStorage.getItem('profile') : sessionStorage.getItem('profile'),
    smallMode: false,
    loading: 0,
    isChange: false,
  },
  mutations: {
    login(state, payload) {
      if(localStorage.getItem('autoLogin') == 'true') {
        localStorage.setItem('token', payload.token)        
        localStorage.setItem('email', payload.email)
        localStorage.setItem('profile', payload.profile)
      }
      sessionStorage.setItem('token', payload.token)
      sessionStorage.setItem('email', payload.email)
      sessionStorage.setItem('profile', payload.profile)
      state.token = payload.token;
      state.email = payload.email;
      state.profile = payload.profile
    },
    logout(state) {
      localStorage.setItem('autoLogin', 'false')
      localStorage.setItem('token', '')        
      localStorage.setItem('email', '')   
      localStorage.setItem('profile', '')
      sessionStorage.setItem('token', '')
      sessionStorage.setItem('email', '')
      sessionStorage.setItem('profile', '')      
      state.token = '';
      state.emial = '';
      state.profile = '';
    },
    startLoading(state) {
      state.loading += 1;
    },
    endLoading(state) {
      if(state.loading > 0)
        state.loading -= 1;
    },
    setDisplayMode(state, payload) {
      state.smallMode = payload
    },
    setChange(state, payload) {
      state.isChange = payload
    },
  },
  actions: {
    
  },
  modules: {
  }
})