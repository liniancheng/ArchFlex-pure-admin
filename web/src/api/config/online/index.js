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

export function fetch (param) {
  return request.get('/auth/token/page', {
    params: param
  })
}

export function deleteToken (id) {
  return request.delete(`/auth/token/${id}`)
}
