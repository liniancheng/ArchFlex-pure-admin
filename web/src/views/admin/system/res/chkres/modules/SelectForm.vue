<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="数据源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-model="mdl.dbType"
          />
        </a-form-item>
        <a-form-item label="连接驱动" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-model="mdl.lkDriver"
          />
        </a-form-item>
        <a-form-item label="连接URL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-model="mdl.lkUrl"
          />
        </a-form-item>
        <a-form-item label="连接用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-model="mdl.lkUser"
          />
        </a-form-item>
        <a-form-item label="连接密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password
            readOnly
            v-model="mdl.lkPassword"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
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
      show (record) {
        this.modalTitle = '资源配置信息'
        this.visible = true
        this.mdl = Object.assign({}, record)
        if (this.mdl.dbType === '1') {
          this.mdl.dbType = 'mysql'
        }
        if (this.mdl.dbType === '2') {
          this.mdl.dbType = 'db2'
        }
        if (this.mdl.dbType === '3') {
          this.mdl.dbType = 'orcle'
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
