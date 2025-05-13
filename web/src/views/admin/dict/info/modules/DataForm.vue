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
        <a-form-item label="字典名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典名称"
            v-decorator="['dictName', { initialValue: mdl.dictName, rules: [{
                                                                              required: true,
                                                                              message: '请输入字典名称！'
                                                                            },
                                                                            {
                                                                              pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                                                                              message:i18nRenderByParams('prompt.nameAndLength', '50')
                                                                            }] }]"
          />
        </a-form-item>
        <a-form-item label="字典编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :disabled="type === 2 ? true : false"
            placeholder="字典编码"
            v-decorator="['dictCode', { initialValue: mdl.dictCode, rules: [{
                                                                              required: true,
                                                                              message: '请输入字典编码！'
                                                                            },
                                                                            {
                                                                              pattern:/^[_a-zA-Z0-9-]{1,50}$/,
                                                                              message:i18nRenderByParams('prompt.codeAndLength', '50')
                                                                            }]
            }]"
          />
        </a-form-item>
        <a-form-item label="字典描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典描述"
            v-decorator="['description', { initialValue: mdl.description,
            }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/admin/dict/info/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
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
      this.modalTitle = '新增 - 字典信息'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
      this.form.resetFields()
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 字典信息'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
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
        this.form.resetFields()
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
