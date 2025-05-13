<template>
  <div>
    <a-card :bordered="false">
      <div v-if="authBtns.select" class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="流程名称">
                <a-input v-model="queryParam.workflowName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="流程描述">
                <a-input v-model="queryParam.workflowRmk" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="loadData">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="reset">重置</a-button>
                <a-button style="margin-left: 8px" type="primary" icon="plus" @click="$refs.typeForm.add()">新增</a-button>
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
            <a-dropdown v-if="authBtns.insert" >
              <a class="ant-dropdown-link" @click="e => e.preventDefault()">
                【新增】 <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
                <a-menu-item v-if="record.workflowType === 'type'">
                  <a
                    class="table_blue"
                    @click="handleAdd(record.workflowId)">
                    流程
                  </a>
                </a-menu-item>
                <a-menu-item v-if="record.workflowType === 'workflow'">
                  <a
                    class="table_blue"
                    @click="handleMaco(record)">
                    宏变量
                  </a>
                </a-menu-item>
                <a-menu-item v-if="record.workflowType === 'workflow'">
                  <a
                    class="table_blue"
                    @click="handleNode(record)">
                    节点
                  </a>
                </a-menu-item>
                <a-menu-item v-if="record.workflowType === 'workflow' && record.versionMax === '1'">
                  <a
                    class="table_blue"
                    @click="handleCopy(record)">
                    版本
                  </a>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
            <a
              v-if="authBtns.update"
              class="table_blue"
              @click="handleEdit(record)">
              【修改】
            </a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.workflowId, record.workflowType)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </a-table>
      <data-form ref="dataForm" @ok="handleOk" />
      <type-form ref="typeForm" @ok="handleOk" />
      <node-list ref="nodeList" @ok="handleOk" />
      <copy-form ref="copyForm" @ok="handleOk" />
      <maco-list ref="macoList" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import DataForm from './modules/DataForm'
import TypeForm from './modules/TypeForm'
import NodeList from './modules/NodeList'
import CopyForm from './modules/CopyForm'
import MacoList from './modules/MacoList'
import { tree, fetchOneWorkflow, removeWorkflowById } from '@/api/admin/workflow/workflow/index'
import { fetchOneType, removeTypeById } from '@/api/admin/workflow/workflow/type'
const columns = [
  { title: '流程名称', dataIndex: 'workflowName', width: 240, key: 'workflowName' },
  { title: '流程描述', dataIndex: 'workflowRmk', width: 400, key: 'workflowRmk' },
  { title: '操作', dataIndex: '', key: 'x', width: 220, scopedSlots: { customRender: 'action' } }
]
export default {
  components: {
    DataForm,
    TypeForm,
    NodeList,
    CopyForm,
    MacoList
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/workflow/workflow:insert'),
        update: this.checkButtonAuth('/admin/workflow/workflow:update'),
        delete: this.checkButtonAuth('/admin/workflow/workflow:delete'),
        select: this.checkButtonAuth('/admin/workflow/workflow:select')
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
    handleAdd (parentId) {
      this.$refs.dataForm.add(parentId)
    },
    handleEdit (record) {
      if (record.workflowType === 'type') {
        fetchOneType(record.workflowId).then(res => {
          if (res.code === 0) {
            this.$refs.typeForm.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      } else if (record.workflowType === 'workflow') {
        fetchOneWorkflow(record.workflowId).then(res => {
          if (res.code === 0) {
            this.$refs.dataForm.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    handleDelete (id, type) {
      if (type === 'type') {
        removeTypeById(id).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
      } else if (type === 'workflow') {
        removeWorkflowById(id).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    handleCopy (record) {
      this.$refs.copyForm.show(record.workflowId, record.workflowName)
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
    handleNode (record) {
      this.$refs.nodeList.handleShow(record.workflowId, record.workflowName)
    },
    handleMaco (record) {
      this.$refs.macoList.handleShow(record.workflowId, record.workflowName)
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
