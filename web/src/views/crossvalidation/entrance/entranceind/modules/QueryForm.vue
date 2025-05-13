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

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

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
    select (record) {
        this.modalTitle = '规则展示'
        this.mdl = Object.assign({}, record)
        this.visible = true
      },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
      this.confirmLoading = false
    }
  }
}
</script>
