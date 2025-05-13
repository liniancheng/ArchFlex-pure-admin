/*
 * @Author: xinglj
 * @Date: 2020-06-15 12:13:02
 * @Descripttion:短信发送日志接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-15 12:13:02
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/smslog/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/smslog/' + id)
}

export function save (params) {
    return request.post('/admin/smslog', params)
}

export function updateById (params) {
    return request.put('/admin/smslog', params)
}

export function removeById (id) {
    return request.delete('/admin/smslog/' + id)
}

export function removeSelected (selectedRowKeys) {
    return request.post('/admin/smslog/removeByIds', selectedRowKeys)
  }
