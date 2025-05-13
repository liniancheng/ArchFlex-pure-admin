/*
 * @Author: adtec
 * @Date: 2020-06-30 09:30:36
 * @Descripttion:用户评论接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:30:36
 */
import request from '@/utils/request'

export function fetch (params) {
     return request.request('/admin/comment/page', {
         params: params
     })
}

export function save (params) {
    return request.post('/admin/comment', params)
}

export function removeById (id) {
    return request.delete('/admin/comment/' + id)
}
