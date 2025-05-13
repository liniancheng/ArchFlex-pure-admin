import request from '@/utils/request'

export function postAction (url, parameter) {
  return request({
    url: url,
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
export function post (url, oldPwd, newPwd) {
  return request({
    url: url + '/' + oldPwd + '/' + newPwd,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
export function downAction (url, parameter) {
  return request({
    url: url,
    method: 'post',
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

// post method= {post | put}
export function httpAction (url, parameter, method) {
  return request({
    url: url,
    method: method,
    data: parameter
  })
}

// put
export function putAction (url, parameter) {
  return request({
    url: url,
    method: 'put',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

// deleteAction
export function deleteAction (url, parameter) {
  return request({
    url: url + parameter,
    method: 'delete'
  })
}
// get
export function getAction (url, parameter) {
  return request({
    url: url,
    method: 'get',
    params: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
export function getResource (url, parameter) {
  return request({
    url: url + parameter,
    method: 'get'
  })
}
export function getResourceNoPara (url) {
  return request({
    url: url,
    method: 'get'
  })
}
