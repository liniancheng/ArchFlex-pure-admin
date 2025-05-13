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
        <a-form-item label="规则实例编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['ruleNo', { initialValue: mdl.ruleNo,
            }]"
          />
        </a-form-item>
        <a-form-item label="系统名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['sysNm', { initialValue: mdl.sysNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['sysTabNm', { initialValue: mdl.sysTabNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="表中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['sysTabChnNm', { initialValue: mdl.sysTabChnNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="字段名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['colNm', { initialValue: mdl.colNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="字段中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['colChnNm', { initialValue: mdl.colChnNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="规则类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['rltypDsc', { initialValue: mdl.rltypDsc,
            }]"
          />
        </a-form-item>
        <a-form-item label="规则描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['ruleDsc', { initialValue: mdl.ruleDsc,
            }]"
          />
        </a-form-item>
        <a-form-item label="SQL" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder=""
            v-decorator="['verisqlInf', { initialValue: mdl.verisqlInf,
            }]"
          />
        </a-form-item>
        <a-form-item label="是否可用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            v-decorator="['vldFlg', { initialValue: this.vldFlg }]"
          >
            <a-select-option value="Y"> 是</a-select-option>
            <a-select-option value="N"> 否</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save, updateById } from '@/api/crossvalidation/entranceind/index'

export default {
  data () {
    return {
      // 是否可用默认值
        vldFlg: 'Y',
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
      this.modalTitle = '新增规则'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - 入口校验'
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
