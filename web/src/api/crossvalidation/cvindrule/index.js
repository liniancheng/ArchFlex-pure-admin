/*
 * @Author: adtec
 * @Date: 2022-03-10 11:03:03
 * @Descripttion:指标规则校验设置接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-03-10 11:03:03
 */
import request from '@/utils/request'

export function fetch (params) {
    return request.get('/crossvalidation/cvindrule/page', {
        params: params
    })
}
// 查询单个
export function fetchOne (id) {
    return request.get('/crossvalidation/cvindrule/' + id)
}
// 新增
export function save (params) {
    return request.post('/crossvalidation/cvindrule', params)
}
// 修改
export function updateById (params) {
    return request.put('/crossvalidation/cvindrule', params)
}
// 删除
export function removeById (id) {
    return request.delete('/crossvalidation/cvindrule/' + id)
}
