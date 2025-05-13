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
        <a-form-item label="短信发送接口" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="短信发送接口实现类"
            v-decorator="['srvSmsProgram', { initialValue: mdl.srvSmsProgram,
                                             rules: [{ required: true, message: '请输入 短信发送接口实现类'}]
            }]"
          />
        </a-form-item>
        <a-form-item label="服务器描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="服务器描述"
            v-decorator="['srvRmk', { initialValue: mdl.srvRmk,
            }]"
          />
        </a-form-item>
        <a-form-item label="服务器扩展属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="服务器扩展属性"
            v-decorator="['srvExtendField', { initialValue: mdl.srvExtendField,
            }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/notice/smssrv/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      type: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.modalTitle = '新增 - 短信服务器'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 短信服务器'
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
    }
  }
}
</script>
