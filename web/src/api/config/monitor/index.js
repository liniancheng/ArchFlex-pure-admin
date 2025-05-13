/*
 * @Author: hewei
 * @Date: 2020-09-01 12:20:30
 * @Descripttion:
 * @version: 1.0
 * @Copyright: 北京先进数通信息技术股份公司
 * @LastEditors: dengchf
 * @LastEditTime: 2020-09-01 12:20:30
 */

import request from '@/utils/request'

export function servers (param) {
  return request.get('/admin/monitor/server', {
    params: param
  })
}

export function services (param) {
  return request.get('/admin/monitor/service', {
    params: param
  })
}

export function clearServer () {
  return request.get('/admin/monitor/clearServer')
}

export function clearService () {
  return request.get('/admin/monitor/clearService')
}
