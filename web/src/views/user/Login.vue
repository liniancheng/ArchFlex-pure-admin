<template>
  <div class="main">
    <h2 class="login_text">
      {{ codeMap['system.login.logo.text'] }}
    </h2>
    <h4 class="title_h4">
      数接千载
      <i style="margin-left:20px"/>据联万里
    </h4>
    <a-form id="formLogin" class="user-layout-login" ref="formLogin" :form="form" @submit="handleSubmit">
      <a-alert v-if="isLoginError" type="error" showIcon style="margin-bottom: 24px;" message="用户名或密码错误" />
      <a-form-item class="from_row">
        <a-input
          size="large"
          type="text"
          placeholder="用户名"
          v-decorator="[ 'username', { initialValue: username, rules: [
            { required: true, message: '请输入用户名' },
            { validator: handleUsernameOrEmail }
          ], validateTrigger: 'change' } ]" >
          <a-icon slot="prefix" type="user" />
        </a-input>
      </a-form-item>

      <a-form-item class="from_row">
        <a-input
          size="large"
          type="password"
          autocomplete="false"
          placeholder="密码"
          v-decorator="['password', { rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur' }]"
        >
          <a-icon slot="prefix" type="lock" />
        </a-input>
      </a-form-item>
      <a-row v-if="isLoginError" :gutter="16">
        <a-col class="gutter-row" :span="16">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="验证码"
              v-decorator="[ 'captcha', { rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur' } ]" >
              <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input>
          </a-form-item>
        </a-col>
        <a-col class="gutter-row" :span="8" @click="getImgCaptcha()">
          <img :src="image" class="avator" :style="{ height: '40px', width: '84px' }" />
        </a-col>
      </a-row>

      <a-form-item>
        <a-checkbox :checked="rememberMe" @change="rememberMeOnChange">记住用户名</a-checkbox>
        <a @click="showForgetPassword" class="forge-password" style="float: right;">忘记密码</a>
      </a-form-item>

      <a-form-item style="margin-top:24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button
        >
      </a-form-item>

      <div class="user-login-other">
        <!-- <router-link class="register" :to="{ name: 'register' }">注册账户</router-link> -->
      </div>
      <forget-password ref="forgetPassword" />
    </a-form>

    <two-step-captcha
      v-if="requiredTwoStepCaptcha"
      :visible="stepCaptchaVisible"
      @success="stepCaptchaSuccess"
      @cancel="stepCaptchaCancel"
    ></two-step-captcha>
  </div>
</template>

