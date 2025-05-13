<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <template slot="footer">
      <a-button @click="handleCancel">取消</a-button>
      <a-button style="margin-left: 8px" type="primary" @click="handleSubmit">确定</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :label="propertyName.name" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :placeholder="propertyName.name"
            v-decorator="['workflowName', { initialValue: mdl.workflowName, rules: [{
              required: true,
              message: '请输入'+propertyName.name+'！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.code" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :disabled="type === 2"
            :placeholder="propertyName.code"
            v-decorator="['workflowCode', { initialValue: mdl.workflowCode, rules: [{
              required: true,
              message: '请输入'+propertyName.code+'！'
            },{
              pattern:/^[_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.codeAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.url" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :placeholder="propertyName.url"
            v-decorator="['detailUrl', { initialValue: mdl.detailUrl, rules: [{
              required: false,
              message: '请输入'+propertyName.url+'！'
            }]
            }]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.srv" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :placeholder="propertyName.srv"
            v-decorator="['finishService', { initialValue: mdl.finishService, rules: [{
              required: false,
              message: '请输入'+propertyName.srv+'！'
            }]
            }]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.rmk" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea :placeholder="propertyName.rmk" v-decorator="[ 'workflowRmk', { initialValue: mdl.workflowRmk} ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { saveWorkflow, updateWorkflowById } from '@/api/admin/workflow/workflow/index'
import { i18nRenderByParams } from '@/locales'
const functionName = '工作流'
export default {
  data () {
    return {
      propertyName: {
        name: '流程名称',
        code: '流程标识',
        url: '详情地址',
        srv: '回调服务',
        rmk: '流程描述'
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
    add (typeId) {
      this.modalTitle = '新增 - ' + functionName
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, { typeId: typeId })
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
            saveWorkflow(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            updateWorkflowById(Object.assign({ ...this.mdl, ...values })).then(res => {
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
