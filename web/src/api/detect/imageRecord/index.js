/*
 * @Author: littlelee
 * @Date: 2025-06-17 11:03:03
 * @Descripttion:图片识别记录接口定义
 * @version: 1.0
 * @Copyright: littlelee
 * @LastEditors: littlelee
 * @LastEditTime: 2025-06-17 11:03:03
 */
import request from '@/utils/request'

export function fetch (params) {
    return request.get('/detect/imageRecord/page', {
        params: params
    })
}
