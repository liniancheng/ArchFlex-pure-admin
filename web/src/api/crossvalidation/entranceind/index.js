/*
 * @Author: adtec
 * @Date: 2022-07-12 14:18:22
 * @Descripttion:入口校验接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-07-12 14:18:22
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/crossvalidation/entranceind/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/crossvalidation/entranceind/' + id)
}

export function save (params) {
    return request.post('/crossvalidation/entranceind', params)
}

export function updateById (params) {
    return request.put('/crossvalidation/entranceind', params)
}

export function removeById (id) {
    return request.delete('/crossvalidation/entranceind/' + id)
}

export function getSysNmList () {
    return request.get('/crossvalidation/entranceind/sysNmList')
}
export function getSysTabNmList (sysNm) {
    return request.get('/crossvalidation/entranceind/sysTabNmList/' + sysNm)
}
export function getSysColunmList (sysTabNm) {
    return request.get('/crossvalidation/entranceind/sysColumnList/' + sysTabNm)
}
