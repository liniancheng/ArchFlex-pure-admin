/*
 * @Author: xinglj
 * @Date: 2020-06-08 09:47:32
 * @Descripttion:公告接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-08 09:47:32
 */
import request from '@/utils/request'

const fetch = params => {
  return request.request('/admin/anno/page', {
    params: params
  })
}

const fetchOne = id => {
  return request.get('/admin/anno/' + id)
}
const fetchAttachList = id => {
  return request.get('/admin/anno/getAttachById/' + id)
}

const save = params => {
  return request.post('/admin/anno', params)
}

const updateById = params => {
  return request.put('/admin/anno', params)
}

const removeById = id => {
  return request.delete('/admin/anno/' + id)
}
const uploadAttach = params => {
  return request.post('/admin/annoAttach', params)
}
const deleteAttach = params => {
  return request.delete('/admin/annoAttach/' + params)
}
export { fetch, fetchOne, save, updateById, removeById, uploadAttach, fetchAttachList, deleteAttach }
