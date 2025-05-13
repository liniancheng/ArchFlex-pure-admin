import request from '@/utils/request'
import CryptoJS from '@/utils/CryptoJS'
import qs from 'qs'

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return request({
    url: '/auth/oauth/token',
    method: 'post',
    data: qs.stringify({
      username: parameter.username,
      password: CryptoJS.encrypt(parameter.password),
      client_id: 'cloud',
      client_secret: 'cloud',
      captcha: parameter.captcha + '@' + parameter.captchaId,
      grant_type: 'password'
    })
  })
}

export function mobileLogin (parameter) {
  return request({
    url: '/auth/mobile/token',
    method: 'post',
    data: qs.stringify({
      mobile: parameter.mobile,
      code: parameter.captcha,
      client_id: 'cloud',
      client_secret: 'cloud',
      grant_type: 'mobile'
    })
  })
}

export function getCaptcha () {
  return request({
    url: '/admin/user/captcha',
    method: 'get',
    handlers: {
      'Authorization': ''
    }
  })
}

export function getSmsCaptcha (parameter) {
  return request.get('/admin/user/mobile/' + parameter.mobile)
}

export function getInfo () {
  return request.get('/admin/user/info', {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getMenuTree () {
  return request.get('/admin/resource/menu/tree', {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurrentUserNav () {
  return request.get('/admin/resource/menu/tree', {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function logout (token) {
  return request.delete('/auth/token/' + token, {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getFindPasswordTypes () {
  return request.get('/admin/user/forgot/findPasswordTypes')
}

export function forgetPassword (params) {
  return request.post('/admin/user/forgot/forgetPassword', params)
}

export function activePassword (code) {
  return request.get('/admin/user/forgot/activePassword/' + code)
}

export function updateLoginTime () {
  return request.post('/admin/user/updateLoginTime')
}
