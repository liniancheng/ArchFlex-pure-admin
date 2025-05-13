<template>
  <a-modal
    :title="modalTitle"
    width="800px"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"

    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col span="12">
            <a-form-item label="资源名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="资源名称"
                v-decorator="['name', { initialValue: mdl.name, rules: [
                  { required: true, message: '请输入资源名称！' },
                  {
                    pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                    message:i18nRenderByParams('prompt.nameAndLength', '50')
                  }
                ] }]" />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="资源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                @change="change"
                v-decorator="['type', { initialValue: mdl.type, rules: [
                  { required: true, message: '请选择资源类型！' },
                ]}]" >
                <a-radio value="0">菜单</a-radio>
                <a-radio value="1">按钮</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row v-if="menuType !== '1'">
          <a-col span="12">
            <a-form-item label="路径名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="路径名称"
                v-decorator="['routeName', { initialValue: mdl.routeName, rules: [
                  { required: true, message: '请输入资源名称！' },
                  {
                    pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-.]{1,50}$/,
                    message:i18nRenderByParams('prompt.nameAndLength', '50')
                  }
                ] }]" />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="资源图标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="点击选择图标" v-decorator="['icon', { initialValue: mdl.icon}]" >
                <a-icon slot="addonAfter" type="setting" @click="selectIcons" />
              </a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row v-if="menuType === '0'">
          <a-col span="12">
            <a-form-item label="隐藏菜单" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                placeholder="是否隐藏"
                v-decorator="['hideFlag', { initialValue: mdl.hideFlag, rules: [
                  { required: true, message: '请选择是否隐藏菜单！' }
                ]}]"
              >
                <a-radio value="1">是</a-radio>
                <a-radio value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="需要权限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                v-decorator="['authFlag', { initialValue: mdl.authFlag, rules: [
                  { required: true, message: '请选择是否是公共菜单！' }
                ]}]"
              >
                <a-radio value="1">是</a-radio>
                <a-radio value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12" v-if="menuType === '0'">
            <a-form-item label="缓存" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                v-decorator="['aliveFlag', { initialValue: mdl.aliveFlag, rules: [
                  { required: true, message: '请选择是否缓存！' }
                ]}]"
              >
                <a-radio value="1">是</a-radio>
                <a-radio value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col span="12" v-if="menuType === '1'">
            <a-form-item label="资源权限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="资源权限"
                v-decorator="['permission', { initialValue: mdl.permission, rules: [
                  { required: true, message: '请输入资源权限！' }
                ]}]" />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="排序权重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="排序权重"
                v-decorator="['sort', { initialValue: mdl.sort, rules: [{
                  required: true,
                  message: '请输入排序权重！'
                }]}]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="资源链接" :labelCol="langLabelCol" :wrapperCol="langWrapperCol" v-if="menuType !== '1'">
          <a-input
            placeholder="资源链接"
            v-decorator="['path', { initialValue: mdl.path, rules: [
              { required: true, message: '请输入资源链接！' },
              {
                pattern:'/+[^\s]*',
                message:'请输入正确的资源链接：/...'
              }
            ]}]" />
        </a-form-item>
        <a-form-item label="组件路径" :labelCol="langLabelCol" :wrapperCol="langWrapperCol" v-if="menuType !== '1'">
          <a-input
            placeholder="组件路径"
            v-decorator="['component', { initialValue: mdl.component, rules: [
              { required: true, message: '请输入组件路径！' },
              {
                pattern:'(views|layouts)/+[^\s]*',
                message:'请输入正确的组件路径：views/...或layouts/...'
              }
            ]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
    <icons @choose="handleIconChoose" @close="handleIconCancel" :iconChooseVisible="iconChooseVisible"></icons>
  </a-modal>
</template>

<script>
  import {
    saveMenu,
    updateMenu
  } from '@/api/admin/menu/index'
  import { i18nRenderByParams } from '@/locales'
  import Icons from './icon/Icons'
  export default {
    components: {
      Icons
    },
    data () {
      return {
        labelCol: { xs: { span: 24 }, sm: { span: 6 } },
        wrapperCol: { xs: { span: 24 }, sm: { span: 16 } },
        langLabelCol: { xs: { span: 24 }, sm: { span: 3 } },
        langWrapperCol: { xs: { span: 24 }, sm: { span: 20 } },
        visible: false,
        confirmLoading: false,
        mdl: {},
        modalTitle: '',
        type: 1,
        menuType: '0',
        // 初始值
        iconChooseVisible: false,
        // 树形选框的option选项
        options: [],
        form: this.$form.createForm(this)
      }
    },
    methods: {
      i18nRenderByParams,
      selectIcons () {
        this.iconChooseVisible = true
      },
      handleIconCancel () {
        this.iconChooseVisible = false
      },
      handleIconChoose (value) {
        this.mdl.icon = value
        this.iconChooseVisible = false
      },
      add (pId) {
        this.modalTitle = '新增 - 菜单'
        this.mdl = Object.assign({}, { sort: 0, parentId: pId, delFlag: '0', type: '0', hideFlag: '0', authFlag: '1', aliveFlag: '1' })
        this.menuType = '0'
        this.visible = true
        this.type = 1
      },
      edit (record) {
        this.modalTitle = '修改 - 菜单'
        this.mdl = Object.assign({}, record)
        this.menuType = record.type
        this.visible = true
        this.type = 2
      },
      close () {
        this.$emit('close')
        this.visible = false
      },
      handleSubmit () {
        const {
          form: {
            validateFields
          }
        } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            if (this.type === 1) {
              saveMenu(Object.assign({ ...this.mdl,
                ...values
              })).then(res => {
                this.handleResponse(res)
              })
            } else {
              updateMenu(Object.assign({ ...this.mdl,
                ...values
              })).then(res => {
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
          this.form.resetFields()
          this.$emit('ok')
        } else {
          this.$message.error(res.message)
        }
      },
      handleCancel () {
        this.visible = false
        this.confirmLoading = false
        this.form.resetFields()
      },
      change (e) {
        this.menuType = e.target.value
        // 第一遍修改可能渲染失败，需要重新赋值一下,不清楚为啥
        setTimeout(() => {
          this.menuType = e.target.value
        }, 100)
      }
    }
  }
</script>
