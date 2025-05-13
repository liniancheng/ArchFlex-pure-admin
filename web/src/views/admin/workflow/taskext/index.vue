<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="任务名称">
                <a-input v-model="queryParam.extName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button v-if="authBtns.select" type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button v-if="authBtns.select" style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-button v-if="authBtns.insert" style="margin-left: 8px" type="primary" icon="plus" @click="$refs.dataForm.add()">新增</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="extId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a v-if="authBtns.update" class="table_blue" href="javascript:;" @click="handleEdit(record.extId)">【修改】</a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.extId)">
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
import { fetch, fetchOne, removeById } from '@/api/admin/workflow/taskext/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/workflow/taskext:insert'),
        update: this.checkButtonAuth('/admin/workflow/taskext:update'),
        delete: this.checkButtonAuth('/admin/workflow/taskext:delete'),
        select: this.checkButtonAuth('/admin/workflow/taskext:select')
      },
      mdl: {},
      // 查询参数
      queryParam: {},
      // 表头
      leng: this.$store.getters.leng,
      columns: [
        {
            title: '任务名称',
            dataIndex: 'extName',
            key: 'extName'
        },
        {
            title: '跳转URL',
            dataIndex: 'extUrl',
            key: 'extUrl'
        },
        {
            title: '任务描述',
            dataIndex: 'extRmk',
            key: 'extRmk'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 160,
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
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
