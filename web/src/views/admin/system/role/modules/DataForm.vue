<template>
  <a-modal
    :title="modalTitle"
    width="60%"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"

  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-card :bordered="true" :bodyStyle="cardBodyStyle" title="基本信息">
              <a-form-item label="角色名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="角色名称"
                  v-decorator="['roleName', { initialValue: mdl.roleName, rules: [
                    { required: true, message: '请输入角色名称！' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }
                  ] }]"
                />
              </a-form-item>
              <a-form-item label="角色代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  :disabled="type===2"
                  placeholder="角色代码"
                  v-decorator="['roleCode', { initialValue: mdl.roleCode, rules: [
                    { required: true, message: '请输入角色代码！' },
                    {
                      pattern: /^[a-zA-Z0-9_]{1,50}$/,
                      message:i18nRenderByParams('prompt.codeAndLength', '50')
                    }
                  ]}]"
                />
              </a-form-item>
              <a-form-item label="状态" :labelCol="labelCol" v-if="type===2" :wrapperCol="wrapperCol">
                <a-radio-group v-model="mdl.delFlag">
                  <a-radio-button value="0">有效</a-radio-button>
                  <a-radio-button value="1">无效</a-radio-button>
                </a-radio-group>
              </a-form-item>
            </a-card>
          </a-col>
          <a-col :span="12">
            <a-card :bordered="true" :bodyStyle="cardBodyStyle" title="资源授权">
              <div style="height:475px;overflow-y:auto;">
                <vue-scroll>
                  <div>
                    <a-radio-group v-model="expandAll" @change="switchDefaultExpandAll" >
                      <a-radio-button value="1">展开所有</a-radio-button>
                      <a-radio-button value="2">合并所有</a-radio-button>
                    </a-radio-group>

                    <a-tree
                      v-if="roleTrees.length"
                      :defaultExpandAll="defaultExpandAll"
                      checkable
                      :show-line="showLine"
                      :checkStrictly="checkStrictly"
                      :tree-data="roleTrees"
                      @check="onCheck"
                      height="500"
                      v-model="treeValues"
                    >
                    </a-tree>
                  </div>
                </vue-scroll>
              </div>
            </a-card>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { i18nRenderByParams } from '@/locales'
import { Tree } from 'ant-design-vue'
import { saveRole, updateRole, getMenusByRole } from '@/api/admin/role/index'
import { getTreeNodes } from '@/api/admin/menu/index'
export default {
  components: {
    Tree
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 12 }
      },
      cardBodyStyle: { height: '550px' },
      visible: false,
      checkStrictly: true,
      confirmLoading: false,
      mdl: {},
      sysRoleVoList: [],
      roleList: [],
      modalTitle: '',
      type: 1,
      // 初始值
      roleTrees: [],
      treeValues: [],
      confirmCheckedKeys: [],
      checkedKeys: [],
      defaultExpandAll: false,
      expandAll: '2',
      showLine: true,
      form: this.$form.createForm(this)
    }
  },
  mounted () {
    this.loadTreeList()
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.treeValues = []
      this.modalTitle = '新增 - 角色'
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, {})
      this.sysRoleVoList = []
      this.confirmCheckedKeys = []
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 角色'
      this.mdl = Object.assign({}, record)
      this.initTreeList(record.roleId)
      this.visible = true
      this.type = 2
    },
    initTreeList (roleId) {
      this.treeValues = []
      const that = this
      getMenusByRole(roleId).then((res) => {
        if (res.code === 0) {
          if (res.data) {
            res.data.map(function (item) {
              that.treeValues.push(item.menuId)
            })
          }
        }
      })
    },
    loadTreeList () {
      this.roleTrees = []
      const that = this
      getTreeNodes().then((res) => {
        if (res.code === 0) {
            that.roleTrees = res.data
        }
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
            values.menuIds = this.confirmCheckedKeys.checked
            if (this.type === 1) {
              saveRole(Object.assign({ ...this.mdl, ...values })).then(res => {
                this.handleResponse(res)
              })
            } else {
              updateRole(Object.assign({ ...this.mdl, ...values })).then(res => {
                this.handleResponse(res)
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
     contains (a, obj) {
      var i = a.length
      while (i--) {
        if (a[i].key === obj) {
          return i
        }
      }
      return false
    },
    switchDefaultExpandAll (e) {
      this.confirmLoading = true
      if (e.target.value === '2') {
        this.defaultExpandAll = false
      } else if (e.target.value === '1') {
        this.defaultExpandAll = true
      }
      this.loadTreeList()
      this.confirmLoading = false
    },
    onCheck (checkedKeys, info) {
        this.confirmCheckedKeys = checkedKeys
    }
  }
}
</script>
