/*
 * @Author: adtec
 * @Date: 2020-06-28 07:42:28
 * @Descripttion:字典信息接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-28 07:42:28
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/info/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/info/' + id)
}

export function save (params) {
    return request.post('/admin/info', params)
}

export function updateById (params) {
    return request.put('/admin/info', params)
}

export function removeById (id) {
    return request.delete('/admin/info/' + id)
}

export function ajaxGetDictItems (code) {
    return request.get('/admin/info/getDictItems/' + code)
}
