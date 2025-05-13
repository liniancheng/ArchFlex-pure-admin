import { deleteAction, getResource, postAction, getAction, putAction } from '@/api/admin/manage'
// 保存角色信息
const saveRole = (params) => postAction('/admin/role', params)
// 修改角色信息
const updateRole = (params) => putAction('/admin/role', params)
// 根据Id查询角色信息
const getRoleById = (params) => getResource('/admin/role/', params)
// 删除角色
const deleteRole = (params) => deleteAction('/admin/role/', params)
// 获取所有角色
const getRolesPage = (params) => getAction('/admin/role/page', params)
// // 获取角色拥有的资源
const getMenusByRole = (params) => getResource('/admin/roleMenuRel/treeNode/', params)
export {
  saveRole,
  updateRole,
  getRoleById,
  deleteRole,
  getRolesPage,
  getMenusByRole
}
