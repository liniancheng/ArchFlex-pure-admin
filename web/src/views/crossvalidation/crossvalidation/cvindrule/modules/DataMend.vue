<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    okText="保存"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="指标名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['indNm', { initialValue: mdl.indNm }]"/>
        </a-form-item >
        <a-form-item label="系统" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['sysNm', { initialValue: mdl.sysNm }]"/>
        </a-form-item>
        <a-form-item label="频度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            style="width: 100%"
            v-decorator="['dateVal', {initialValue: mdl.dateVal }]"
          >
            <a-select-option value="D">
              日
            </a-select-option>
            <a-select-option value="W">
              周(日)
            </a-select-option>
            <a-select-option value="T">
              旬
            </a-select-option>
            <a-select-option value="M">
              月
            </a-select-option>
            <a-select-option value="Q">
              季
            </a-select-option>
            <a-select-option value="HY">
              半年
            </a-select-option>
            <a-select-option value="Y">
              年
            </a-select-option>
            <a-select-option value="P">
              人行日
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['orgNm', { initialValue: mdl.orgNm }]"/>
        </a-form-item >
        <a-form-item label="是否可用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            v-decorator="['state', { initialValue: mdl.state }]"
          >
            <a-select-option value="1"> 是 </a-select-option>
            <a-select-option value="0"> 否 </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="sql" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            :auto-size="{ minRows: 3, maxRows: 5 }"
            v-decorator="['chkSql', { initialValue: mdl.chkSql, rules: [{ required: true, message: '不能为空' }]}]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { updateById } from '@/api/crossvalidation/cvindrule/index'

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
      form: this.$form.createForm(this)
    }
  },
  methods: {
    edit (record) {
      this.modalTitle = '编辑校验规则'
      this.mdl = Object.assign({}, record)
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
            updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
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
