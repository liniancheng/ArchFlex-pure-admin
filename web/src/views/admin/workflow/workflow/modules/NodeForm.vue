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
        <a-form-item v-if="mdl.wfnodeLevel > 1" label="上级节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            placeholder="上级节点"
            v-decorator="['selectParentNodeIds', { initialValue: mdl.selectParentNodeIds, rules: [{ required: true, message: '请选择上级节点'}]
            },]"
          >
            <a-select-option v-for="option in parentNodeList" :key="option.nodeId" :value="option.nodeId">{{ option.nodeName }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="节点名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="节点名称"
            v-decorator="['wfnodeName', { initialValue: mdl.wfnodeName, rules: [{
              required: true,
              message: '请输入节点名称！'
            },{
              pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
              message:i18nRenderByParams('prompt.nameAndLength', '50')
            }]}]"
          />
        </a-form-item>
        <a-form-item label="审批通过数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="审批通过数"
            style="width: 100%"
            v-decorator="['agreeNum', { initialValue: mdl.agreeNum, rules: [{
              required: true,
              message: '请输入审批通过数！'
            }]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="审批不通过数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="审批通过数"
            style="width: 100%"
            v-decorator="['disagreeNum', { initialValue: mdl.disagreeNum, rules: [{
              required: true,
              message: '请输入审批不通过数！'
            }]
            }
            ]"
          />
        </a-form-item>
        <a-form-item label="节点描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea placeholder="节点描述" v-decorator="[ 'wfnodeRmk', { initialValue: mdl.wfnodeRmk} ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { save, updateById, parentNodes } from '@/api/admin/workflow/workflow/node'
import { i18nRenderByParams } from '@/locales'
const functionName = '流程节点'
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
      form: this.$form.createForm(this),
      parentNodeList: []
    }
  },
  methods: {
    i18nRenderByParams,
    add (workflowId, nodeId, nodeLevel) {
      this.modalTitle = '新增 - ' + functionName
      this.visible = true
      this.form.resetFields()
      this.initParentNodeList(workflowId, nodeLevel)
      if (nodeId.indexOf('.') > -1) {
        nodeId = nodeId.substring(nodeId.indexOf('.') + 1)
      }
      this.mdl = Object.assign({}, { workflowId: workflowId, wfnodeLevel: nodeLevel + 1, agreeNum: 1, disagreeNum: 1, selectParentNodeIds: [ nodeId ] })
      this.type = 1
      this.confirmLoading = false
    },
    edit (record) {
      this.modalTitle = '修改 - ' + functionName
      this.form.resetFields()
      const nodeLevel = record.wfnodeLevel - 1
      this.initParentNodeList(record.workflowId, nodeLevel)
      record.selectParentNodeIds = record.parentIds.split(',')
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.type = 2
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.mdl.wfnodeLevel > 1) {
            values.parentIds = values.selectParentNodeIds.join(',')
          } else {
            values.parentIds = '-1,'
          }
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
        this.visible = false
        this.$emit('ok')
      } else {
        this.form.resetFields()
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
    },
    initParentNodeList (workflowId, nodeLevel) {
      parentNodes(workflowId, nodeLevel).then(res => {
        if (res.code === 0) {
            this.parentNodeList = res.data
          } else {
            this.$message.error(res.message)
          }
      })
    }
  }
}
</script>
