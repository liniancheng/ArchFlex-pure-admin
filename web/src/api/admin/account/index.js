import { getAction, post, postAction } from '@/api/admin/manage'
// 获取用户信息
const getAccountInfo = (params) => getAction('/admin/user/accountInfo')
// 获取所有任务消息
const getTaskPage = (params) => getAction('/admin/appTask/page', params)
// 修改用户信息
const modifyAccountInfo = (params) => postAction('/admin/user/modifyAccountInfo', params)
// 修改用户密码
const modifyLoginPwd = (oldPwd, newPwd) => post('/admin/user/modifyLoginPwd', oldPwd, newPwd)
// 获取公告和消息
const getAllMessages = (params) => getAction('/admin/base/person/allMessages', params)
// 获取公告
const getMessages = (params) => getAction('/admin/base/person/messages', params)
// 获取消息
const getAnnos = (params) => getAction('/admin/base/person/annos', params)
// 标记已读
const readMessage = (type, id) => postAction('/admin/base/person/readMessage/' + type + '/' + id)
// 标记删除
const deleteMessage = (type, id) => postAction('/admin/base/person/deleteMessage/' + type + '/' + id)
// 获取任务
const getTasks = (params) => getAction('/admin/base/person/tasks', params)
// 审批任务
const operTask = (params) => postAction('/admin/base/person/operTask', params)

export {
  getAccountInfo,
  readMessage,
  deleteMessage,
  modifyAccountInfo,
  modifyLoginPwd,
  getTaskPage,
  getAllMessages,
  getMessages,
  getAnnos,
  getTasks,
  operTask
}
