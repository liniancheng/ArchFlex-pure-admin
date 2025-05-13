<template>
  <!-- 修改密码 -->
  <div class="update_pwd">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>修改密码</h2>
    </div>
    <a-form :form="formPassword" :hideRequiredMark="true">
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="旧密码" has-feedback>
            <a-input-password
              v-decorator="[ 'oldPwd', { rules: [
                { required: true, message: '请输入旧密码!' }
              ] } ]" >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="新密码" has-feedback>
            <a-input-password
              placeholder="至少6位密码，区分大小写"
              v-decorator="[ 'password', { rules: [
                { required: true, message: '请输入新密码!' },
                { validator: handlePasswordLevel }
              ] } ]" >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password>
            <div :style="{ width: '260px' }" >
              <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span><a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " /></div>
            </div>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item v-bind="formItemLayout" label="确认密码" has-feedback>
            <a-input-password
              v-decorator="[ 'confirm', { rules: [
                { required: true, message: '请输入确认密码!' },
                { validator: compareToFirstPassword }
              ] } ]"
              type="password"
              @blur="handlePswdConfirmBlur" >
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input-password>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :xs="24" :sm="20" :md="18" :lg="16" :xl="12">
          <a-form-item :span="24" class="form_btn">
            <a-button type="primary" @click="handlePswdSubmit">确认</a-button>
            <a-button type="primary" @click="handlePswdReset">重置</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </div>
</template>
<script>
import { modifyLoginPwd } from '@/api/admin/account'
const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  data () {
    return {
      // 修改密码/信息 布局
      formItemLayout: {
        labelCol: { xs: { span: 24 }, sm: { span: 6 } },
        wrapperCol: { xs: { span: 24 }, sm: { span: 14 } }
      },
      // 修改登录密码表单
      formPassword: this.$form.createForm(this, { name: 'dynamic_rule1' }),
      state: {
        passwordLevel: 0,
        passwordLevelChecked: true,
        percent: 10,
        progressColor: '#FF0000'
      }
    }
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    },
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    // -------------------------修改密码-----------------------------
    handlePswdSubmit (e) {
      e.preventDefault()
      this.formPassword.validateFieldsAndScroll((err, values) => {
        if (!err) {
          modifyLoginPwd(values.oldPwd, values.password).then(res => {
            this.handlePswdResponse(res)
          })
        }
      })
    },
    handlePswdResponse (res) {
      if (res.code === 0) {
        this.$message.success(res.message)
        setTimeout(() => {
          window.location.href = '/'
        }, 300)
      } else {
        this.$message.error(res.message)
      }
    },
    handlePswdConfirmBlur (e) {
      const value = e.target.value
      this.confirmDirty = this.confirmDirty || !!value
    },
    compareToFirstPassword (rule, value, callback) {
      const form = this.formPassword
      if (value && value !== form.getFieldValue('password')) {
        callback(new Error('两次输入新密码和确认密码不一致!'))
      } else {
        callback()
      }
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0
      // // 判断这个字符串中有没有数字
      // if (/[0-9]/.test(value)) {
      //   level++
      // }
      // // 判断字符串中有没有字母
      // if (/[a-zA-Z]/.test(value)) {
      //   level++
      // }
      // // 判断字符串中有没有特殊符号
      // if (/[^0-9a-zA-Z_]/.test(value)) {
      //   level++
      // }
      const list = this.codeMap['system.pwd.rule.pattern'].split(',')
      if (list.length > 0) {
        for (var i = 0; i < list.length; i++) {
          if (new RegExp(list[i]).test(value)) {
            level++
          }
        }
      }
      this.state.passwordLevel = level
      this.state.percent = level / list.length * 100
      if (level === list.length) {
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error(this.codeMap['system.pwd.rule.msg']))
      }
    },
    handlePswdReset () {
      this.formPassword.resetFields()
    }

  }
}
</script>
