/*
 * @Author: xinglj
 * @Date: 2020-06-15 11:05:41
 * @Descripttion:短信服务器接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-15 11:05:41
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/smssrv/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/smssrv/' + id)
}

export function save (params) {
    return request.post('/admin/smssrv', params)
}

export function updateById (params) {
    return request.put('/admin/smssrv', params)
}

export function removeById (id) {
    return request.delete('/admin/smssrv/' + id)
}
