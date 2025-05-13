/*
 * @Author: dengchf
 * @Date: 2020-08-25 15:17:06
 * @Descripttion:布局数据项关系接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: dengchf
 * @LastEditTime: 2020-08-25 15:17:06
 */
import request from '@/utils/request'

export function listRels (id) {
    return request('/admin/layoutItemRel/listRels/' + id)
}
export function saveLists (params, layId) {
  return request.post(`/admin/layoutItemRel/saveLists/${layId}`, params)
}
