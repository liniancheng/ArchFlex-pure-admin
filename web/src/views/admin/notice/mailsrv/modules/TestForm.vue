<template>
  <a-modal
    :title="modalTitle"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="邮件标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="邮件标题"
            v-decorator="['title', { initialValue: mdl.title,
                                     rules: [{ required: true, message: '请输入 邮件标题'}]
            }]"
          />
        </a-form-item>
        <a-form-item label="收件地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="收件地址(使用逗号分隔)"
            v-decorator="['toMails', { initialValue: mdl.toMails,
                                       rules: [{ required: true, message: '请输入 收件地址'}]
            }]"
          />
        </a-form-item>
        <a-form-item label="抄送地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="抄送地址(使用逗号分隔)"
            v-decorator="['ccMails', { initialValue: mdl.ccMails,
                                       rules: [{ required: false, message: '请输入 抄送地址'}]
            }]"
          />
        </a-form-item>
        <a-form-item label="邮件模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            placeholder="请选择测试邮件模板"
            optionFilterProp="children"
            v-decorator="[
              'tempId',
              {
                initialValue: mdl.tempId,
                rules: [
                  {
                    required: true,
                    message: '请选测试邮件模板！'
                  }
                ]
              }
            ]"
            :getPopupContainer="target => target.parentNode"
          >  <a-select-option v-for="(temp, index) in tempList" :key="index.toString()" :value="temp.tempId">
            {{ temp.tempName }}
          </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="变量替换" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea placeholder="变量替换(使用逗号分隔,名称与值使用等号分隔)" v-decorator="[ 'paraValues', { initialValue: mdl.paraValues} ]" />
        </a-form-item>
        <a-form-item label="发送结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <div style="width:370px;height:60px;overflow:auto;border:1px solid rgb(221, 221, 221);" v-html="mdl.executeResult" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { test, fetchList } from '@/api/admin/notice/mailsrv/index'
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
      tempList: [],
      modalTitle: '',
      form: this.$form.createForm(this)
    }
  },
  methods: {
    show (srvId, srvName) {
      this.confirmLoading = false
      this.modalTitle = '测试发送邮件 - ' + srvName
      this.visible = true
      this.form.resetFields()
      this.initialTempList()
      this.mdl = Object.assign({}, { srvId: srvId })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          test(Object.assign({ ...this.mdl, ...values })).then(res => {
            if (res.code === 0) {
              this.mdl.executeResult = res.data
              this.confirmLoading = false
            } else {
                this.$message.error(res.message)
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
      this.confirmLoading = false
    },
    initialTempList () {
      fetchList().then(res => {
        if (res.code === 0) {
          this.tempList = res.data
        } else {
          console.log(res.message)
        }
      })
    }
  }
}
</script>
