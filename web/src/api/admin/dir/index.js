/*
 * @Author: xinglj
 * @Date: 2020-06-17 09:38:38
 * @Descripttion:知识库类型接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-17 09:38:38
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/dir/page', {
        params: params
    })
}
export function fetchOne (id) {
    return request.get('/admin/dir/' + id)
}
export function save (params) {
    return request.post('/admin/dir', params)
}

export function updateById (params) {
    return request.put('/admin/dir', params)
}

export function removeById (id) {
    return request.delete('/admin/dir/' + id)
}
// 获取所有类型
export function getdirs () {
    return request.get('/admin/dir/tree')
}
// 获取所有类型的树
export function getTreeNodes () {
    return request.get('/admin/dir/treeNode')
}
