<template>
  <a-modal
    :title="modalTitle"
    :width="1040"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <div>
      <a-card :bordered="false">
        <div class="table-page-search-wrapper">
          <a-form layout="inline" :form="form">
            <a-row :gutter="48">
              <template>
                <a-col :md="8" :sm="24">
                  <a-form-item label="类型">
                    <a-select placeholder="请选择" default-value="0" v-model="mdl.authType">
                      <a-select-option value="role">角色</a-select-option>
                      <a-select-option value="user">用户</a-select-option>
                      <a-select-option value="role_macro">角色宏</a-select-option>
                      <a-select-option value="user_macro">用户宏</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </template>
              <a-col :md="8" :sm="24" v-if="mdl.authType === 'user'">
                <a-form-item label="登录名">
                  <a-input v-model="mdl.loginName" placeholder="请输入登录名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24" v-if="mdl.authType === 'role'">
                <a-form-item label="选择角色">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    placeholder="请选择角色"
                    optionFilterProp="children"
                    v-model="sysRoleVoList"
                    :getPopupContainer="(target) => target.parentNode">
                    <a-select-option v-for="(role,roleindex) in roleList" :key="roleindex.toString()" :value="role.roleId">
                      {{ role.roleName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24" v-if="mdl.authType === 'role_macro'">
                <a-form-item label="选择宏">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    placeholder="请选择宏变量"
                    optionFilterProp="children"
                    v-model="roleWorkflowMacroList"
                    :getPopupContainer="(target) => target.parentNode">
                    <a-select-option v-for="(macro,macroindex) in roleMacroList" :key="macroindex.toString()" :value="macro.macroCode">
                      {{ macro.macroName }}({{ macro.macroCode }})
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24" v-if="mdl.authType === 'user_macro'">
                <a-form-item label="选择宏">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    placeholder="请选择宏变量"
                    optionFilterProp="children"
                    v-model="userWorkflowMacroList"
                    :getPopupContainer="(target) => target.parentNode">
                    <a-select-option v-for="(macro,macroindex) in userMacroList" :key="macroindex.toString()" :value="macro.macroCode">
                      {{ macro.macroName }}({{ macro.macroCode }})
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                  <a-button style="margin-left: 8px" type="primary" icon="plus" @click="handleAdd">添加</a-button>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>

        <a-table
          ref="table"
          size="default"
          rowKey="authId"
          :pagination="false"
          :columns="columns"
          :data-source="data"
          :loading="loading"
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record)">
                <a class="table_orange">【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table>

      </a-card>
    </div>
  </a-modal>
</template>

<script>
import { fetch, save, removeById, roles, macros } from '@/api/admin/workflow/workflow/auth'

export default {
  name: 'TableList',
  data () {
    return {
      modalTitle: '',
      visible: false,
      confirmLoading: false,
      data: [],
      loading: false,
      roleList: [],
      sysRoleVoList: [],
      roleMacroList: [],
      roleWorkflowMacroList: [],
      userMacroList: [],
      userWorkflowMacroList: [],
      mdl: { authType: 'role' },
      columns: [
        {
            title: '权限类型',
            dataIndex: 'authType',
            key: 'authType',
            customRender: function (text) {
              if (text === 'role') {
                return '角色'
              } else if (text === 'user') {
                return '用户'
              } else if (text === 'role_macro') {
                return '角色宏'
              } else if (text === 'user_macro') {
                return '用户宏'
              } else {
                return ''
              }
            }
        },
        {
            title: '角色(宏)或用户名(宏)',
            dataIndex: 'objName',
            key: 'objName'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 100,
          fixed: 'right',
          align: 'center',
          scopedSlots: {
            customRender: 'action'
         }
        }
      ],
      selectedRowKeys: [],
      selectedRows: [],
      form: this.$form.createForm(this)
    }
  },
  methods: {
    handleShow (workflowId, nodeId, nodeName) {
      this.mdl.nodeId = nodeId
      this.mdl.workflowId = workflowId
      this.modalTitle = nodeName + ' - 权限'
      this.visible = true
      this.initialRoleList()
      this.initialMacroList('role_macro')
      this.initialMacroList('user_macro')
      this.loadData()
    },
    handleAdd () {
      let flag = true
      if (this.mdl.authType === 'role') {
        if (this.sysRoleVoList.length === 0) {
            flag = false
        } else {
            this.mdl.objId = this.sysRoleVoList.join(',')
        }
      } else if (this.mdl.authType === 'role_macro') {
        if (this.roleWorkflowMacroList.length === 0) {
            flag = false
        } else {
            this.mdl.objId = this.roleWorkflowMacroList.join(',')
        }
      } else if (this.mdl.authType === 'user_macro') {
        if (this.userWorkflowMacroList.length === 0) {
            flag = false
        } else {
            this.mdl.objId = this.userWorkflowMacroList.join(',')
        }
      } else {
         if (this.mdl.loginName === '') {
            flag = false
         } else {
             this.mdl.objId = this.mdl.loginName
         }
      }
      if (flag) {
        this.confirmLoading = true
        save(this.mdl).then(res => {
          if (res.code === 0) {
            this.$message.success('添加成功!')
            this.handleOk()
            if (this.mdl.authType === 'role') {
                this.sysRoleVoList = []
                this.initialRoleList()
            } else if (this.mdl.authType === 'role_macro') {
                this.roleWorkflowMacroList = []
                this.initialMacroList(this.mdl.authType)
            } else if (this.mdl.authType === 'user_macro') {
                this.userWorkflowMacroList = []
                this.initialMacroList(this.mdl.authType)
            } else {
                this.loginName = ''
            }
          } else {
            this.$message.error('添加失败：' + res.message)
          }
          this.confirmLoading = false
        })
      }
    },
    handleDelete (record) {
        removeById(record.authId).then(res => {
          if (res.code === 0) {
            if (record.authType === 'role') {
              this.initialRoleList()
            } else if (record.authType === 'role_macro') {
              this.initialMacroList(record.authType)
            } else if (record.authType === 'user_macro') {
              this.initialMacroList(record.authType)
            }
            this.$message.success('删除成功!')
            this.handleOk()
          } else {
            this.$message.error('删除失败：' + res.message)
          }
        })
    },
    handleOk () {
      this.loadData()
    },
    initialRoleList () {
      roles(this.mdl.nodeId).then((res) => {
        if (res.code === 0) {
          this.roleList = res.data
        }
      })
    },
    initialMacroList (authType) {
      macros(this.mdl.workflowId, this.mdl.nodeId, authType).then((res) => {
        if (res.code === 0) {
          if (authType === 'role_macro') {
            this.roleMacroList = res.data
          } else if (authType === 'user_macro') {
            this.userMacroList = res.data
          }
        }
      })
    },
    handleCancel () {
      this.visible = false
    },
    loadData () {
      this.loading = true
      fetch({ nodeId: this.mdl.nodeId })
        .then(res => {
          this.data = res.data.records
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
</script>
