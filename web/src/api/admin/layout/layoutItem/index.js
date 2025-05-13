/*
 * @Author: dengchf
 * @Date: 2020-08-25 15:17:06
 * @Descripttion:布局数据项接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: dengchf
 * @LastEditTime: 2020-08-25 15:17:06
 */
import request from '@/utils/request'
import qs from 'qs'// 引入qs

export function fetch (params) {
    return request('/admin/layoutItem/page', {
        params: params
    })
}
export function fetchPerson (params) {
    return request('/admin/layoutItem/fetchPerson', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/layoutItem/' + id)
}

export function save (params) {
    return request.post('/admin/layoutItem', params)
}

export function updateById (params) {
    return request.put('/admin/layoutItem', params)
}

export function removeById (id) {
    return request.delete('/admin/layoutItem/' + id)
}
// 权限相关
export function listRel (id) {
    return request('/admin/itemRel/list/' + id)
}

export function saveItemRel (params) {
    return request({
        data: qs.stringify(params),
        method: 'POST',
        url: '/admin/itemRel'
    })
}

export function deleteItemRel (params) {
    return request.delete('/admin/itemRel', {
        params: params
    })
}
