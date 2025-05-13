<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="公告标题">
                <a-input v-model="queryParam.annoTitle" allowClear placeholder="请输入公告标题"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-button style="margin-left: 8px" type="primary" icon="plus" @click="handleAdd">新增</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="annoId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <!-- <span slot="annoLevel" slot-scope="text">
          <a-tag :color=" text === '1' ? '#52c41a' : '#ff7e05'">{{ text==='1' ? '重要' : '普通' }}</a-tag>
        </span> -->
        <span slot="valids" slot-scope="text">
          <a-tag :color=" text === 1 ? 'green' : 'red'">{{ text===1 ? '有效' : '无效' }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleEdit(record.annoId)">【修改】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.annoId)">
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
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
import { fetch, fetchOne, removeById } from '@/api/config/anno/index'
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
            title: '是否有效',
            dataIndex: 'isValid',
            key: 'isValid',
          scopedSlots: { customRender: 'valids' }
        },
        {
            title: '公告标题',
            dataIndex: 'annoTitle',
            key: 'annoTitle'
        },
        // {
        //     title: '公告等级',
        //     dataIndex: 'annoLevel',
        //     key: 'annoLevel',
        //     scopedSlots: { customRender: 'annoLevel' }
        // },
        {
            title: '生效时间',
            dataIndex: 'startTime',
            key: 'startTime',
            customRender: (text, row, index) => {
              return this.formatDate(new Date(text), 'yyyy/MM/DD hh:mm:ss')
            }
        },
        {
            title: '失效时间',
            dataIndex: 'endTime',
            key: 'endTime',
            customRender: (text, row, index) => {
              return this.formatDate(new Date(text), 'yyyy/MM/DD hh:mm:ss')
            }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 250,
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
    handleAdd () {
      const record = {
        annoLevel: '1',
        isValid: '1',
        annoContent: ''
      }
      this.$refs.dataForm.visible = true
      this.$refs.dataForm.add(record)
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
      this.$refs.table.refresh()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
