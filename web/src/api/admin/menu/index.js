import { deleteAction, getResource, postAction, getResourceNoPara, putAction } from '@/api/admin/manage'
// 保存菜单信息
const saveMenu = (params) => postAction('/admin/resource', params)
// 修改菜单信息
const updateMenu = (params) => putAction('/admin/resource', params)
// 根据userId查询菜单信息
const getMenuById = (params) => getResource('/admin/resource/id/', params)
// 删除菜单
const deleteMenu = (params) => deleteAction('/admin/resource/id/', params)
// 根据所有菜单信息
const getMenus = () => getResourceNoPara('/admin/resource/tree')
// 根据所有菜单信息
const getTreeNodes = () => getResourceNoPara('/admin/resource/treeNode')
export {
  saveMenu,
  updateMenu,
  getMenuById,
  deleteMenu,
  getMenus,
  getTreeNodes
}
