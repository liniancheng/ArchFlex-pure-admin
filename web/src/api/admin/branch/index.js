import { deleteAction, getResource, postAction, getResourceNoPara, putAction, downAction } from '@/api/admin/manage'
// 保存机构信息
const saveBranch = (params) => postAction('/admin/branch', params)
// 修改机构信息
const updateBranch = (params) => putAction('/admin/branch', params)
// 根据Id查询机构信息
const getBranchById = (params) => getResource('/admin/branch/id/', params)
// 删除机构
const deleteBranch = (params) => deleteAction('/admin/branch/id/', params)
// 获取所有机构信息
const getBranchs = () => getResourceNoPara('/admin/branch/tree')
// 获取所有一级机构信息
const fetchBranchs = () => getResourceNoPara('/admin/branch/fetchBranchs')
// 获取所有机构树信息
const getTreeNodes = () => getResourceNoPara('/admin/branch/treeNode')
// 批量导入
const upload = (params) => postAction('/admin/branch/upload', params)
// 导入模板下载
const download = () => downAction('/admin/branch/download', {
  responseType: 'blob',
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
})
export {
  saveBranch,
  updateBranch,
  getBranchById,
  deleteBranch,
  getBranchs,
  getTreeNodes,
  upload,
  download,
  fetchBranchs
}
