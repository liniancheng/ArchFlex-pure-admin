<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :width="modalWidth"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row :gutter="48">
          <a-col :md="12" :sm="24">
            <a-form-item label="服务器名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="服务器名称"
                v-decorator="['srvName', { initialValue: mdl.srvName,
                                           rules: [{ required: true, message: '请输入 服务器名称'},
                                                   {
                                                     pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                                                     message:i18nRenderByParams('prompt.nameAndLength', '50')
                                                   }]
                }]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="服务器描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="服务器描述"
                v-decorator="['srvRmk', { initialValue: mdl.srvRmk,
                }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :md="12" :sm="24">
            <a-form-item label="邮箱地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="邮箱地址"
                v-decorator="['defaultFrom', { initialValue: mdl.defaultFrom,
                                               rules: [{ required: true, message: '请输入 邮箱地址'},
                                                       {
                                                         validator: validateMail
                                                       }]
                }]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="显示名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="显示名称"
                v-decorator="['showName', { initialValue: mdl.showName,
                                            rules: [{ required: true, message: '请输入 显示名称'},
                                                    {
                                                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                                                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                                                    }]
                }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :md="12" :sm="24">
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="用户名"
                v-decorator="['loginName', { initialValue: mdl.loginName,
                                             rules: [{ required: true, message: '请输入 用户名'}]
                              },
                              {
                                pattern: /^[a-zA-Z0-9_]{1,50}$/,
                                message:i18nRenderByParams('prompt.codeAndLength', '50')
                              }]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="密码"
                v-decorator="['loginPwd', { initialValue: mdl.loginPwd,
                                            rules: [{ required: true, message: '请输入 密码'}]
                }]"
                type="password"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :md="12" :sm="24">
            <a-form-item label="服务器地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="服务器地址"
                v-decorator="['srvUrl', { initialValue: mdl.srvUrl,
                                          rules: [{ required: true, message: '请输入 邮件服务器地址'},
                                                  {
                                                    validator: validateUrl
                                                  }]
                }]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="服务器端口" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="服务器端口"
                v-decorator="['srvPort', { initialValue: mdl.srvPort, rules: [{
                  required: true,
                  message: '请输入 服务器端口'
                }]
                }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :md="12" :sm="24">
            <a-form-item label="启用SSL" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-model="mdl.sslFlag">
                <a-radio-button value="0">否</a-radio-button>
                <a-radio-button value="1">是</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/notice/mailsrv/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 12 }
      },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      modalWidth: window.innerWidth * 0.6,
      type: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.modalTitle = '新增 - 邮件服务器'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 邮件服务器'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.type === 1) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            }).finally(() => {
                this.confirmLoading = false
              })
          }
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
      this.form.resetFields()
      this.confirmLoading = false
    },
   validateMail (rule, value, callback) {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (value && !regEmail.test(value)) {
        callback(new Error('邮箱格式不正确，请检查!'))
      } else {
        callback()
      }
    },
    validateUrl (rule, value, callback) {
      const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
      if (value && !urlregex.test(value)) {
        callback(new Error('邮件服务器地址格式不正确，请检查!'))
      } else {
        callback()
      }
    }
  }
}
</script>
