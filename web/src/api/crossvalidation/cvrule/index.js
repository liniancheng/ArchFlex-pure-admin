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
// 查询规则
export function fetch (params) {
  return request.post('/crossvalidation/cvrule/page', params)
}
