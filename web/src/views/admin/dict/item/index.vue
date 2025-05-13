<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="字典ID">
                  <a-input v-model="queryParam.dictId" placeholder="字典ID" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="字典项文本">
                  <a-input v-model="queryParam.itemText" placeholder="字典项文本" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="字典项值">
                  <a-input v-model="queryParam.itemValue" placeholder="字典项值" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="字典项描述">
                  <a-input v-model="queryParam.description" placeholder="字典项描述" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="字典项排序">
                  <a-input-number v-model="queryParam.sortOrder" placeholder="字典项排序" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="状态（1启用 0不启用）">
                  <a-input-number v-model="queryParam.status" placeholder="状态（1启用 0不启用）" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="创建时间">
                  <a-input v-model="queryParam.createTime" placeholder="创建时间" />
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="(!advanced && 8) || 24" :sm="24">
              <span
                class="table-page-search-submitButtons"
                :style="(advanced && { float: 'right', overflow: 'hidden' }) || {}"
              >
                <a-button v-if="authBtns.select" type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button
                  icon="reload"
                  v-if="authBtns.select"
                  style="margin-left: 8px"
                  @click="() => (queryParam = {})"
                >重置</a-button
                >
                <a-button
                  v-if="authBtns.insert"
                  style="margin-left: 8px"
                  type="primary"
                  icon="plus"
                  @click="$refs.dataForm.add()"
                >新增</a-button
                >

                <a v-if="authBtns.select" @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'" />
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a v-if="authBtns.update" class="table_blue" href="javascript:;" @click="handleEdit(record.srvId)">【修改】</a>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.srvId)">
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
import { fetch, fetchOne, removeById } from '@/api/admin/dict/item/index'

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
        insert: this.checkButtonAuth('/dict/item:insert'),
        update: this.checkButtonAuth('/dict/item:update'),
        delete: this.checkButtonAuth('/dict/item:delete'),
        select: this.checkButtonAuth('/dict/item:select')
      },
      // 高级搜索 展开/关闭  默认关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          key: 'dictId',
          title: '字典ID',
          dataIndex: 'dictId'
        },
        {
          key: 'itemText',
          title: '字典项文本',
          dataIndex: 'itemText'
        },
        {
          key: 'itemValue',
          title: '字典项值',
          dataIndex: 'itemValue'
        },
        {
          key: 'description',
          title: '描述',
          dataIndex: 'description'
        },
        {
          key: 'sortOrder',
          title: '排序',
          dataIndex: 'sortOrder'
        },
        {
          key: 'status',
          title: '状态（1启用 0不启用）',
          dataIndex: 'status'
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
          width: 150,
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetch(this.dictCode).then(res => {
          if (res.code === 0) {
            return res.data
          } else {
            return {}
          }
        })
      },
      dictCode: null,
      selectedRowKeys: [],
      selectedRows: [],
      // custom table alert & rowSelection
      options: {
        alert: {
          show: false,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
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
