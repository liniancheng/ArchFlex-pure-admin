<template>
  <div>
    <a-card :bordered="false">
      <div v-if="authBtns.select" class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="实例名称">
                <a-input v-model="queryParam.instanceName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="实例状态">
                <a-select placeholder="请选择实例状态" v-model="queryParam.instanceStatus">
                  <a-select-option value="1">处理中</a-select-option>
                  <a-select-option value="2">已完成</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="loadData">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="reset">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <a-table
        ref="table"
        size="default"
        rowKey="workflowId"
        :pagination="false"
        :columns="columns"
        :data-source="data"
        :loading="loading"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a
              v-if="authBtns.insert && record.workflowType === 'workflow'"
              class="table_blue"
              @click="handleAdd(record.workflowId)">
              【新增】
            </a>
            <a
              v-if="record.workflowType === 'instance'"
              class="table_blue"
              @click="handleGrap(record)">
              【进度】
            </a>
            <a
              v-if="authBtns.update && record.workflowType === 'instance'"
              class="table_blue"
              @click="handleEdit(record)">
              【修改】
            </a>
            <a-popconfirm title="确定要删除?" v-if="authBtns.delete && record.workflowType === 'instance'" @confirm="() => handleDelete(record.workflowId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </a-table>
      <data-form ref="dataForm" @ok="handleOk" />
      <grap-form ref="grapForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import DataForm from './modules/DataForm'
import GrapForm from './modules/GrapForm'
import { tree, fetchOne, removeById } from '@/api/admin/workflow/instance/index'
const columns = [
  { title: '流程名称', dataIndex: 'workflowName', width: 240, key: 'workflowName' },
  { title: '流程状态',
    dataIndex: 'workflowStatus',
    width: 120,
    key: 'workflowStatus',
    customRender: (text, row, index) => {
      if (!text) {
        return ''
      } else if (text === '1') {
        return '处理中'
      } else if (text === '2') {
        return '已完成'
      }
    }
  },
  { title: '流程描述', dataIndex: 'workflowRmk', width: 400, key: 'workflowRmk' },
  { title: '操作', dataIndex: '', key: 'x', width: 220, scopedSlots: { customRender: 'action' } }
]
export default {
  components: {
    DataForm,
    GrapForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/workflow/instance:insert'),
        update: this.checkButtonAuth('/admin/workflow/instance:update'),
        delete: this.checkButtonAuth('/admin/workflow/instance:delete'),
        select: this.checkButtonAuth('/admin/workflow/instance:select')
      },
      data: [],
      queryParam: {},
      loading: false,
      columns,
      selectedRowKeys: [],
      selectedRows: [],
      expandedRowKeys: []
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    handleAdd (workflowId) {
      this.$refs.dataForm.add(workflowId)
    },
    handleEdit (record) {
      fetchOne(record.workflowId).then(res => {
          if (res.code === 0) {
            this.$refs.dataForm.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
    },
    handleDelete (id, type) {
      removeById(id).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
    },
    handleOk () {
      this.loadData()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    onClearSelected () {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    handleExpandedRowsChange (expandedRows) {
      this.expandedRowKeys = expandedRows
    },
    handleGrap (record) {
      this.$refs.grapForm.handleShow(record.workflowId, record.workflowName)
    },
    loadData () {
      this.loading = true
      tree(this.queryParam)
        .then(res => {
          this.data = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    reset () {
      this.queryParam = {}
      this.loadData()
    }
  }
}
</script>
