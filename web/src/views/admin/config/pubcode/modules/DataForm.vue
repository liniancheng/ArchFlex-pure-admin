<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="参数名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入参数名称"
            v-decorator="['paramName', { initialValue: mdl.paramName,rules: [
              { required: true, message: '请输入参数名称' },
              {
                pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-.]{1,50}$/,
                message:i18nRenderByParams('prompt.nameAndLength', '50')
              }]
            }]"
          />
        </a-form-item>
        <a-form-item label="参数值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入参数值"
            v-decorator="['paramValue', { initialValue: mdl.paramValue, rules: [ { required: true, message: '请输入参数值' }] }]" />
        </a-form-item>
        <a-form-item label="参数描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea allow-clear placeholder="请输入类型描述" v-decorator="['paramRmk', { initialValue: mdl.paramRmk }]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save, updateById } from '@/api/config/pubcode/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: { xs: { span: 24 }, sm: { span: 6 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 15 } },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      type: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.modalTitle = '新增 - 参数'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 参数'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
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
          if (this.type === 1) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            }).finally(() => {
                this.confirmLoading = false
              })
          } else {
            updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            }).finally(() => {
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
        this.$message.success(res.message)
        this.visible = false
        this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
    }
  }
}
</script>
