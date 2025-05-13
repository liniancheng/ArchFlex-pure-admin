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
  return request.request('/admin/annoType/page', {
    params: params
  })
}

const fetchOne = id => {
  return request.get('/admin/annoType/' + id)
}

const save = params => {
  return request.post('/admin/annoType', params)
}

const updateById = params => {
  return request.put('/admin/annoType', params)
}

const removeById = id => {
  return request.delete('/admin/annoType/' + id)
}

const fetAll = () => {
  return request.get('/admin/annoType/findAll/')
}

export { fetch, fetchOne, save, updateById, removeById, fetAll }
