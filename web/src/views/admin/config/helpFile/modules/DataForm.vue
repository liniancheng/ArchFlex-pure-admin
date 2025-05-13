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
        <a-form-item
          label=""
          :labelCol="{ xs: { span: 24 }, sm: { span: 2 } }"
          :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
        >
          <a-upload
            name="file"
            :multiple="true"
            :remove="onRemove"
            :before-upload="beforeUpload"
            :fileList="fileList"
            :customRequest="customRequest"
          >
            <a-button type="primary"> <a-icon type="upload" />选择文档</a-button>
          </a-upload>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save } from '@/api/config/helpFile/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      form: this.$form.createForm(this),
      fileList: []
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.fileList = []
      this.modalTitle = '上传 - 帮助文档'
      this.visible = true
    },
    close () {
      this.visible = false
    },
    handleSubmit () {
      const that = this
      if (this.fileList.length === 0) {
        that.$message.error('请选择上传文档!')
      } else {
        that.confirmLoading = true
        save(that.formData)
          .then((res) => {
            if (res.code !== 0) {
              that.$message.error(res.message)
            } else {
              that.$message.success('上传成功!')
              that.$emit('ok')
              that.close()
            }
            that.confirmLoading = false
          })
          .catch(function () {
            that.$message.error('上传失败：未知错误')
            that.confirmLoading = false
          })
      }
    },
    beforeUpload (file) {
      const that = this
      return new Promise((resolve, reject) => {
        if (file.size / 1024 / 1024 > 100) {
          that.$message.error(file.name + '大于100M，请检查！')
        } else {
          that.formData = new FormData()
          that.formData.append('file', file)
          resolve()
        }
      })
    },
    customRequest (data) {
      this.fileList = [{ uid: data.file.uid, name: data.file.name, status: 'done' }]
    },
    onRemove () {
      this.fileList = []
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
    }
  }
}
</script>
