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
              <a-form-item label="模板名称">
                <a-input v-model="queryParam.tempName" placeholder="模板名称"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="用户名">
                  <a-input v-model="queryParam.loginName" placeholder="用户名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="发送状态">
                  <a-select placeholder="请选择" v-model="queryParam.logState">
                    <a-select-option value="1">成功</a-select-option>
                    <a-select-option value="0">失败</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button v-if="authBtns.select" type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button v-if="authBtns.select" style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-popconfirm
                  v-if="authBtns.delete"
                  :disabled="selectedRowKeys.length === 0"
                  title="确定要批量删除选中数据?"
                  @confirm="() => handleDeleteSelected()">
                  <a-button
                    :disabled="selectedRowKeys.length === 0"
                    style="margin-left: 8px"
                    type="danger"
                    icon="delete">批量删除</a-button>
                </a-popconfirm>
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
        rowKey="logId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.logId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { fetchLog, removeSelected, removeLogById } from '@/api/admin/notice/mailsrv/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      authBtns: {
        select: this.checkButtonAuth('/admin/notice/log:select'),
        delete: this.checkButtonAuth('/admin/notice/log:delete')
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
            key: 'tempName',
            title: '模板名称',
            dataIndex: 'tempName'
        },
         {
            key: 'logState',
            title: '发送状态',
            dataIndex: 'logState',
            customRender: (text, row, index) => {
              if (text === '1') {
                return '成功'
              } else {
                return '失败'
              }
            }
        },
        {
            key: 'loginName',
            title: '用户名',
            dataIndex: 'loginName'
        },
        {
            key: 'createTime',
            title: '创建时间',
            customRender: (text, row, index) => {
              return this.formatDate(new Date(text))
            },
            dataIndex: 'createTime'
        },
         {
          title: '操作',
          dataIndex: 'action',
          width: 100,
          align: 'center',
          scopedSlots: {
            customRender: 'action'
         }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetchLog(Object.assign(parameter, this.queryParam)).then(res => {
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
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  methods: {
     handleDelete (id) {
        removeLogById(id).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
    },
    handleDeleteSelected () {
      removeSelected(this.selectedRowKeys).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
    },
    handleOk () {
      this.selectedRowKeys = []
      this.selectedRows = []
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
