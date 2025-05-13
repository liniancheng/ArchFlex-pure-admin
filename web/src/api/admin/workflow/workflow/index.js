/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:工作流管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function tree (params) {
    return request.request('/admin/workflow/tree', {
        params: params
    })
}

export function fetchOneWorkflow (id) {
    return request.get('/admin/workflow/' + id)
}

export function saveWorkflow (params) {
    return request.post('/admin/workflow', params)
}

export function updateWorkflowById (params) {
    return request.put('/admin/workflow', params)
}

export function removeWorkflowById (id) {
    return request.delete('/admin/workflow/' + id)
}

export function copyWorkflow (params) {
    return request.post('/admin/workflow/copy', params)
}
