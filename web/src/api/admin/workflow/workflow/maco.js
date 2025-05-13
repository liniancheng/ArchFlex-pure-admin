/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:工作流宏变量管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function tree (params) {
    return request.request('/admin/workflowMacro/tree', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/workflowMacro/' + id)
}

export function save (params) {
    return request.post('/admin/workflowMacro', params)
}

export function updateById (params) {
    return request.put('/admin/workflowMacro', params)
}

export function removeById (id) {
    return request.delete('/admin/workflowMacro/' + id)
}
