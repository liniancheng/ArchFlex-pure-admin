import { getAction, deleteAction, getResource, postAction, getResourceNoPara, putAction } from '@/api/admin/manage'
import request from '@/utils/request'
import qs from 'qs'// 引入qs

// 获取用户对应的角色
const getUserList = (params) => getAction('/admin/user/page', params)
// 获取所有角色
const getRoles = () => getResourceNoPara('/admin/role/page')
// 删除用户
const deleteUser = (params) => deleteAction('/admin/user/id/', params)
// 保存用户信息
const saveUser = (params) => postAction('/admin/user', params)
// 修改用户信息
const updateUser = (params) => putAction('/admin/user', params)
// 根据mobile获取用户信息
const loadUserByMobile = (params) => getResource('/admin/user/loadUserByMobile/', params)
// 根据登录名获取用户信息
const loadUserByUsername = (params) => getResource('/admin/user/loadUserByUsername/', params)
// 根据所有机构信息
const getTrees = () => getResourceNoPara('/admin/branch/tree')
// 根据userId查询用户信息
const getUserById = (params) => getResource('/admin/user/id/', params)
// 删除菜单
const deleteMenu = (params) => deleteAction('/admin//resource/id/', params)
// 根据所有菜单信息
const getMenus = () => getResourceNoPara('/admin/resource/tree')
// 根据userId查询用户信息
export {
  getUserList,
  deleteUser,
  saveUser,
  loadUserByMobile,
  loadUserByUsername,
  getRoles,
  updateUser,
  getTrees,
  getUserById,
  deleteMenu,
  getMenus
}

export function changeLoginPwd (params) {
  return request({
    url: `/admin/user/changeLoginPwd/${params.loginName}`,
    method: 'post',
    data: qs.stringify(params)
  })
}
