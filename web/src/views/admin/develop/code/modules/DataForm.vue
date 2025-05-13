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
        <a-form-item label="包名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="包名"
            v-decorator="['packageName', { initialValue: mdl.packageName, rules: [{
              required: true,
              message: '请输入包名！'
            }] }]"
          />
        </a-form-item>
        <a-form-item label="模块名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="模块名"
            v-decorator="['moduleName', { initialValue: mdl.moduleName, rules: [{
              required: true,
              message: '请输入模块名！'
            }] }]"
          />
        </a-form-item>
        <a-form-item label="功能英文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="功能英文名"
            v-decorator="['funcName', { initialValue: mdl.funcName, rules: [{
              required: true,
              message: '请输入功能英文名！'
            }] }]"
          />
        </a-form-item>
        <a-form-item label="功能中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="功能中文名"
            v-decorator="['funcDesc', { initialValue: mdl.funcDesc, rules: [{
              required: true,
              message: '请输入功能中文名！'
            }] }]"
          />
        </a-form-item>
        <a-form-item label="作者名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="作者名"
            v-decorator="['authorName', { initialValue: mdl.authorName, rules: [{
              required: true,
              message: '请输入作者名！'
            }] }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { code } from '@/api/gen/index'

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
    edit (tableName) {
      this.modalTitle = tableName + ' - 生成代码'
      this.visible = true
      this.mdl = Object.assign({}, { packageName: 'com.adtec.rdc.base', authorName: 'adtec', tableNames: [tableName] })
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
          code(Object.assign({ ...this.mdl, ...values })).then(res => {
            this.handleResponse(res)
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleResponse (res) {
      const content = res
      const blob = new Blob([content])
      const fileName = 'code.zip'
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL 对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, fileName)
      }
      this.confirmLoading = false
      this.visible = false
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
