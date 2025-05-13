<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    okText="保存"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tree-select
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            allow-clear
            tree-default-expand-all
            :treeData="data"
            v-decorator="['indNo', { rules: [{ required: true, message: '不能为空' }] }]"
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="系统" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            v-decorator="['sysVal', { rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option v-for="sys in sysList" :value="sys.itemValue" :key="sys.itemText">
              {{ sys.itemText }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="频度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择"
            style="width: 100%"
            v-decorator="['dateVal', {initialValue: this.dateVal, rules: [{ required: true, message: '不能为空' }] }]"
          >
            <a-select-option value="D">
              日
            </a-select-option>
            <a-select-option value="W">
              周(日)
            </a-select-option>
            <a-select-option value="T">
              旬
            </a-select-option>
            <a-select-option value="M">
              月
            </a-select-option>
            <a-select-option value="Q">
              季
            </a-select-option>
            <a-select-option value="HY">
              半年
            </a-select-option>
            <a-select-option value="Y">
              年
            </a-select-option>
            <a-select-option value="P">
              人行日
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            v-decorator="['orgVal']"
          >
            <a-select-option v-for="org in orgList" :value="org.branchNo" :key="org.branchName">
              {{ org.branchName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="是否可用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            style="width: 100%"
            v-decorator="['state', { initialValue: this.state }]"
          >
            <a-select-option value="1"> 是</a-select-option>
            <a-select-option value="0"> 否</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="sql" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            :auto-size="{ minRows: 3, maxRows: 5 }"
            v-decorator="['chkSql', { rules: [{ required: true, message: '不能为空' }] }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { save } from '@/api/crossvalidation/cvindrule/index'
  import { treeNode } from '@/api/crossvalidation/cvchkind/index'
  import { fetchSys } from '@/api/crossvalidation/cvdict/index'
  import { fetchBranchs } from '@/api/admin/branch/index'

  export default {
    data () {
      return {
        // 法人
        orgList: [],
        // 系统
        sysList: [],
        dictType: '',
        // 指标
        data: [],
        // 是否可用默认值
        state: '1',
        // 频度默认值
        dateVal: 'M',
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
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 指标
      loadTreeList () {
        treeNode().then((res) => {
          if (res.code === 0) {
            this.data = this.exchangeNodes(res.data)
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
      // 系统
      loadSysList () {
        this.dictType = 'IND_SYS'
        fetchSys(this.dictType).then((res) => {
          if (res.code === 0) {
            this.sysList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 法人
      loadOrgList () {
        fetchBranchs().then((res) => {
          if (res.code === 0) {
            this.orgList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      add () {
        this.modalTitle = '新增 - 指标规则校验设置'
        this.visible = true
        this.loadTreeList()
        this.loadSysList()
        this.loadOrgList()
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
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
