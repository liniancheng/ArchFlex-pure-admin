/*
 * @Author: xinglj
 * @Date: 2020-06-13 10:29:18
 * @Descripttion:邮件管理接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: xinglj
 * @LastEditTime: 2020-06-13 10:29:18
 */
import request from '@/utils/request'

export function fetch (params) {
    return request('/admin/mail/mailTemp/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/mail/mailTemp/getById/' + id)
}

export function save (params) {
    return request.post('/admin/mail/mailTemp/save', params)
}

export function updateById (params) {
    return request.put('/admin/mail/mailTemp/update', params)
}

export function removeById (id) {
    return request.delete('/admin/mail/mailTemp/delete/' + id)
}
