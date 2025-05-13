<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="服务器名称">
                <a-input v-model="queryParam.srvName" placeholder="服务器名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务器描述">
                <a-input v-model="queryParam.srvRmk" placeholder="服务器描述"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="用户名">
                  <a-input v-model="queryParam.loginName" placeholder="用户名"/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button v-if="authBtns.select" type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button v-if="authBtns.select" style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-button v-if="authBtns.insert" style="margin-left: 8px" type="primary" icon="plus" @click="$refs.dataForm.add()">新增</a-button>

                <a v-if="authBtns.select" @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="srvId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" v-if="authBtns.update || authBtns.delete" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleTest(record.srvId, record.srvName)">【测试】</a>
            <a class="table_blue" @click="handleEdit(record.srvId)">【修改】</a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.srvId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
      <test-form ref="testForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
import TestForm from './modules/TestForm'
import { fetch, fetchOne, removeById } from '@/api/admin/notice/mailsrv/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm,
    TestForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/notice/mailsrv:insert'),
        update: this.checkButtonAuth('/admin/notice/mailsrv:update'),
        delete: this.checkButtonAuth('/admin/notice/mailsrv:delete'),
        select: this.checkButtonAuth('/admin/notice/mailsrv:select')
      },
      // 高级搜索 展开/关闭  默认关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
            key: 'srvName',
            title: '服务器名称',
            dataIndex: 'srvName'
        },
        {
            key: 'srvRmk',
            title: '服务器描述',
            dataIndex: 'srvRmk'
        },
        {
            key: 'srvUrl',
            title: '服务器地址',
            dataIndex: 'srvUrl'
        },
        {
            key: 'defaultFrom',
            title: '邮箱地址',
            dataIndex: 'defaultFrom'
        },
        {
            key: 'loginName',
            title: '用户名',
            dataIndex: 'loginName'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 210,
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
    handleTest (srvId, srvName) {
      this.$refs.testForm.show(srvId, srvName)
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
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
