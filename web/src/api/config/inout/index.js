/*
 * @Author: dengchf
 * @Date: 2019-12-15 22:22:24
 * @Descripttion:导入导出信息表服务定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: dengchf
 * @LastEditTime: 2019-12-15 22:22:24
 */
import request from '@/utils/request'
import qs from 'qs'

export function upload (file) {
  const form = new FormData()
  form.append('file', file)
  return request.post(`/admin/migratePub/upload`, form)
}

export function download (ids) {
  return request({
    data: qs.stringify({ ids: ids.join(',') }),
    method: 'POST',
    responseType: 'blob',
    url: '/admin/migratePub/download'
  })
}
export function fetch () {
  return request.get('/admin/migratePub/getAllTreeNodes')
}
