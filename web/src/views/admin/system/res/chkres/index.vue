<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="数据源类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  placeholder="请选择"
                  v-model="queryParam.dbType"
                >
                  <a-select-option value="1"> mysql </a-select-option>
                  <a-select-option value="2"> db2 </a-select-option>
                  <a-select-option value="3"> orcle </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span
                class="table-page-search-submitButtons">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
                <a-button
                  style="margin-left: 8px"
                  type="primary"
                  icon="plus"
                  @click="$refs.dataForm.add()">新建</a-button>
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
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a href="javascript:;" @click="handleSelect(record.id)">【查看】</a>
            <a href="javascript:;" @click="handleEdit(record.id)">【修改】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.id)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk"/>
      <select-form ref="selectForm" @ok="handleOk"/>
    </a-card>
  </div>
</template>

<script>
  import { STable, Ellipsis } from '@/components'
  import DataForm from './modules/DataForm'
  import SelectForm from './modules/SelectForm'
  import { fetch, fetchOne, removeById } from '@/api/admin/res/index'
  export default {
    name: 'TableList',
    components: {
      STable,
      Ellipsis,
      DataForm,
      SelectForm
    },
    data () {
      return {
        // 查询参数
        queryParam: {},
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: 'index',
            align: 'center',
            width: 300,
            // customRender函数来渲染序号的数据
            customRender: (text, record, index) => index + 1,
            fixed: 'left'
          },
          {
            key: 'dbType',
            title: '数据源类型',
            dataIndex: 'dbType',
             align: 'center',
            customRender: (text, record, index) => {
              if (text === '1') {
                return 'mysql'
              }
              if (text === '2') {
                return 'db2'
              }
              if (text === '3') {
                return 'orcle'
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            width: 300,
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
          alert: {
            show: true,
            clear: () => {
              this.selectedRowKeys = []
            }
          },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange
          }
        },
        optionAlertShow: false
      }
    },
    methods: {
      handleSelect (id) {
        fetchOne(id).then(res => {
          if (res.code === 0) {
            this.$refs.selectForm.show(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
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
      }
    }
  }
</script>
