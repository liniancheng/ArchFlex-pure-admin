/*
 * @Author: dengchf
 * @Date: 2020-09-02 09:00:00
 * @Descripttion:公告类型接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: dengchf
 * @LastEditTime: 2020-09-02 09:00:00
 */
import request from '@/utils/request'

export function fetch (params) {
  // return request('/admin/migrate/page', params)
  return request.get('/admin/migrate/getAllTreeNodes')
}

export function fetchOne (id) {
  return request.get(`/admin/migrate/${id}`)
}

export function save (params) {
  return request.post('/admin/migrate', params)
}

export function updateById (params) {
  return request.put('/admin/migrate', params)
}

export function removeById (id) {
  return request.delete(`/admin/migrate/${id}`)
}

export function getTreeNodes (pId) {
  return request.get(`/admin/migrate/getTreeNodes/${pId}`)
}
export function getAllTreeNodes () {
  return request.get('/admin/migrate/getAllTreeNodes')
}
