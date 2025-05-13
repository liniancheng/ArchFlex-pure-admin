/*
 * @Author: xinglj
 * @Date: 2020-06-09 09:47:32
 * @Descripttion:操作日志
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-09 09:47:32
 */
import request from '@/utils/request'

export function fetch (params) {
  return request.request('/admin/log/page', {
    params: params
  })
}

export function removeById (id) {
  return request.delete('/admin/log/' + id)
}
export function removeSelected (selectedRowKeys) {
  return request.post('/admin/log/removeByIds', selectedRowKeys)
}
