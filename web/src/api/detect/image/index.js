/*
 * @Author: all
 * @Date: 2022-03-03 10:48:27
 * @Description: 图片相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-06-18 22:38:27
 */
import request from '@/utils/request'
// 查询执行计划
export function fetch (params) {
  return request.post('/crossvalidation/cvchkplan/page', params)
}

// 删除执行计划
export function deletePlan (planId) {
  return request.delete('/crossvalidation/cvchkplan/' + planId)
}

// 新增
export function save (params) {
  return request.post('/crossvalidation/cvchkplan', params)
}

// 上传
export function upload (file) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post(
        '/detect/image/upload',
        formData,
        {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
    )
}
