import storage from 'store'
import { getHomePage } from '@/api/admin/home'
import { login, mobileLogin, getInfo, logout, getCaptcha, getMenuTree } from '@/api/login'
import { ACCESS_TOKEN, PREMISSIONS } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {},
    permissions: [],
    menuTree: [],
    listTask: [],
    listMsg: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_PREMISSION: (state, premissions) => {
      state.permissions = premissions
    },
    SET_MENU_TREE: (state, menuTree) => {
      state.menuTree = menuTree
    },
    SET_LIST_MSG: (state, listMsg) => {
      state.listMsg = listMsg
    },
    SET_LIST_TASK: (state, listTask) => {
      state.listTask = listTask
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          if (response.code) {
            reject(response)
          } else {
            const result = response.access_token
            // storage.set(ACCESS_TOKEN, result, 60 * 60 * 1000)
            sessionStorage.setItem(ACCESS_TOKEN, result)
            commit('SET_TOKEN', result)
            resolve()
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    MobileLogin ({
      commit
    }, userInfo) {
      return new Promise((resolve, reject) => {
        mobileLogin(userInfo).then(response => {
          // const result = response.access_token
          // storage.set(ACCESS_TOKEN, result, 60 * 60 * 1000)
          // commit('SET_TOKEN', result)
          // resolve()
          if (response.code && response.code === -1) {
            reject(response)
          } else {
            const result = response.access_token
            // storage.set(ACCESS_TOKEN, result, 60 * 60 * 1000)
            sessionStorage.setItem(ACCESS_TOKEN, result)
            commit('SET_TOKEN', result)
            resolve()
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetMenuTree ({ commit }) {
      return new Promise((resolve, reject) => {
        getMenuTree().then(result => {
          if (result.code === 0) {
            commit('SET_MENU_TREE', result.data)
            resolve(result)
          } else {
            reject(result)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetCaptcha ({ commit }) {
      return new Promise((resolve, reject) => {
        getCaptcha().then(res => {
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          if (response.code === 0) {
            // 保存一些重要信息
            if (response.code === 0) {
              const result = response.data
              const roles = result.roles
              const permissions = result.permissions
              commit('SET_ROLES', roles)
              commit('SET_INFO', result)
              commit('SET_NAME', { name: result.name, welcome: welcome() })
              // commit('SET_AVATAR', result.avatar) // 暂时没头像
              storage.set(PREMISSIONS, permissions, 60 * 60 * 1000)
              commit('SET_PREMISSION', permissions)
            } else {
              reject(response.message)
            }
          } else {
            reject(response.message)
          }

          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetHomePage ({ commit }) {
      return new Promise((resolve) => {
        getHomePage().then(res => {
          if (res.code === 0) {
            commit('SET_LIST_MSG', {
              anno: res.data.annoList,
              mess: res.data.messList
            })
            commit('SET_LIST_TASK', res.data.taskList)
          }
          resolve(res)
        })
      })
    },
    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_INFO', {})
          sessionStorage.removeItem(ACCESS_TOKEN)
        })
      })
    }

  }
}

export default user
