<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="55%"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="模板名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="模板名称"
            v-decorator="['tempName', { initialValue: mdl.tempName,
                                        rules: [{ required: true, message: '请输入模板名称'},
                                                {
                                                  pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                                                  message:i18nRenderByParams('prompt.nameAndLength', '50')
                                                }]
            }]"
          />
        </a-form-item>
        <a-form-item label="模板内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-textarea
            placeholder="模板内容"
            v-decorator="['tempContent', { initialValue: mdl.tempContent,
                                           rules: [{ required: true, message: '请输入模板内容'}]
            }]"
          /> -->
          <!-- <ckeditor
            @input="onChange"
            style="width:100%"
            :config="editorConfig"
            v-decorator="['tempContent', { initialValue: mdl.tempContent, rules: [
              { required: true, message: '请输入模板内容' }
            ]
            }]"/>-->

          <editor-bar
            ref="editor"
            :isClear="isClear"
            @change="onChange"
            v-decorator="['tempContent', { initialValue: mdl.tempContent, rules: [
              { required: true, message: '请输入模板内容' }
            ]
            }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/notice/mailtmp/index'
import EditorBar from '@/components/Editor/WangEditor'
import { i18nRenderByParams } from '@/locales'
export default {
  components: { EditorBar },
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 20 }
      },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      modalWidth: window.innerWidth * 0.5,
      type: 1,
      isClear: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    add () {
      this.form.resetFields()
      this.modalTitle = '新增 - 邮件模板'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 邮件模板'
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
            }).finally(() => {
                this.confirmLoading = false
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
    onChange (v) {
      this.form.setFieldsValue({ 'tempContent': v })
    }
  }
}
</script>
