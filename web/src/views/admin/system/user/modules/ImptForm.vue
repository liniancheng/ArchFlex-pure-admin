<template>
  <a-modal
    :title="modalTitle"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :footer="null"
    @cancel="handleCancel"

  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="模板下载" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-button type="default" icon="download" @click="handleDownload">下载导入模板</a-button>
        </a-form-item>
        <a-form-item label="已有数据" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-model="mdl.importType">
            <a-radio-button value="exit">中断</a-radio-button>
            <a-radio-button value="skip">跳过</a-radio-button>
            <a-radio-button value="update">覆盖</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="导入文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-upload
            name="file"
            :multiple="false"
            accept=".xlsx"
            :showUploadList="false"
            :customRequest="uploadRequest"
            @change="handleUploadChange"
          >
            <a-button type="default" icon="upload"> 点击上传文件</a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="导入结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <div style="width:400px;height:200px;overflow:auto;border:1px solid rgb(221, 221, 221);" v-html="mdl.importResult" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { download, upload } from '@/api/admin/user/impt'
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
    show () {
      this.modalTitle = '导入 - 用户'
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, { importType: 'exit', importResult: '' })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleCancel () {
      this.visible = false
    },
    handleDownload () {
      download('-1', '-1').then(res => {
          this.handleDownloadResponse(res)
      })
    },
    handleDownloadResponse (res) {
      const content = res
      const blob = new Blob([content])
      const fileName = '用户导入模板.xlsx'
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
    },
    uploadRequest (data) {
      this.confirmLoading = true
      this.mdl.importResult = ''
      const formData = new FormData()
      formData.append('file', data.file)
      upload(this.mdl.importType, formData).then(res => {
        this.form.resetFields()
        this.mdl.importResult = res.message
        this.confirmLoading = false
        this.$emit('ok')
      })
    },
    handleUploadChange (info) {
      if (info.file.status === 'done') {
        this.$message.success(`${info.file.name} 文件上传成功.`)
      } else if (info.file.status === 'error') {
        this.$message.error(`${info.file.name} 文件上传失败.`)
      }
    }
  }
}
</script>
