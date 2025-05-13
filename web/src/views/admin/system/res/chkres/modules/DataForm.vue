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
        <a-form-item label="数据源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            v-decorator="['dbType', { initialValue: mdl.dbType,rules: [{ required: true, message: '请选择' }]}]"
          >
            <a-select-option value="1"> mysql </a-select-option>
            <a-select-option value="2"> db2 </a-select-option>
            <a-select-option value="3"> orcle </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="连接驱动" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="连接驱动"
            v-decorator="['lkDriver', { initialValue: mdl.lkDriver,rules: [{ required: true, message: '请输入' }]
            }]"
          />
        </a-form-item>
        <a-form-item label="连接URL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="连接URL"
            v-decorator="['lkUrl', { initialValue: mdl.lkUrl,rules: [{ required: true, message: '请输入' }]
            }]"
          />
        </a-form-item>
        <a-form-item label="连接用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="连接用户"
            v-decorator="['lkUser', { initialValue: mdl.lkUser,rules: [{ required: true, message: '请输入' }]
            }]"
          />
        </a-form-item>
        <a-form-item label="连接密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password
            placeholder="连接密码"
            v-decorator="['lkPassword', { initialValue: mdl.lkPassword,rules: [{ required: true, message: '请输入' }]
            }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { save, updateById, test } from '@/api/admin/res/index'

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
        this.modalTitle = '新增 - 资源配置'
        this.visible = true
        this.mdl = Object.assign({}, {})
        this.type = 1
      },
      edit (record) {
        this.modalTitle = '修改 - 资源配置'
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
              test(Object.assign({ ...this.mdl, ...values })).then(res => {
                if (res.data) {
                  save(Object.assign({ ...this.mdl, ...values })).then(res => {
                    this.handleResponse(res)
                  })
                }
              })
            } else {
              test(Object.assign({ ...this.mdl, ...values })).then(res => {
                if (res.data) {
                  updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
                    this.handleResponse(res)
                  })
                }
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
          this.form.resetFields()
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
