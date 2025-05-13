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
        <a-form-item label="布局名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="布局名称"
            v-decorator="['layName', { initialValue: mdl.layName,rules: [
              { required: true, message: '请输入 布局名称'},
              {
                pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                message:i18nRenderByParams('prompt.nameAndLength', '50')
              }
            ]
            }]"
          />
        </a-form-item>
        <a-form-item label="默认布局" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group
            placeholder="布局类型"
            v-decorator="['layType', { initialValue: mdl.layType,rules: [
              { required: true, message: '请选中是否是默认布局'}]
            }]"
          >
            <a-radio value="0">是</a-radio>
            <a-radio value="1">否</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="布局权重" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            :min="0"
            style="width:100%;"
            placeholder="布局权重"
            v-decorator="['layLevel', { initialValue: mdl.layLevel,rules: [
              { required: true, message: '请输入布局权重'}] }]"/>
        </a-form-item>
        <a-form-item label="布局描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="布局描述"
            v-decorator="['layRmk', { initialValue: mdl.layRmk }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save, updateById } from '@/api/admin/layout/layout/index'
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
      this.modalTitle = '新增 - 首页布局'
      this.visible = true
      this.mdl = Object.assign({}, { layType: '1', layLevel: 0 })
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - 首页布局'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.type === 1) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
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
        this.form.resetFields()
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
      this.confirmLoading = false
    }
  }
}
</script>
