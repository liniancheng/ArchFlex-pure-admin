<template>
  <!-- 修改信息 -->
  <div class="update_pwd">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>修改信息</h2>
    </div>
    <a-form :form="formInformation" :hideRequiredMark="true">
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="姓名" has-feedback>
            <a-input
              v-decorator="[ 'userName', { initialValue: accountInfo.userName, rules: [
                { required: true, message: '请输入姓名' }] }
              ]"
              placeholder="请输入姓名"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="手机" has-feedback>
            <a-input
              v-decorator="[ 'userMobTel', { initialValue: accountInfo.userMobTel, rules: [
                { required: true, message: '请输入电话' },
                { validator: validatePhone }
              ] } ]"
              placeholder="请输入手机" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="邮箱" has-feedback>
            <a-input
              v-decorator="[ 'userEmail', { initialValue: accountInfo.userEmail, rules: [
                { required: true, message: '请输入邮箱' },
                { validator: validateMail }
              ] } ]"
              placeholder="请输入邮箱"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item :span="24" class="form_btn">
            <a-button type="primary" @click="handleInfoSubmit">确认</a-button>
            <a-button type="primary" @click="handleInfoReset">重置</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </div>
</template>
<script>
import { modifyAccountInfo } from '@/api/admin/account'
import events from '@/utils/events'
export default {
  name: 'PersonMessage',
  props: {
    accountInfo: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      // 账户信息
      mdl: {},
      // 修改密码/信息 布局
      formItemLayout: {
        labelCol: { xs: { span: 24 }, sm: { span: 6 } },
        wrapperCol: { xs: { span: 24 }, sm: { span: 14 } }
      },
      // 修改个人信息表单
      formInformation: this.$form.createForm(this, { name: 'dynamic_rule2' })
    }
  },
  methods: {
    validateMail (rule, value, callback) {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (value && !regEmail.test(value)) {
        callback(new Error('邮箱格式不正确，请检查!'))
      } else {
        callback()
      }
    },
    validatePhone (rule, value, callback) {
      const regPhone = /^1[3456789]\d{9}$/
      if (value && !regPhone.test(value)) {
        callback(new Error('手机格式不正确，请检查!'))
      } else {
        callback()
      }
    },
    // -------------------------修改个人信息-----------------------------
    handleInfoSubmit (e) {
      e.preventDefault()
      this.formInformation.validateFieldsAndScroll((err, values) => {
        if (!err) {
          modifyAccountInfo(Object.assign({ ...this.mdl, ...values })).then(res => {
            this.handleInfoResponse(res, values)
          })
        }
      })
    },
    handleInfoResponse (res, values) {
      if (res.code === 0) {
        this.accountInfo.userName = values.userName
        this.accountInfo.userEmail = values.userEmail
        this.accountInfo.userMobTel = values.userMobTel
        this.$message.success('修改成功！')
        events.$emit('OnAccountInfoChange', this.accountInfo)
      } else {
        this.$message.error(res.message)
      }
    },
    handleInfoReset () {
      this.formInformation.resetFields()
    }
  }
}
</script>