<script>
import TwoStepCaptcha from '@/components/tools/TwoStepCaptcha'
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
import { getSmsCaptcha, getCaptcha, updateLoginTime } from '@/api/login'
import ForgetPassword from './ForgetPassword'
import { logoName } from '@/core/icons'
const rememberMeSaveKey = 'adtec_base_system_login_name'
export default {
  components: {
    logoName,
    TwoStepCaptcha,
    ForgetPassword
  },
  data () {
    return {
      loginBtn: false,
      // login type: 0 email, 1 username, 2 telephone
      loginType: 0,
      isLoginError: false,
      requiredTwoStepCaptcha: false,
      stepCaptchaVisible: false,
      form: this.$form.createForm(this),
      image: undefined,
      captchaId: '',
      rememberMe: false,
      username: '',
      state: {
        time: 60,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        smsSendBtn: false
      }
    }
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    }
  },
  mounted () {
    const username = localStorage.getItem(rememberMeSaveKey)
    if (username) {
      this.rememberMe = true
      this.username = username
    }
  },
  watch: {
    codeMap (newVal, oldVal) {}
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    // handler
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        state.loginType = 0
      } else {
        state.loginType = 1
      }
      callback()
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state,
        Login
      } = this

      state.loginBtn = true

      const validateFieldsKey = ['username', 'password', 'captcha', 'captchaId']
      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          const loginParams = { ...values }
          delete loginParams.username
          loginParams[!state.loginType ? 'email' : 'username'] = values.username
          loginParams.password = values.password
          loginParams['captchaId'] = this.captchaId
          Login(loginParams)
            .then(res => this.loginSuccess(loginParams.username))
            .catch(err => this.requestFailed(err))
            .finally(() => {
              state.loginBtn = false
            })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      })
    },
    getImgCaptcha (e) {
      getCaptcha()
        .then(res => {
          if (res.code === 0) {
            this.image = 'data:image/png;base64,' + res.data[0]
            this.captchaId = res.data[1]
          } else {
            this.$notification['success']({
              message: '提示',
              description: res.message,
              duration: 8
            })
          }
        })
        .catch(err => {
          this.requestFailed(err)
        })
    },
    getCaptcha (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state
      } = this

      validateFields(['mobile'], { force: true }, (err, values) => {
        if (!err) {
          state.smsSendBtn = true
          const interval = window.setInterval(() => {
            if (state.time-- <= 0) {
              state.time = 60
              state.smsSendBtn = false
              window.clearInterval(interval)
            }
          }, 1000)

          const hide = this.$message.loading('验证码发送中..', 0)
          getSmsCaptcha({ mobile: values.mobile })
            .then(res => {
              if (res.code === 0) {
                setTimeout(hide, 2500)
                this.$notification['success']({
                  message: '提示',
                  description: '验证码获取成功，您的验证码为：' + res.data,
                  duration: 8
                })
              } else {
                this.$notification['failure']({
                  message: '提示',
                  description: '验证码获取成功，您的验证码为：' + res.data,
                  duration: 8
                })
              }
            })
            .catch(err => {
              setTimeout(hide, 1)
              clearInterval(interval)
              state.time = 60
              state.smsSendBtn = false
              this.requestFailed(err)
            })
        }
      })
    },
    stepCaptchaSuccess () {
      this.loginSuccess()
    },
    stepCaptchaCancel () {
      this.Logout().then(() => {
        this.loginBtn = false
        this.stepCaptchaVisible = false
      })
    },
    loginSuccess (username) {
      if (this.rememberMe) {
        if (username) {
          localStorage.setItem(rememberMeSaveKey, username)
        }
      } else {
        localStorage.removeItem(rememberMeSaveKey)
      }
      this.$router.push({ name: 'menu.dashboard' })
      // 更新登录时间
      updateLoginTime().then(res => {})
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
      this.isLoginError = false
    },
    requestFailed (err) {
      if (err.code && err.data === 'FIRST') {
        this.$router.push({ name: 'change', params: { message: '初次登录,请修改密码！' } })
      } else if (err.code && err.data === 'LOCKED') {
        this.$router.push({ name: 'change', params: { message: '密码已失效,请修改密码！' } })
      } else {
        this.isLoginError = true
        if (this.state.loginType === 1) {
          this.getImgCaptcha()
        }
        this.$notification['error']({
          message: '错误',
          description: (err || {}).message || '请求出现错误，请稍后再试',
          duration: 4
        })
      }
    },
    showForgetPassword () {
      this.$refs.forgetPassword.show()
    },
    rememberMeOnChange (e) {
      this.rememberMe = e.target.checked
    }
  }
}
</script>

<style lang="less" scoped>
@import '~ant-design-vue/es/style/themes/default.less';
.main {
  .login {
    margin-left: 42px;
    margin-bottom: 30px;
  }
  .login_text {
    text-align: center;
    font-size: 32px;
    font-weight: bold;
    // margin-bottom: 60px;
    color: @primary-color;
  }
  h4.title_h4 {
    font-size: 21px;
    font-family: SourceHanSansCN;
    font-weight: 400;
    color: rgba(153, 153, 153, 1);
    text-align: center;
    margin-bottom: 50px;
  }
}
.user-layout-login {
  margin: 0 60px;
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
