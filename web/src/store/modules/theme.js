// 在store中存储主题

import { publicCode } from '@/api/admin/public'

const theme = {
  state: {
    config: {}
  },

  mutations: {
    SET_CONFIG: (state, config) => {
      state.config = config
    }
  },
  actions: {
    PublicCode ({ commit }) {
      return new Promise((resolve, reject) => {
        publicCode().then(res => {
          if (res.code === 0) {
            if (res.data.codeMap) {
              commit('SET_CONFIG', res.data.codeMap)
            } else {
              commit('SET_CONFIG', {})
            }
            resolve(res)
          } else {
            reject(res)
          }
        })
      })
    }
  }
}

export default theme
