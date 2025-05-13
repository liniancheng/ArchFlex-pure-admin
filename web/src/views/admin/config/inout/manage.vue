<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-button icon="reload" @click="handleLoad" style="margin-right:8px;">刷新</a-button>
          <a-button type="primary" icon="plus" @click="handleAdd('-1')">添加</a-button>
        </a-form>
        <br/>
      </div>
      <a-table
        ref="table"
        size="default"
        rowKey="migId"
        :columns="columns"
        :data-source="data"
        :loading="loading"
        :pagination="{ showSizeChanger: true, showTotal: total => `共有 ${total} 条数据` }"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleAdd(record.migId)">【新增】</a>
            <a @click="handleEdit(record.migId)">【修改】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.migId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </a-table>
    </a-card>
    <data-form ref="dataForm" @ok="handleOk" />
  </div>
</template>

<script>
import DataForm from './modules/DataForm'
import { STable, Ellipsis } from '@/components'
import { fetch, fetchOne, removeById } from '@/api/config/inout/manage'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm
  },
  data () {
    return {
      // 表头
      columns: [
        { title: '表中文名', dataIndex: 'migTabCname', key: 'migTabCname', ellipsis: true },
        { title: '表英文名', dataIndex: 'migTabName', key: 'migTabName', ellipsis: true },
        { title: '表类型',
          dataIndex: 'migType',
          key: 'migType',
          width: 80,
          customRender: (text, row, index) => {
            if (text === '0') {
              return <a-tag color="#58bbf8">目录</a-tag>
            } else if (text === '1') {
              return <a-tag color="#58bbf8">实体表</a-tag>
            } else if (text === '2') {
              return <a-tag color="#58bbf8">关系表</a-tag>
            }
          } },
        { title: '显示字段', dataIndex: 'migColName', key: 'migColName', ellipsis: true },
        { title: '操作', dataIndex: 'action', width: 200, align: 'center', scopedSlots: { customRender: 'action' } }
      ],
      loading: true,
      data: []
    }
  },
  mounted () {
    this.handleLoad()
  },
  methods: {
    handleLoad () {
      this.loading = true
      fetch().then(res => {
        if (res.code === 0) {
          this.data = res.data
        }
      }).finally(() => {
        this.loading = false
      })
    },
    handleAdd (pId) {
      this.$refs.dataForm.add(pId)
    },
    handleEdit (id) {
      fetchOne(id).then(res => {
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
      this.handleLoad()
    }
  }
}
</script>
