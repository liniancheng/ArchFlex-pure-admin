import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
import theme from './modules/theme'

// default router permission control
// import permission from './modules/permission'

// dynamic router permission control (Experimental)
import permission from './modules/async-router'
import getters from './getters'

getters.getRouteParam = state => {
  return state.route.param
}

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    theme,
    permission
  },
  state: {
    route: { param: '' }
  },
  mutations: {
    setRouteParam (state, value) {
      state.route.param = value
    }
  },
  actions: {
    setRouteParam ({ commit, state }, value) {
      commit('setRouteParam', value)
    }
  },
  getters
})
