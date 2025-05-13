<template>
  <a-modal
    :title="modalTitle"
    :width="modalWidth"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="机构号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="机构号"
            :disabled="kind === 2"
            v-decorator="[ 'branchNo', { initialValue: mdl.branchNo, rules: [
              { required: true, message: '请输入机构号！' },
              {
                pattern: /^[a-zA-Z0-9]{1,50}$/,
                message:i18nRenderByParams('prompt.codeAndLength', '50')
              }
            ]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="机构名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="机构名称"
            v-decorator="[ 'branchName', { initialValue: mdl.branchName, rules: [
              { required: true, message: '请输入机构名称！' },
              {
                pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-.]{1,50}$/,
                message:i18nRenderByParams('prompt.nameAndLength', '50')
              }
            ]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="机构类型" :labelCol="labelCol" v-if="kind === 1" :wrapperCol="wrapperCol">
          <a-radio-group v-model="mdl.branchType">
            <a-radio value="P">默认</a-radio>
            <a-radio value="A">A</a-radio>
            <a-radio value="B">B</a-radio>
            <a-radio value="C">C</a-radio>
            <a-radio value="D">D</a-radio>
            <a-radio value="E">E</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="机构简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="机构简称"
            style="width: 100%"
            v-decorator="[ 'branchShortName', { initialValue: mdl.branchShortName, rules: [
              { required: true, message: '请输入机构简称！' },
              {
                pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-.]{1,30}$/,
                message:i18nRenderByParams('prompt.nameAndLength', '30')
              }
            ] } ]"
          />
        </a-form-item>
        <a-form-item label="虚拟机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-model="mdl.virFlag">
            <a-radio value="0">是</a-radio>
            <a-radio value="1">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { i18nRenderByParams } from '@/locales'
import { saveBranch, updateBranch } from '@/api/admin/branch/index'
export default {
  data () {
    return {
      labelCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 6
        }
      },
      wrapperCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 15
        }
      },
      modalWidth: window.innerWidth * 0.5,
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      kind: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    i18nRenderByParams,
    addParent (record) {
      this.modalTitle = '新增 - 机构'
      this.mdl = Object.assign({}, record)
      this.kind = 1
    },
    add (record) {
      this.modalTitle = '新增 - 下级机构'
      this.mdl = Object.assign({}, record)
      this.kind = 1
    },
    edit (record) {
      this.modalTitle = '修改 - 机构'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.kind = 2
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.kind === 2) {
            updateBranch(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            saveBranch(Object.assign({ ...this.mdl, ...values })).then(res => {
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
        this.$message.success('保存成功！')
        this.form.resetFields()
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
    onChange (e) {
      this.val = e.target.value
    }
  }
}
</script>
