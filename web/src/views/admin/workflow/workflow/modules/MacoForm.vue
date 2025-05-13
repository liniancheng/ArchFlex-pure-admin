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
        <a-form-item label="宏变量名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="宏变量名称"
            v-decorator="['macroName', { initialValue: mdl.macroName, rules: [{
              required: true,
              message: '请输入宏变量名称！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item label="宏变量标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="宏变量标识"
            v-decorator="['macroCode', { initialValue: mdl.macroCode, rules: [{
              required: true,
              message: '请输入宏变量标识！'
            },{
              pattern:/^[_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.codeAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item label="宏变量描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea placeholder="宏变量描述" v-decorator="[ 'macroRmk', { initialValue: mdl.macroRmk} ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/workflow/workflow/maco'
import { i18nRenderByParams } from '@/locales'
const functionName = '流程宏变量'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 15 }
      },
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
    add (workflowId, macroType) {
      this.modalTitle = '新增 - ' + functionName
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, { workflowId: workflowId, macroType: macroType })
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - ' + functionName
      this.form.resetFields()
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
        this.$message.success(res.message)
        this.visible = false
        this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
