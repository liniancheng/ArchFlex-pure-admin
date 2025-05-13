<template>
  <a-modal
    :title="modalTitle"
    :width="1040"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"

  >
    <div>
      <a-card :bordered="false">
        <a-table
          ref="table"
          size="default"
          rowKey="nodeId"
          :pagination="false"
          :columns="columns"
          :data-source="data"
          :loading="loading"
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a
                class="table_blue"
                @click="handleAdd(record)">
                【新增】
              </a>
              <a
                v-if="record.nodeId !== '-1'"
                class="table_blue"
                @click="handleAuth(record)">
                【权限】
              </a>
              <a
                v-if="record.nodeId !== '-1'"
                class="table_blue"
                @click="handleEdit(record)">
                【修改】
              </a>
              <a-popconfirm title="确定要删除?" v-if="record.nodeId !== '-1'" @confirm="() => handleDelete(record.nodeId)">
                <a class="table_orange">【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table>
        <data-form ref="dataForm" @ok="handleOk" />
        <auth-form ref="authForm" @ok="handleOk" />
      </a-card>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import DataForm from './NodeForm'
import AuthForm from './AuthForm'
import { tree, fetchOne, removeById } from '@/api/admin/workflow/workflow/node'

export default {
  name: 'TableList',
  components: {
    DataForm,
    AuthForm
  },
  data () {
    return {
      data: [],
      queryParam: {},
      loading: false,
      columns: [
        { title: '节点名称', dataIndex: 'nodeName', width: 120, key: 'nodeName' },
        { title: '节点描述', dataIndex: 'nodeRmk', width: 150, key: 'nodeRmk' },
        { title: '操作', dataIndex: '', key: 'x', width: 200, scopedSlots: { customRender: 'action' } }
      ],
      visible: false,
      confirmLoading: false,
      modalTitle: ''
    }
  },
  methods: {
    handleShow (workflowId, workflowName) {
      this.modalTitle = workflowName + ' - 节点列表'
      this.visible = true
      this.queryParam.workflowId = workflowId
      this.loadData()
    },
    handleAdd (record) {
      this.$refs.dataForm.add(this.queryParam.workflowId, record.nodeId, record.nodeLevel)
    },
    handleEdit (record) {
      fetchOne(record.nodeId).then(res => {
        if (res.code === 0) {
            this.$refs.dataForm.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
      })
    },
    handleDelete (id) {
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
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    },
    handleCancel () {
      this.visible = false
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
    handleAuth (record) {
      this.$refs.authForm.handleShow(this.queryParam.workflowId, record.nodeId, record.nodeName)
    }
  }
}
</script>
