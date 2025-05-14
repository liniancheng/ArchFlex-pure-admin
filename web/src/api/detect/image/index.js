/*
 * @Author: adtec
 * @Date: 2022-03-03 10:48:27
 * @Descripttion:指标定义接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-03-03 10:48:27
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
