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
    return request('/admin/mail/mailSrv/page', {
        params: params
    })
}
export function fetchList () {
    return request('/admin/mail/list')
}
export function fetchLog (params) {
    return request('/admin/mail/mailLog/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/mail/mailSrv/getById/' + id)
}

export function save (params) {
    return request.post('/admin/mail/mailSrv/save', params)
}

export function updateById (params) {
    return request.put('/admin/mail/mailSrv/update', params)
}

export function removeById (id) {
    return request.delete('/admin/mail/mailSrv/delete/' + id)
}

export function test (params) {
    return request.post('/admin/mail/mailSrv/test', params)
}

export function removeLogById (id) {
    return request.delete('/admin/mail/' + id)
  }
  export function removeSelected (selectedRowKeys) {
    return request.post('/admin/mail/removeByIds', selectedRowKeys)
  }
