/*
 * @Author: xinglj
 * @Date: 2020-06-17 09:36:02
 * @Descripttion:知识库管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-17 09:36:02
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/knowledge/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/knowledge/' + id)
}

export function save (params) {
    return request.post('/admin/knowledge', params)
}

export function updateById (params) {
    return request.put('/admin/knowledge', params)
}

export function removeById (id) {
    return request.delete('/admin/knowledge/' + id)
}

export function uploadAttach (params) {
    return request.post('/admin/att', params)
}

export function download (id) {
    return request.get('/admin/att/download/' + id, {
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      }
    })
}

export function fetchAttch (params) {
    return request('/admin/att/page', {
        params: params
    })
}
export function fetchList (id) {
    return request('/admin/att/list/' + id)
}
export function fetchAtt (id) {
    return request.get('/admin/att/' + id)
}
export function updateAtt (params) {
    return request.put('/admin/att', params)
}

export function removeAtt (id) {
    return request.delete('/admin/att/' + id)
}
