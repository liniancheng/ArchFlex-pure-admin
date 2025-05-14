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
        <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="法人"
            v-decorator="['lgprInstCd', { initialValue: mdl.lgprInstCd,
                                          rules: [{ required: true, message: '请输入 法人'}]
            }]"
          />
        </a-form-item>
        <a-form-item label="数据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="数据日期"
            v-decorator="['dataDt', { initialValue: mdl.dataDt,rules: [{ required: true, message: '请输入 数据日期'}]}]"
          />
        </a-form-item>
        <a-form-item label="系统名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="系统名"
            v-decorator="['sysNm', { initialValue: mdl.sysNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="检核表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核表名"
            v-decorator="['sysTabNm', { initialValue: mdl.sysTabNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="检核表中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核表中文名"
            v-decorator="['sysTabChnNm', { initialValue: mdl.sysTabChnNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="检核字段名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核字段名"
            v-decorator="['colNm', { initialValue: mdl.colNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="检核字段中文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核字段中文名"
            v-decorator="['colChnNm', { initialValue: mdl.colChnNm,
            }]"
          />
        </a-form-item>
        <a-form-item label="规则描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="规则描述"
            v-decorator="['ruleDsc', { initialValue: mdl.ruleDsc,
            }]"
          />
        </a-form-item>
        <a-form-item label="错误条数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="错误条数"
            v-decorator="['errNum', { initialValue: mdl.errNum,
            }]"/>
        </a-form-item>
        <a-form-item label="记录数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="记录数"
            v-decorator="['rcrdNum', { initialValue: mdl.rcrdNum,
            }]"/>
        </a-form-item>
        <a-form-item label="检核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核状态"
            v-decorator="['verfSts', { initialValue: mdl.verfSts,
            }]"
          />
        </a-form-item>
        <a-form-item label="检核状态描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="检核状态描述"
            v-decorator="['verfDsc', { initialValue: mdl.verfDsc,
            }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

import { save, updateById } from '@/api/crossvalidation/chkresult/index'

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
      this.modalTitle = '新增 - 入口校验结果'
      this.visible = true
      this.mdl = Object.assign({}, {})
      this.type = 1
    },
    edit (record) {
      this.modalTitle = '修改 - 入口校验结果'
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
