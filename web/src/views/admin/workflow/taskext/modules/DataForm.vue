<template>
  <a-modal
    :title="modalTitle"
    :width="640"
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
        <a-form-item label="任务名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="任务名称"
            v-decorator="['extName', { initialValue: mdl.extName, rules: [{
              required: true,
              message: '请输入任务名称！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }] }]"
          />
        </a-form-item>
        <a-form-item label="跳转URL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="跳转URL"
            v-decorator="['extUrl', { initialValue: mdl.extUrl, rules: [{
              required: true,
              message: '请输入跳转URL！'
            }] }]"
          />
        </a-form-item>
        <a-form-item label="待处理SQL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="待处理SQL"
            v-decorator="[ 'undoSql', { initialValue: mdl.undoSql, rules: [{
              required: true,
              message: '请输入待处理SQL！'
            }] } ]" />
        </a-form-item>
        <a-form-item label="已处理SQL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="已处理SQL"
            v-decorator="[ 'doneSql', { initialValue: mdl.doneSql, rules: [{
              required: true,
              message: '请输入已处理SQL！'
            }] } ]" />
        </a-form-item>
        <a-form-item label="任务描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea placeholder="任务描述" v-decorator="[ 'extRmk', { initialValue: mdl.extRmk} ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/workflow/taskext/index'
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
      this.modalTitle = '新增 - 自定义任务'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 自定义任务'
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
