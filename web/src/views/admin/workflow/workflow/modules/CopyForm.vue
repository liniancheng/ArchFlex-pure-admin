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
        <a-form-item label="版本类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select placeholder="请选择版本类型" default-value="small" v-model="mdl.versionType">
            <a-select-option value="small">小版本升级</a-select-option>
            <a-select-option value="big">大版本升级</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="复制范围" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select placeholder="请选择复制范围" default-value="2" v-model="mdl.copyType">
            <a-select-option value="2">流程+节点+权限</a-select-option>
            <a-select-option value="1">流程+节点</a-select-option>
            <a-select-option value="0">流程</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { copyWorkflow } from '@/api/admin/workflow/workflow/index'
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
    show (workflowId, workflowName) {
      this.modalTitle = '复制 - ' + workflowName
      this.form.resetFields()
      this.mdl = { workflowId: workflowId, versionType: 'small', copyType: '2' }
      this.visible = true
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
          copyWorkflow(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
          })
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
