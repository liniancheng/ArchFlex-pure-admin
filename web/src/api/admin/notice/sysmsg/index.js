/*
 * @Author: xinglj
 * @Date: 2020-06-15 12:18:10
 * @Descripttion:系统消息接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-15 12:18:10
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/sysmsg/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/sysmsg/' + id)
}

export function save (params) {
    return request.post('/admin/sysmsg', params)
}

export function updateById (params) {
    return request.put('/admin/sysmsg', params)
}

export function removeById (id) {
    return request.delete('/admin/sysmsg/' + id)
}

export function removeSelected (selectedRowKeys) {
    return request.post('/admin/sysmsg/removeByIds', selectedRowKeys)
  }
