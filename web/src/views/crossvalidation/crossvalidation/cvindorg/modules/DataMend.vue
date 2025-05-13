<template>
  <a-modal
    :title="modalTitle"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    okText="保存"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="指标名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['indNm', { initialValue: mdl.indNm }]"
          />
        </a-form-item>
        <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['orgNm', { initialValue: mdl.orgNm }]"
          />
        </a-form-item>
        <a-form-item label="法人适用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group name="radioGroup" v-decorator="['isUse', { initialValue: mdl.isUse }]">
            <a-radio value="Y">
              是
            </a-radio>
            <a-radio value="N">
              否
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="参与对比" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group name="radioGroup" v-decorator="['isShow', { initialValue: mdl.isShow }]">
            <a-radio value="Y">
              是
            </a-radio>
            <a-radio value="N">
              否
            </a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { updateById } from '@/api/crossvalidation/cvindorg/index'

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
        data: [],
        indBack: { background: '#0097f4' },
        modalWidth: window.innerWidth * 0.5,
        visible: false,
        confirmLoading: false,
        mdl: {},
        modalTitle: '',
        form: this.$form.createForm(this)
      }
    },
    methods: {
      edit (record) {
        this.modalTitle = '编辑指标法人配置管理'
        this.visible = true
        this.mdl = Object.assign({}, record)
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            updateById(Object.assign({ ...this.mdl, ...values })).then((res) => {
              this.handleResponse(res)
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleResponse (res) {
        this.confirmLoading = false
        if (res.data) {
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
