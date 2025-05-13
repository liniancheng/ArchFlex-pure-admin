<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="50%"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="短信模板名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="短信模板名称"
            v-decorator="['tempName', { initialValue: mdl.tempName,
                                        rules: [{ required: true, message: '请输入模板名称'}]
                          },
                          {
                            pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                            message:i18nRenderByParams('prompt.nameAndLength', '50')
                          }]"
          />
        </a-form-item>
        <a-form-item label="短信模板内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-textarea
            placeholder="短信模板内容"
            v-decorator="['tempHtml', { initialValue: mdl.tempHtml,
            }]"
          /> -->
          <!-- <ckeditor
            @input="onChange"
            style="width:100%"
            v-decorator="['tempHtml', { initialValue: mdl.tempHtml, rules: [
              { required: true, message: '请输入短信模板内容' }
            ]
            }]"/>-->
        </a-form-item>
        <a-form-item label="短信模板描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="短信模板描述"
            v-decorator="['tempRmk', { initialValue: mdl.tempRmk,
                                       rules: [{ required: true, message: '请输入模板描述'}]
            }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/notice/smstemp/index'
import { i18nRenderByParams } from '@/locales'
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
      type: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.modalTitle = '新增 - 短信模板'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 短信模板'
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
    },
    onChange (v) {
      this.form.setFieldsValue({ 'tempHtml': v })
    }
  }
}
</script>
