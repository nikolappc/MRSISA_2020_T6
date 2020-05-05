import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    ulogovan:"",
    snackbar: {
      showing: false,
      text: '',
    }
  },
  mutations: {
    setUlogovan(state, ulogovan){
      state.ulogovan = ulogovan
    },
    setSnackbar(state, snackbar) {
      snackbar.showing = true;
      state.snackbar = snackbar;
    },
  },
  actions: {
  },
  modules: {
  }
})
