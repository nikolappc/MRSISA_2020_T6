import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    ulogovan:""
  },
  mutations: {
    setUlogovan(state, ulogovan){
      state.ulogovan = ulogovan
    }
  },
  actions: {
  },
  modules: {
  }
})
