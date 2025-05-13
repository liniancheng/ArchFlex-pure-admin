<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="参数名称">
                <a-input v-model="queryParam.paramName" allowClear placeholder="请输入参数名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-button style="margin-left: 8px" type="primary" icon="plus" @click="$refs.dataForm.add()">新增</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="paramId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleEdit(record.paramId)">【修改】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.paramId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import DataForm from './modules/DataForm'
import { STable, Ellipsis } from '@/components'
import { fetch, fetchOne, removeById } from '@/api/config/pubcode/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm
  },
  data () {
    return {
      mdl: {},
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
            title: '参数名称',
            dataIndex: 'paramName',
            key: 'paramName'
        },
        {
            title: '参数值',
            dataIndex: 'paramValue',
            key: 'paramValue'
        },
        {
            title: '参数描述',
            dataIndex: 'paramRmk',
            key: 'paramRmk'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 200,
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetch(Object.assign(parameter, this.queryParam)).then(res => {
            if (res.code === 0) {
                return res.data
            } else {
                return {}
            }
        })
      },
      selectedRowKeys: [],
      selectedRows: [],
      // custom table alert & rowSelection
      options: {
        alert: { show: false, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: null
      },
      optionAlertShow: false
    }
  },
  methods: {
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
      this.$refs.table.refresh()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    }
  }
}
</script>
