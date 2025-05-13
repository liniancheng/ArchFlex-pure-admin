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
          rowKey="macroId"
          :pagination="false"
          :columns="columns"
          :data-source="data"
          :loading="loading"
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a
                v-if="record.macroType !== 'macro'"
                class="table_blue"
                @click="handleAdd(record)">
                【新增】
              </a>
              <a
                class="table_blue"
                v-if="record.macroType === 'macro'"
                @click="handleEdit(record)">
                【修改】
              </a>
              <a-popconfirm title="确定要删除?" v-if="record.macroType === 'macro'" @confirm="() => handleDelete(record.macroId)">
                <a class="table_orange">【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table>
        <data-form ref="dataForm" @ok="handleOk" />
      </a-card>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import DataForm from './MacoForm'
import { tree, fetchOne, removeById } from '@/api/admin/workflow/workflow/maco'

export default {
  name: 'TableList',
  components: {
    DataForm
  },
  data () {
    return {
      data: [],
      queryParam: {},
      loading: false,
      columns: [
        { title: '宏变量名称', dataIndex: 'macroName', width: 120, key: 'macroName' },
        { title: '宏变量标识', dataIndex: 'macroCode', width: 120, key: 'macroCode' },
        { title: '宏变量描述', dataIndex: 'macroRmk', width: 150, key: 'macroRmk' },
        { title: '操作', dataIndex: '', key: 'x', width: 200, scopedSlots: { customRender: 'action' } }
      ],
      visible: false,
      confirmLoading: false,
      modalTitle: ''
    }
  },
  methods: {
    handleShow (workflowId, workflowName) {
      this.modalTitle = workflowName + ' - 宏变量列表'
      this.visible = true
      this.queryParam.workflowId = workflowId
      this.loadData()
    },
    handleAdd (record) {
      this.$refs.dataForm.add(this.queryParam.workflowId, record.macroType)
    },
    handleEdit (record) {
      fetchOne(record.macroId).then(res => {
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
    }
  }
}
</script>
