<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="800px"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-card title="基础信息" type="inner" size="small">
          <a-row>
            <a-col span="12">
              <a-form-item label="表英文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入表英文名"
                  v-decorator="['migTabName', { initialValue: mdl.migTabName,rules: [
                    { required: true, message: '请输入表英文名' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }]
                  }]"
                />
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="表中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入表中文名"
                  v-decorator="['migTabCname', { initialValue: mdl.migTabCname,rules: [
                    { required: true, message: '请输入表中文名' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }]
                  }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col span="12">
              <a-form-item label="主键字段" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入主键字段"
                  v-decorator="['migColId', { initialValue: mdl.migColId,rules: [
                    { required: true, message: '请输入主键字段' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }]
                  }]"
                />
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="显示字段" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入显示字段"
                  v-decorator="['migColName', { initialValue: mdl.migColName,rules: [
                    { required: true, message: '请输入显示字段' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }]
                  }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col span="12">
              <a-form-item label="表类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-decorator="['migType', { initialValue: mdl.migType,rules: [ { required: true, message: '请选中表类型' }] }]" >
                  <a-select-option value="0" :disabled="relVisible">目录</a-select-option>
                  <a-select-option value="1" :disabled="!relVisible">实体表</a-select-option>
                  <a-select-option value="2" :disabled="!relVisible">关系表</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
        <a-card title="关联信息" v-if="relVisible" type="inner" size="small">
          <a-row>
            <a-col span="12">
              <a-form-item label="关联类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-decorator="['migRelType', { initialValue: mdl.migRelType,rules: [ { required: true, message: '请选择关联类型' }] }]" >
                  <a-select-option value="0">主表关联关系表</a-select-option>
                  <a-select-option value="1">关系表关联主表</a-select-option>
                  <a-select-option value="2">自关联</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="关联字段" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入关联字段"
                  v-decorator="['migRelId', { initialValue: mdl.migRelId,rules: [
                    { required: true, message: '请输入关联字段' },
                    {
                      pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                      message:i18nRenderByParams('prompt.nameAndLength', '50')
                    }]
                  }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col span="12">
              <a-form-item label="转换类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-decorator="['migConverType', { initialValue: mdl.migConverType,rules: [ { required: true, message: '请选择转换类型' }] }]" >
                  <a-select-option value="0">不转换</a-select-option>
                  <a-select-option value="1">list</a-select-option>
                  <a-select-option value="2">map</a-select-option>
                  <a-select-option value="3">json</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col v-if="form.getFieldValue('migConverType') !== '0'" span="12">
              <a-form-item label="提取值" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入提取值"
                  v-decorator="['migRelValue', { initialValue: mdl.migRelValue,rules: [
                    { required: true, message: '请输入提取值' }]
                  }]"
                />
              </a-form-item>
            </a-col>
            <a-col v-if="form.getFieldValue('migRelType') === '2'" span="12">
              <a-form-item label="自关联结束值" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入自关联结束值" v-decorator="['migEndSign', { initialValue: mdl.migEndSign }]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById } from '@/api/config/inout/manage'
import { i18nRenderByParams } from '@/locales'
export default {
  data () {
    return {
      labelCol: { xs: { span: 24 }, sm: { span: 8 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 16 } },
      visible: false,
      confirmLoading: false,
      mdl: {},
      modalTitle: '',
      type: 1,
      form: this.$form.createForm(this),
      relVisible: true
    }
  },
  methods: {
    i18nRenderByParams,
    add (pId) {
      this.form.resetFields()
      this.modalTitle = '新增 - 导入导出配置'
      this.visible = true
      this.mdl = Object.assign({}, { migType: pId === '-1' ? '0' : '1', parentMigId: pId, migRelType: '0', migConverType: '0' })
      this.relVisible = (pId !== '-1')
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 导入导出配置'
      this.mdl = Object.assign({}, record)
      this.relVisible = (record.migType !== '0')
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
    }
  }
}
</script>
