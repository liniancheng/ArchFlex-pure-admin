/*
 * @Author: dengchf
 * @Date: 2020-08-25 15:04:09
 * @Descripttion:首页布局接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: dengchf
 * @LastEditTime: 2020-08-25 15:04:09
 */
import request from '@/utils/request'
import qs from 'qs'// 引入qs

export function fetch (params) {
    return request('/admin/layout/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/layout/' + id)
}

export function save (params) {
    return request.post('/admin/layout', params)
}

export function updateById (params) {
    return request.put('/admin/layout', params)
}

export function removeById (id) {
    return request.delete('/admin/layout/' + id)
}

/**
 * 用户自定义布局（没有时，自动创建）
 */
export function fetchByLoginName () {
    return request.get('/admin/layout/fetchByLoginName')
}
/**
 * 预览
 * @param {*} id
 */
export function preview (id) {
    return request.get('/admin/layout/fetchOnce/' + id)
}
// 权限相关
export function listRel (id) {
    return request('/admin/layoutRel/list/' + id)
}

export function saveLayoutRel (params) {
    return request({
        data: qs.stringify(params),
        method: 'POST',
        url: '/admin/layoutRel'
    })
}

export function deleteLayoutRel (params) {
    return request.delete('/admin/layoutRel', {
        params: params
    })
}
