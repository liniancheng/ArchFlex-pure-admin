import axios from 'axios'
// import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'

export function modifyLoginPwd (oldPwd, newPwd) {
  const token = sessionStorage.getItem(ACCESS_TOKEN)
  if (token) {
    axios.defaults.headers['Authorization'] = 'bearer ' + token
    axios.defaults.headers['Tenant'] = 'tenant base'
  }
  return axios({
    url: '/api/admin/user/modifyLoginPwd/' + oldPwd + '/' + newPwd,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function tree (url, id, key) {
  const token = sessionStorage.getItem(ACCESS_TOKEN)
  if (token) {
    axios.defaults.headers['Authorization'] = 'bearer ' + token
    axios.defaults.headers['Tenant'] = 'tenant base'
  }
  return axios('/api' + url + id + '&key=' + key)
}
