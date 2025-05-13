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
        <a-form-item label="指标名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['indNm', { initialValue: mdl.indNm }]"/>
        </a-form-item >
        <a-form-item label="系统:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['sysNm', { initialValue: mdl.sysNm }]"/>
        </a-form-item>
        <a-form-item label="法人机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['orgNm', { initialValue: mdl.orgNm }]"/>
        </a-form-item>
        <a-form-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['currencyVal', { initialValue: mdl.currencyVal=='CNY'?'人民币':'' }]"/>
        </a-form-item>
        <a-form-item label="频度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['dateVal', { initialValue: this.dateVal }]"
          />
        </a-form-item>
        <a-form-item label="检验sql" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea :rows="4" readOnly v-decorator="['chkSql', { initialValue: mdl.chkSql }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  export default {
    data () {
      return {
        dateVal: '',
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
      select (record) {
        this.modalTitle = '规则展示'
        this.mdl = Object.assign({}, record)
        this.visible = true
        if (this.mdl.dateVal === 'D') {
          this.dateVal = '日'
        }
        if (this.mdl.dateVal === 'W') {
          this.dateVal = '周'
        }
        if (this.mdl.dateVal === 'T') {
          this.dateVal = '旬'
        }
        if (this.mdl.dateVal === 'M') {
          this.dateVal = '月'
        }
        if (this.mdl.dateVal === 'Q') {
          this.dateVal = '季'
        }
        if (this.mdl.dateVal === 'H') {
          this.dateVal = '半年'
        }
        if (this.mdl.dateVal === 'Y') {
          this.dateVal = '年'
        }
        if (this.mdl.dateVal === 'RHD') {
          this.dateVal = '人行日'
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
<style scoped>
.mdl{
      border:solid 1px #bfc6cc;
      width: 300px;
      height: 40px;
}
.sql{
  border:solid 1px #bfc6cc;
  width: 300px;
  height: 100%;
}
</style>
