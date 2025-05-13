/*
 * @Author: xinglj
 * @Date: 2020-06-08 09:47:32
 * @Descripttion:公告类型接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-08 09:47:32
 */
import request from '@/utils/request'

const fetch = params => {
  return request.request('/admin/help/page', {
    params: params
  })
}

const fetchOne = id => {
  return request.get('/admin/help/' + id)
}

const save = params => {
  return request.post('/admin/help', params)
}

const removeById = id => {
  return request.delete('/admin/help/' + id)
}

const download = id => {
  return request.get('/admin/help/download/' + id, {
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export { fetch, fetchOne, save, removeById, download }
