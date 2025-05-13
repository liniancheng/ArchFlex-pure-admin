<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <template slot="footer">
      <a-button @click="handleSubmit" type="primary">执行校验</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="计划名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['planName', { initialValue: this.planName}]"/>
        </a-form-item>
        <a-form-item label="机构法人:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            readOnly
            v-decorator="['orgVal', { initialValue: mdl.orgVal }]"/>
        </a-form-item>
        <a-form-item label="执行方法" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 120px"
            v-decorator="['planType', { initialValue: this.planType, rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option value="1"> 立即执行</a-select-option>
            <a-select-option value="2">定时执行</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="数据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-date-picker v-model="mdl.dataTime" @change="handleChange"/>
        </a-form-item>
        <a-form-item label="资源配置" :labelCol="labelCol" :wrapperCol="wrapperCol" :required="true">
          <a-select
            style="width: 100%"
            v-decorator="['resVal', { rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option v-for="res in resList" :value="res.id" :key="res.dbNm">
              {{ res.dbNm }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { save } from '@/api/crossvalidation/cvchkplan/index'
  import { execSql } from '@/api/crossvalidation/cvplandetail/index'
  import { fetchList } from '@/api/admin/res/index'

  export default {
    data () {
      return {
        planName: '',
        planType: '1',
        labelCol: {
          xs: { span: 24 }, sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 }, sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        mdl: {},
        resList: [],
        modalTitle: '',
        form: this.$form.createForm(this)
      }
    },
    methods: {
      add (queryparam) {
        console.log(666, queryparam)
        this.modalTitle = '创建指标校验计划'
        this.mdl = Object.assign({}, queryparam)
        this.visible = true
        this.planName = this.mdl.orgVal.substring(0, 3) + '_KXTJY_' + new Date().toLocaleDateString().replaceAll('/', '-')
        this.selectResList()
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            this.mdl = { dataTime: this.mdl.dataTime, indNos: this.mdl.indNos, sysVals: this.mdl.sysVals, currencyVal: this.mdl.currencyVal }
            this.mdl.planName = values.planName
            this.mdl.orgVal = values.orgVal
            this.mdl.planType = values.planType
            this.mdl.resVal = values.resVal
            save(this.mdl).then(res => {
              if (res.data) {
                this.$message.success('执行计划创建成功,请稍后到检核结果处查看!')
                res.data.resVal = this.mdl.resVal
                this.handleResponse(res)
              } else {
                this.$message.error(res.message)
                this.confirmLoading = false
              }
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleResponse (res) {
        this.confirmLoading = false
        if (res.data) {
          this.mdl = {}
          this.mdl.planId = res.data.planId
          this.mdl.dataTime = res.data.dataTime
          this.mdl.resVal = res.data.resVal
          execSql(this.mdl).then(res => {
            if (res.data) {
              this.$message.success('检核完成,请到检核结果处查看!')
            } else {
              this.$message.error(res.message)
            }
          }).finally(() => {
            this.visible = false
          })
        }
      },
      handleChange (date, dateString) {
        this.mdl.dataTime = dateString
      },
      handleCancel () {
        this.visible = false
        this.form.resetFields()
        this.confirmLoading = false
        this.planName = ''
      },
      selectResList () {
        fetchList().then(res => {
          if (res.code === 0) {
            this.resList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>
