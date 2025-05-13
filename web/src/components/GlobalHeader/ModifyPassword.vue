<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :width="640"
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
        <a-form-item v-bind="formItemLayout" label="旧密码" has-feedback>
          <a-input-password
            v-decorator="[ 'oldPwd', { rules: [
              { required: true, message: '请输入旧密码!' }
            ] } ]" >
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
          </a-input-password>
        </a-form-item>
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
        <a-form-item v-bind="formItemLayout" label="确认密码" has-feedback>
          <a-input-password
            type="password"
            @blur="handleConfirmBlur"
            v-decorator="[ 'confirm', { rules: [
              { required: true, message: '请输入确认密码!' },
              { validator: compareToFirstPassword }
            ]}]">
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
          </a-input-password>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { modifyLoginPwd } from '../_util/pub'
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
  name: 'ModifyPassword',
  data () {
    return {
      confirmDirty: false,
      formItemLayout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        }
      },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      form: this.$form.createForm(this),
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
    show () {
      this.modalTitle = '修改密码'
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          modifyLoginPwd(values.oldPwd, values.password).then(res => {
             if (res.data.code === 0) {
              this.$message.success(res.data.message)
              setTimeout(() => {
                window.location.href = '/'
              }, 300)
            } else {
              this.$message.error(res.data.message)
            }
          })
        }
      })
    },
    handleConfirmBlur (e) {
      const value = e.target.value
      this.confirmDirty = this.confirmDirty || !!value
    },
    compareToFirstPassword (rule, value, callback) {
      const form = this.form
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
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
