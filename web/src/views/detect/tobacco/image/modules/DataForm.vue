<template>
  <a-modal
    :title="modalTitle"
    :width="modalWidth"
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
            placeholder="请输入指标名称"
            v-decorator="['indNm', {initialValue: mdl.indNm, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="指标释义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="请输入指标释义"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            v-decorator="['indExpr', {initialValue: mdl.indExpr, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="参考指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入参考指标"
            v-decorator="['busDirec', {initialValue: mdl.busDirec, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            style="width: 120px"
            v-decorator="['currencyVal', {initialValue: this.currencyVal, rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option value="CNY" >
              人民币
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="频度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            style="width: 120px"
            v-decorator="['dateVal', {initialValue: this.dateVal, rules: [{ required: true, message: '不能为空' }] }]"
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
            <a-select-option value="H">
              半年
            </a-select-option>
            <a-select-option value="Y">
              年
            </a-select-option>
            <a-select-option value="RHD">
              人行日
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save } from '@/api/crossvalidation/cvchkind/index'

export default {
  data () {
    return {
      // 币种默认值
      currencyVal: 'CNY',
      // 频度默认值
      dateVal: 'M',
      labelCol: {
        xs: { span: 24 }, sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 15 }
      },
       modalWidth: window.innerWidth * 0.5,
      visible: false,
      confirmLoading: false,
      mdl: { parentNo: '' },
      modalTitle: '',
      form: this.$form.createForm(this)
    }
  },
  methods: {
    addOne () {
      this.modalTitle = '添加一级指标'
      this.visible = true
      this.mdl.parentNo = '-1'
      this.mdl.indLevel = '1'
    },
    add (record) {
      this.modalTitle = '添加下级指标'
      this.visible = true
      this.mdl.parentNo = record.indNo
      this.mdl.parentLevel = record.indLevel
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.data) {
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
