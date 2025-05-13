/*
 * @Author: adtec
 * @Date: 2020-06-10 16:06:03
 * @Descripttion:用户导入接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-10 16:06:03
 */
import request from '@/utils/request'

export function download (type) {
    return request({
      url: '/admin/user/download/' + type,
      method: 'post',
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      }
    })
}

export function upload (type, formData) {
    return request({
      url: '/admin/user/upload/' + type,
      method: 'post',
      data: formData
    })
}
