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
        <a-form-item :label="propertyName.name" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :placeholder="propertyName.name"
            v-decorator="['instanceName', { initialValue: mdl.instanceName, rules: [{
              required: true,
              message: '请输入'+propertyName.name+'！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.rmk" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea :placeholder="propertyName.rmk" v-decorator="[ 'instanceRmk', { initialValue: mdl.instanceRmk} ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/workflow/instance/index'
import { i18nRenderByParams } from '@/locales'
const functionName = '工作流实例'
export default {
  data () {
    return {
      propertyName: {
        name: '实例名称',
        rmk: '实例描述'
      },
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
    add (workflowId) {
      this.modalTitle = '新增 - ' + functionName
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, { workflowId: workflowId })
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
