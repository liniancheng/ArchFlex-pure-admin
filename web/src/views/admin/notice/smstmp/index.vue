<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="短信模板名称">
                <a-input v-model="queryParam.tempName" placeholder="短信模板名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="短信模板描述">
                <a-input v-model="queryParam.tempRmk" placeholder="短信模板描述"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="短信模板内容">
                  <a-input v-model="queryParam.tempHtml" placeholder="短信模板内容"/>
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
        rowKey="tempId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="tempContent" slot-scope="text">
          <span v-html="text"> {{ text }}</span>
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleEdit(record.tempId)">【修改】</a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.tempId)">
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
import { fetch, fetchOne, removeById } from '@/api/admin/notice/smstemp/index'

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
        insert: this.checkButtonAuth('/admin/notice/smstemp:insert'),
        update: this.checkButtonAuth('/admin/notice/smstemp:update'),
        delete: this.checkButtonAuth('/admin/notice/smstemp:delete'),
        select: this.checkButtonAuth('/admin/notice/smstemp:select')
      },
      // 高级搜索 展开/关闭  默认关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
            key: 'tempName',
            title: '短信模板名称',
            dataIndex: 'tempName'
        },
        // {
        //     key: 'tempHtml',
        //     title: '短信模板内容',
        //     dataIndex: 'tempHtml',
        //   scopedSlots: { customRender: 'tempContent' }
        // },
        {
            key: 'tempRmk',
            title: '短信模板描述',
            dataIndex: 'tempRmk'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 150,
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
