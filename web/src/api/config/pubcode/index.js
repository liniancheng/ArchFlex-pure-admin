/*
 * @Author: xinglj
 * @Date: 2020-06-08 09:47:32
 * @Descripttion:参数管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-08 09:47:32
 */
import request from '@/utils/request'

const fetch = params => {
  return request.request('/admin/param/page', {
    params: params
  })
}

const fetchOne = id => {
  return request.get('/admin/param/' + id)
}

const save = params => {
  return request.post('/admin/param', params)
}

const updateById = params => {
  return request.put('/admin/param', params)
}

const removeById = id => {
  return request.delete('/admin/param/' + id)
}

export { fetch, fetchOne, save, updateById, removeById }
