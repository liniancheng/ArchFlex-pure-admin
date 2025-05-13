<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    width="800px"
    @cancel="handleCancel"

  >
    <a-spin :spinning="confirmLoading">
      <a-alert type="info" banner >
        <div slot="message">
          <span>宽度：按屏幕宽度等分24栅格，宽度即所占的份数。</span><br/>
          <span>高度：基础行高为5px，这里的高度为行高的倍数。</span>
        </div>
      </a-alert>
      <a-form :form="form">
        <a-row>
          <a-col span="12">
            <a-form-item label="数据项名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="数据项名称"
                v-decorator="['itemName', { initialValue: mdl.itemName,rules: [
                  { required: true, message: '请输入 数据项名称'},
                  {
                    pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                    message:i18nRenderByParams('prompt.nameAndLength', '50')
                  }]
                }]"
              />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="组件路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="组件路径"
                v-decorator="['itemComp', { initialValue: mdl.itemComp,rules: [
                  { required: true, message: '请输入 组件路径'},
                  {
                    pattern:'(views|layouts)/+[^\s]*',
                    message:'请输入正确的组件路径：views/...或layouts/...'
                  }]
                }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <a-form-item label="最小宽度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:100%"
                placeholder="最小宽度"
                :min="4"
                v-decorator="['minWidth', { initialValue: mdl.minWidth,rules: [
                  { required: true, message: '请输入 最小宽度'}]
                }]"/>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="最小高度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:100%"
                placeholder="最小高度"
                :min="5"
                v-decorator="['minHeight', { initialValue: mdl.minHeight,rules: [
                  { required: true, message: '请输入 最小高度'}]
                }]"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <a-form-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:100%"
                placeholder="排序"
                :min="0"
                v-decorator="['itemOrder', { initialValue: mdl.itemOrder ,rules: [
                  { required: true, message: '请输入 排序'}]
                }]"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="描述" :labelCol="{ xs: { span: 24 }, sm: { span: 3 } }" :wrapperCol="{ xs: { span: 24 }, sm: { span: 21 } }">
          <a-textarea
            style="width:100%"
            placeholder="描述"
            :min="0"
            v-decorator="['itemRmk', { initialValue: mdl.itemRmk }]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save, updateById } from '@/api/admin/layout/layoutItem/index'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: { xs: { span: 24 }, sm: { span: 6 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 } },
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
      this.modalTitle = '新增 - 布局数据项'
      this.visible = true
      this.mdl = Object.assign({}, { itemOrder: 1 })
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - 布局数据项'
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
