/*
 * @Author: adtec
 * @Date: 2022-03-29 14:14:04
 * @Descripttion:资源配置接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-03-29 14:14:04
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/resall/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/resall/' + id)
}

export function fetchList () {
  return request.get('/admin/resall/list')
}

export function save (params) {
    return request.post('/admin/resall', params)
}

export function test (params) {
  return request.post('/admin/resall/test', params)
}

export function updateById (params) {
    return request.put('/admin/resall', params)
}

export function removeById (id) {
    return request.delete('/admin/resall/' + id)
}
