/*
 * @Author: adtec
 * @Date: 2020-06-30 09:15:15
 * @Descripttion:工作流实例图形接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2020-06-30 09:15:15
 */
import request from '@/utils/request'

export function graph (id) {
    return request.get('/admin/workflowInstance/graph/' + id)
}
