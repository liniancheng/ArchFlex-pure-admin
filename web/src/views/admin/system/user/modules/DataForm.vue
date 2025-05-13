<template>
  <a-modal
    :title="modalTitle"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"

  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="登录名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :disabled="type === 2 ? true : false"
            placeholder="登录名"
            v-decorator="[
              'loginName',
              {
                initialValue: mdl.loginName,
                rules: [
                  {
                    required: true,
                    message: '请输入用户名！'
                  },
                  {
                    pattern: /^[_a-zA-Z0-9-.]{1,50}$/,
                    message:i18nRenderByParams('prompt.codeAndLength', '50')
                  }
                ]
              }
            ]"
          />
        </a-form-item>
        <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="用户名"
            v-decorator="[
              'userName',
              {
                initialValue: mdl.userName,
                rules: [
                  {
                    required: true,
                    message: '请输入用户名！'
                  },
                  {
                    pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                    message:i18nRenderByParams('prompt.nameAndLength', '50')
                  }
                ]
              }
            ]"
          />
        </a-form-item>
        <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="邮箱"
            v-decorator="[
              'userEmail',
              {
                initialValue: mdl.userEmail,
                rules: [
                  {
                    required: true,
                    message: '请输入邮箱'
                  },
                  {
                    validator: validateMail
                  }
                ]
              }
            ]"
          />
        </a-form-item>
        <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="手机号"
            style="width: 100%"
            v-decorator="[
              'userMobTel',
              {
                initialValue: mdl.userMobTel,
                rules: [
                  {
                    required: true,
                    message: '请输入手机号！'
                  },
                  {
                    validator: validatePhone
                  }
                ]
              }
            ]"
          />
        </a-form-item>
        <a-form-item label="角色分配" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择用户角色"
            optionFilterProp="children"
            v-decorator="[
              'sysRoleVoList',
              {
                initialValue: sysRoleVoList,
                rules: [
                  {
                    required: true,
                    message: '请选用户角色！'
                  }
                ]
              }
            ]"
            @change="changeRole"
            :getPopupContainer="target => target.parentNode"
          >
            <a-select-option v-for="(role, roleindex) in roleList" :key="roleindex.toString()" :value="role.roleId">
              {{ role.roleName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="机构选择" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tree-select
            placeholder="请选择机构"
            v-decorator="[
              'branchNo',
              {
                initialValue: mdl.branchNo,
                rules: [
                  {
                    required: true,
                    message: '请选择机构！'
                  }
                ]
              }
            ]"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            tree-default-expand-all
            allow-clear
            @change="onChange"
            style="width: 100%"
            :tree-data="options"
            :show-checked-strategy="SHOW_PARENT"
            search-placeholder="Please select"
          />
        </a-form-item>
        <a-form-item v-if="type === 2" label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-model="mdl.delFlag">
            <a-radio-button value="0">有效</a-radio-button>
            <a-radio-button value="1">无效</a-radio-button>
            <a-radio-button value="9">锁定</a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { i18nRenderByParams } from '@/locales'
import { TreeSelect } from 'ant-design-vue'
import { saveUser, getRoles, updateUser } from '@/api/admin/user/index'
import { getTreeNodes } from '@/api/admin/branch/index'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      visible: false,
      modalWidth: window.innerWidth * 0.6,
      confirmLoading: false,
      mdl: {},
      sysRoleVoList: [],
      roleList: [],
      modalTitle: '',
      type: 1,
      // 初始值
      // branchNo: [],
      // 树形选框的option选项
      options: [],
      disableSubmit: false,
      SHOW_PARENT,
      form: this.$form.createForm(this)
    }
  },
   mounted () {
    this.initialRoleList()
    this.loadTreeList()
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.sysRoleVoList = []
      this.modalTitle = '新增 - 用户'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.sysRoleVoList = []
      this.modalTitle = '修改 - 用户'
      this.mdl = Object.assign({}, record)
      this.makeRoles(this.mdl.sysRoleVoList)
      this.visible = true
      this.type = 2
    },
    makeRoles (results) {
      const that = this
      if (results && results !== undefined) {
          results.forEach(function (item, index, results) {
                that.sysRoleVoList.push(item.roleId)
        })
      }
    },
    initialRoleList () {
      getRoles().then(res => {
        if (res.code === 0) {
          this.roleList = res.data.records
        } else {
          console.log(res.message)
        }
      })
    },
    loadTreeList () {
      this.options = []
      const that = this
      getTreeNodes().then(res => {
        if (res.code === 0) {
          that.options = res.data
        } else {
          console.log(res.message)
        }
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleSubmit () {
      const that = this
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.branchNo = this.mdl.branchNo
          const roleList = []
          const sysRoles = that.sysRoleVoList
          sysRoles.forEach(function (item, index, sysRoles) {
            const role = {
              roleId: item
            }
            roleList.push(role)
          })
          values.sysRoleVoList = roleList
          if (this.type === 1) {
            values.delFlag = '0'
            saveUser(Object.assign({ ...this.mdl, ...values }))
              .then(res => {
                this.handleResponse(res)
              })
              .finally(() => {
                this.confirmLoading = false
              })
          } else {
            updateUser(Object.assign({ ...this.mdl, ...values }))
              .then(res => {
                this.handleResponse(res)
              })
              .finally(() => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.code === 0) {
        this.$message.success('保存成功！')
        this.visible = false
        this.$emit('ok')
        this.form.resetFields()
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
      this.form.resetFields()
    },
    onChange (v) {
      if (v !== undefined) {
        this.mdl.branchNo = v
      } else {
        this.mdl.branchNo = ''
      }
    },
    changeRole (v) {
      this.sysRoleVoList = v
    },
    validateMail (rule, value, callback) {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (value && !regEmail.test(value)) {
        callback(new Error('邮箱格式不正确，请检查!'))
      } else {
        callback()
      }
    },
    validatePhone (rule, value, callback) {
      const regPhone = /^1[3456789]\d{9}$/
      if (value && !regPhone.test(value)) {
        callback(new Error('手机格式不正确，请检查!'))
      } else {
        callback()
      }
    }
  }
}
</script>
