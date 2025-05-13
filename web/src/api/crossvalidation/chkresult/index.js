/*
 * @Author: adtec
 * @Date: 2022-07-12 17:12:31
 * @Descripttion:入口校验结果接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-07-12 17:12:31
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/crossvalidation/chkresult/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/crossvalidation/chkresult/' + id)
}

export function save (params) {
    return request.post('/crossvalidation/chkresult', params)
}

export function updateById (params) {
    return request.put('/crossvalidation/chkresult', params)
}

export function removeById (id) {
    return request.delete('/crossvalidation/chkresult/' + id)
}

export function getlgprList () {
    return request.get('/crossvalidation/chkresult/lgprList')
}

export function getSysNmList () {
    return request.get('/crossvalidation/chkresult/sysNmList')
}

export function getSysTabNmList (sysNm) {
    return request.get('/crossvalidation/chkresult/sysTabNmList/' + sysNm)
}
