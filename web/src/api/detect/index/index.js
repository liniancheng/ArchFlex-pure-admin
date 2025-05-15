/*
 * @Author: adtec
 * @Date: 2022-03-03 10:48:27
 * @Descripttion:指标定义接口定义
 * @version: 1.0
 * @Copyright: adtec
 * @LastEditors: adtec
 * @LastEditTime: 2022-03-03 10:48:27
 */
import request from '@/utils/request'
// 修改
export function fetchOne (indNo) {
    return request.get('/detect/detectindex/getCvChkInd/' + indNo)
}
// 新增
export function save (params) {
    return request.post('/detect/detectindex', params)
}
// 修改
export function updates (params) {
    return request.put('/detect/detectindex', params)
}
// 删除
export function removeById (indNo) {
    return request.delete('/detect/detectindex' + indNo)
}
// 树形菜单接口
export function treeNode () {
    return request.get('/detect/detectindex/treeNode')
}
// 列表接口
export function fetchByIndNo (indNo) {
    return request.get('/detect/detectindex/fetchByIndNo/' + indNo)
}
// 搜索树形接口
export function treeSearch (searchValue) {
    return request.get('/detect/detectindex/treeSearch/' + searchValue)
  }
