<template>
  <a-modal
    :title="modalTitle"
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
        <a-tabs :active-key="activeKey" @change="onTabChange">
          <a-tab-pane v-if="findPassword.adminType" key="adminTab">
            <span slot="tab">联系管理员</span>
            <p>管理员：{{ findPassword.adminUserName }}</p>
            <p>邮箱：{{ findPassword.adminUserEmail }}</p>
            <p>手机：{{ findPassword.adminUserPhone }}</p>
          </a-tab-pane>
          <a-tab-pane v-if="findPassword.mailType" key="mailTab">
            <span slot="tab">邮件找回</span>
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="用户名"
                v-decorator="['loginName', { initialValue: mdl.loginName, rules: [{
                  required: true,
                  message: '请输入用户名！'
                }]}]"
              />
            </a-form-item>
            <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="邮箱"
                v-decorator="['userEmail', { initialValue: mdl.userEmail, rules: [{
                  required: true,
                  message: '请输入邮箱！'
                }]}]"
              />
            </a-form-item>
            <a-form-item label="新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                type="password"
                autocomplete="false"
                placeholder="至少6位密码，区分大小写"
                v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
              <div :style="{ width: '260px' }" >
                <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span><a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " /></div>
              </div>
            </a-form-item>
            <a-form-item label="确认密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                type="password"
                autocomplete="false"
                placeholder="确认密码"
                v-decorator="['password2', {rules: [{ required: true, message: '至少6位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane v-if="findPassword.phoneType" key="phoneTab">
            <span slot="tab">短信找回</span>
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="用户名"
                v-decorator="['loginName', { initialValue: mdl.loginName, rules: [{
                  required: true,
                  message: '请输入用户名！'
                }]}]"
              />
            </a-form-item>
            <a-form-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-search
                placeholder="手机"
                enter-button="发送"
                size="middle"
                @search="onSearch"
                v-decorator="['userMobTel', { initialValue: mdl.userMobTel, rules: [{
                  required: true,
                  message: '请输入手机！'
                }]}]"
              />
            </a-form-item>
            <a-form-item label="验证码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="验证码"
                v-decorator="['smsCaptcha', { initialValue: mdl.smsCaptcha, rules: [{
                  required: true,
                  message: '请输入验证码！'
                }]}]"
              />
            </a-form-item>
            <a-form-item label="新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                type="password"
                autocomplete="false"
                placeholder="至少6位密码，区分大小写"
                v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
              <div :style="{ width: '260px' }" >
                <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span><a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " /></div>
              </div>
            </a-form-item>
            <a-form-item label="确认密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                type="password"
                autocomplete="false"
                placeholder="确认密码"
                v-decorator="['password2', {rules: [{ required: true, message: '至少6位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
            </a-form-item>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { getFindPasswordTypes, forgetPassword } from '@/api/login'
import { i18nRenderByParams } from '@/locales'
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
      activeKey: 'adminTab',
      findPassword: {},
      form: this.$form.createForm(this),
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: true,
        percent: 10,
        progressColor: '#FF0000'
      }
    }
  },
  computed: {
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
    i18nRenderByParams,
    show () {
      this.modalTitle = '忘记密码'
      this.visible = true
      this.form.resetFields()
      this.mdl = Object.assign({}, {})
      this.getFindPasswordTypes()
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
          if (this.activeKey === 'phoneTab') {
            this.mdl.type = 'mobile'
          } else if (this.activeKey === 'mailTab') {
            this.mdl.type = 'email'
          }
          forgetPassword(Object.assign({ ...this.mdl, ...values })).then(res => {
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
        this.$message.success(res.message)
        this.visible = false
        this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
    },
    onTabChange (activeKey) {
      this.activeKey = activeKey
    },
    onSearch (value) {
    },
    getFindPasswordTypes () {
      getFindPasswordTypes().then(res => {
        if (res.code === 0) {
          this.findPassword = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0
      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    }
  }
}
</script>
<style lang="less">
  .user-register {
    &.error {
      color: #ff0000;
    }
    &.warning {
      color: #ff7e05;
    }
    &.success {
      color: #52c41a;
    }
  }
  .user-layout-register {
    .ant-input-group-addon:first-child {
      background-color: #fff;
    }
  }
</style>
<style lang="less" scoped>
  .user-layout-register {
    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }
    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }
    .register-button {
      width: 50%;
    }
    .login {
      float: right;
      line-height: 40px;
    }
  }
</style>
