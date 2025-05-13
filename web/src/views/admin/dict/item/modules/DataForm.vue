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
        <a-form-item label="字典ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典ID"
            v-decorator="['dictId', { initialValue: mdl.dictId,
            }]"
          />
        </a-form-item>
        <a-form-item label="字典项文本" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典项文本"
            v-decorator="['itemText', { initialValue: mdl.itemText,
            }]"
          />
        </a-form-item>
        <a-form-item label="字典项值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典项值"
            v-decorator="['itemValue', { initialValue: mdl.itemValue,
            }]"
          />
        </a-form-item>
        <a-form-item label="字典项描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="字典项描述"
            v-decorator="['description', { initialValue: mdl.description,
            }]"
          />
        </a-form-item>
        <a-form-item label="字典项排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="字典项排序"
            v-decorator="['sortOrder', { initialValue: mdl.sortOrder,
            }]"/>
        </a-form-item>
        <a-form-item label="状态（1启用 0不启用）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="状态（1启用 0不启用）"
            v-decorator="['status', { initialValue: mdl.status,
            }]"/>
        </a-form-item>
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="创建时间"
            v-decorator="['createTime', { initialValue: mdl.createTime,
                                          rules: [{ required: true, message: '请输入 创建时间'}]
            }]"
          />
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
    add () {
      this.modalTitle = '新增 - 字典明细'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
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
