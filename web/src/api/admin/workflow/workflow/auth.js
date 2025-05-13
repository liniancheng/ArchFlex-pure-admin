/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:工作流节点权限管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function fetch (params) {
  return request.request('/admin/workflowNodeAuth/page', {
    params: params
  })
}

export function save (params) {
    return request.post('/admin/workflowNodeAuth', params)
}

export function removeById (id) {
    return request.delete('/admin/workflowNodeAuth/' + id)
}

export function roles (id) {
    return request.request('/admin/workflowNodeAuth/roles/' + id)
}

export function macros (workflowId, nodeId, authType) {
    return request.request('/admin/workflowNodeAuth/macros/' + workflowId + '/' + nodeId + '/' + authType)
}
