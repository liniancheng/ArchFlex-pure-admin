/*
 * @Author: adtec
 * @Date: 2020-06-28 07:49:50
 * @Descripttion:字典明细接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-28 07:49:50
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/item/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/item/' + id)
}

export function save (params) {
    return request.post('/admin/item', params)
}

export function updateById (params) {
    return request.put('/admin/item', params)
}

export function removeById (id) {
    return request.delete('/admin/item/' + id)
}
