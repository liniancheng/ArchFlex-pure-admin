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
            placeholder="请输入指标名称"
            v-decorator="['indNm', { initialValue: mdl.indNm, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="指标释义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="请输入指标释义"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            v-decorator="['indExpr', { initialValue: mdl.indExpr, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="参考指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入参考指标"
            v-decorator="['busDirec', { initialValue: mdl.busDirec, rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
        <a-form-item label="指标层级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="指标层级" readOnly v-decorator="['indLevel', { initialValue: mdl.indLevel }]"/>
        </a-form-item>

        <a-form-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            style="width: 120px"
            v-decorator="[
              'currencyVal',
              { initialValue: mdl.currencyVal, rules: [{ required: true, message: '不能为空' }] },
            ]"
          >
            <a-select-option value="CNY"> 人民币</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="频度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 120px"
            v-decorator="['dateVal', { initialValue: mdl.dateVal, rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option value="D"> 日</a-select-option>
            <a-select-option value="W"> 周(日)</a-select-option>
            <a-select-option value="T"> 旬</a-select-option>
            <a-select-option value="M"> 月</a-select-option>
            <a-select-option value="Q"> 季</a-select-option>
            <a-select-option value="H"> 半年</a-select-option>
            <a-select-option value="Y"> 年</a-select-option>
            <a-select-option value="RHD"> 人行日</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="修改指标层级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            @change="handleChange"
            style="width: 120px"
            v-decorator="['updateIndLevel', { initialValue: false, rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option :value="true"> 是</a-select-option>
            <a-select-option :value="false"> 否</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="父级指标" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="this.updateIndLevel === true">
          <a-tree-select
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            placeholder="请选择"
            allow-clear
            tree-default-expand-all
            :treeDefaultExpandAll="false"
            :treeData="data"
            v-decorator="['parentNo', { rules: [{ required: true, message: '不能为空' }] }]"
          >
          </a-tree-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { treeNode, updates } from '@/api/crossvalidation/cvchkind/index'

  export default {
    data () {
      return {
        updateIndLevel: false,
        data: [],
        oldData: [],
        firstNode: {
          checked: false,
          children: null,
          disabled: false,
          key: '-1',
          leaf: false,
          parentId: null,
          selectable: true,
          title: '置为一级指标',
          type: null,
          value: '-1'
        },
        treeExpandedKeys: [],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        modalWidth: window.innerWidth * 0.5,
        visible: false,
        confirmLoading: false,
        mdl: { updateIndLevel: false },
        modalTitle: '',
        form: this.$form.createForm(this)
      }
    },
    methods: {
      handleChange (value) {
        if (value === true) {
          this.updateIndLevel = value
        } else {
          this.updateIndLevel = value
        }
      },
      // tree请求
      loadData () {
        this.loading = true
        treeNode().then((res) => {
          if (res.data) {
            this.oldData = res.data
            this.oldData.unshift(this.firstNode)
            this.data = this.exchangeNodes(this.oldData)
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      exchangeNodes (nodes) {
        if (nodes && nodes.length > 0) {
          return nodes.map((item) => {
            const node = { ...item, scopedSlots: { title: 'indNm' }, children: this.exchangeNodes(item.children) }
            return node
          })
        } else {
          return null
        }
      },
      edit (record) {
        this.modalTitle = '修改指标'
        this.mdl = Object.assign({}, record)
        this.visible = true
        this.updateIndLevel = false
        this.loadData()
      },
      handleSubmit () {
        const {
          form: { validateFields }
        } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            updates(Object.assign({ ...this.mdl, ...values })).then((res) => {
              this.handleResponse(res)
            })
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
