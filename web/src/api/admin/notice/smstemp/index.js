/*
 * @Author: xinglj
 * @Date: 2020-06-15 12:07:12
 * @Descripttion:短信模板接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-15 12:07:12
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/smstemp/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/smstemp/' + id)
}

export function save (params) {
    return request.post('/admin/smstemp', params)
}

export function updateById (params) {
    return request.put('/admin/smstemp', params)
}

export function removeById (id) {
    return request.delete('/admin/smstemp/' + id)
}
