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
        <a-form-item :label="propertyName.name" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            :placeholder="propertyName.name"
            v-decorator="['typeName', { initialValue: mdl.typeName, rules: [{
              required: true,
              message: '请输入'+propertyName.name+'！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.order" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            :placeholder="propertyName.order"
            style="width: 100%"
            v-decorator="['typeOrder', { initialValue: mdl.typeOrder, rules: [{
              required: true,
              message: '请输入'+propertyName.order+'！'
            }]
            }
            ]"
          />
        </a-form-item>
        <a-form-item :label="propertyName.rmk" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            :placeholder="propertyName.rmk"
            v-decorator="[ 'typeRmk', { initialValue: mdl.typeRmk} ]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { saveType, updateTypeById } from '@/api/admin/workflow/workflow/type'
import { i18nRenderByParams } from '@/locales'
const functionName = '分类'
export default {
  data () {
    return {
      propertyName: {
        name: functionName + '名称',
        order: functionName + '排序',
        rmk: functionName + '描述'
      },
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
      this.modalTitle = '新增 - ' + functionName
      this.visible = true
      this.form.resetFields()
      this.confirmLoading = false
      this.mdl = Object.assign({}, { typeOrder: 1 })
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - ' + functionName
      this.form.resetFields()
      this.confirmLoading = false
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
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
          if (this.type === 1) {
            saveType(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            updateTypeById(Object.assign({ ...this.mdl, ...values })).then(res => {
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
    }
  }
}
</script>
