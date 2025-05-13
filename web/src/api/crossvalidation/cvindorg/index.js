/*
 * @Author: adtec
 * @Date: 2022-04-01 10:40:29
 * @Descripttion:指标法人配置接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-04-01 10:40:29
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/crossvalidation/cvindorg/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/crossvalidation/cvindorg/' + id)
}

export function save (params) {
    return request.post('/crossvalidation/cvindorg', params)
}

export function updateById (params) {
    return request.put('/crossvalidation/cvindorg', params)
}

export function removeById (id) {
    return request.delete('/crossvalidation/cvindorg/' + id)
}
