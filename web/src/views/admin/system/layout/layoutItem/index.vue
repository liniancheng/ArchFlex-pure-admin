<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="数据项名称">
                <a-input v-model="queryParam.itemName" placeholder="数据项名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button v-if="authBtns.select" icon="search" type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button v-if="authBtns.select" icon="reload" style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
                <a-button v-if="authBtns.insert" style="margin-left: 8px" type="primary" icon="plus" @click="$refs.dataForm.add()">新增</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="itemId"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a v-if="authBtns.insert || authBtns.update" href="javascript:;" @click="handleAuth(record.itemId, record.itemName)">【授权】</a>
            <a v-if="authBtns.update" href="javascript:;" @click="handleEdit(record.itemId)">【修改】</a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.itemId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
      <data-auth-form ref="authForm"/>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
import DataAuthForm from './modules/DataAuthForm'
import { fetch, fetchOne, removeById } from '@/api/admin/layout/layoutItem/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm,
    DataAuthForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/layout/layoutItem:insert'),
        update: this.checkButtonAuth('/layout/layoutItem:update'),
        delete: this.checkButtonAuth('/layout/layoutItem:delete'),
        select: this.checkButtonAuth('/layout/layoutItem:select')
      },
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          key: 'itemName',
          title: '数据项名称',
          dataIndex: 'itemName',
          ellipsis: true,
          width: 200
        },
        {
          key: 'createTime',
          title: '创建时间',
          customRender: (text, row, index) => {
            return this.formatDate(new Date(text))
          },
          dataIndex: 'createTime',
          width: 180
        },
        {
          key: 'itemRmk',
          title: '数据项描述',
          dataIndex: 'itemRmk',
          ellipsis: true
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 220,
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
        alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  methods: {
    handleAuth (id, name) {
      this.$refs.authForm.show(id, name)
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
