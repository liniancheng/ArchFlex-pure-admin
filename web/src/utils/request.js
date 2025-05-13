import axios from 'axios'
import store from '@/store'
import { VueAxios } from './axios'
import { ACCESS_TOKEN } from '@/store/mutation-types'

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 600000, // 请求超时时间
  headers: {
    Tenant: 'tenant base'
  }
})

const codeMessage = {
  // 200: '服务器成功返回请求的数据。',
  201: '新增或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新增或修改数据的操作。',
  // 401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。'
}

// 异常拦截处理器
const errorHandler = (error) => {
  if (error.response) {
    const data = error.response.data
    // 从 sessionStorage 获取 token
    const token = sessionStorage.getItem(ACCESS_TOKEN)
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  if (codeMessage[error.response.status]) {
    // 能识别的异常（服务器异常）都传回去
    if (error.response.data.code) {
      return Promise.resolve(error.response.data)
    } else {
      return Promise.resolve({ code: error.response.status, message: codeMessage[error.response.status] })
    }
  } else {
    return Promise.reject(error)
  }
}

// request interceptor
request.interceptors.request.use(config => {
  const token = sessionStorage.getItem(ACCESS_TOKEN)
  // 如果 token 存在
  // 让每个请求携带自定义 token 请根据实际情况自行修改
  if (token) {
    config.headers['Authorization'] = 'bearer ' + token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  return config
}, errorHandler)

// response interceptor
request.interceptors.response.use((response) => {
  return response.data
}, errorHandler)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios
}
