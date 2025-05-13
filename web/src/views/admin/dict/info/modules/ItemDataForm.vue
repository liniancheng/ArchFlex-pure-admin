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
        <a-form-item label="明细名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="明细名称"
            v-decorator="['itemText', { initialValue: mdl.itemText, rules: [{
              required: true,
              message: '请输入明细名称！'
            }]
            }]"
          />
        </a-form-item>
        <a-form-item label="明细数据值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="明细数据值"
            v-decorator="['itemValue', { initialValue: mdl.itemValue, rules: [{
              required: true,
              message: '请输入明细数据值！'
            }]
            }]"
          />
        </a-form-item>
        <a-form-item label="明细描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="明细描述"
            v-decorator="['description', { initialValue: mdl.description,
            }]"
          />
        </a-form-item>
        <a-form-item label="明细排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="明细排序"
            v-decorator="['sortOrder', { initialValue: mdl.sortOrder, rules: [{
              required: true,
              message: '请输入明细排序！'
            }]
            }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { save, updateById } from '@/api/admin/dict/item/index'
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
    add (dictId) {
      this.form.resetFields()
      this.modalTitle = '新增 - 字典明细'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.mdl.dictId = dictId
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 字典明细'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.dictId = this.mdl.dictId
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
