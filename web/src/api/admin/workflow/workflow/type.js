/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:工作流分类管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function fetchOneType (id) {
    return request.get('/admin/workflowType/' + id)
}

export function saveType (params) {
    return request.post('/admin/workflowType', params)
}

export function updateTypeById (params) {
    return request.put('/admin/workflowType', params)
}

export function removeTypeById (id) {
    return request.delete('/admin/workflowType/' + id)
}
