import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const getDefaultState = () => {
  return {
    ulogovan:"",
    snackbar: {
      showing: false,
      text: '',
    }
  }
}

export default new Vuex.Store({
  state: {
    ulogovan:"",
    snackbar: {
      showing: false,
      text: '',
    }
  },
  plugins:[
    createPersistedState(),

  ],
  mutations: {
    setUlogovan(state, ulogovan){
      state.ulogovan = ulogovan
    },
    setSnackbar(state, snackbar) {
      snackbar.showing = true;
      state.snackbar = snackbar;
    },
    resetState (state) {
      Object.assign(state, getDefaultState())
    }
  },
  actions: {
  },
  modules: {
  }
})
