/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:工作流节点管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function tree (params) {
    return request.request('/admin/workflowNode/tree', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/workflowNode/' + id)
}

export function save (params) {
    return request.post('/admin/workflowNode', params)
}

export function updateById (params) {
    return request.put('/admin/workflowNode', params)
}

export function removeById (id) {
    return request.delete('/admin/workflowNode/' + id)
}

export function parentNodes (id, level) {
    return request.get('/admin/workflowNode/parentNodes/' + id + '/' + level)
}
