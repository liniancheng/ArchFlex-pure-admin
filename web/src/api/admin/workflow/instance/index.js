/*
 * @Author: adtec
 * @Date: 2020-06-30 09:15:15
 * @Descripttion:工作流实例接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:15:15
 */
import request from '@/utils/request'

export function tree (params) {
    return request.request('/admin/workflowInstance/tree', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/workflowInstance/' + id)
}

export function save (params) {
    return request.post('/admin/workflowInstance', params)
}

export function updateById (params) {
    return request.put('/admin/workflowInstance', params)
}

export function removeById (id) {
    return request.delete('/admin/workflowInstance/' + id)
}
