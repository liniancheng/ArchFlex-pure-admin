<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="文档名称">
                <a-input v-model="queryParam.fileName" placeholder="请输入文档名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-button style="margin-left: 8px" type="primary" icon="upload" @click="$refs.dataForm.add()">上传</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="fileId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleDownload(record)">【下载】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.fileId)">
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
import { fetch, download, removeById } from '@/api/config/helpFile/index'

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
            title: '文档名称',
            dataIndex: 'fileName',
            key: 'fileName'
        },
        {
            title: '文档大小',
            dataIndex: 'fileSize',
            key: 'fileSize',
            customRender: (text, row, index) => {
              return this.formatSize(text, 2)
            }
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
    },
    handleDownload (record) {
      download(record.fileId).then(res => {
        this.handleDownloadResponse(record.fileName, res)
      })
    },
    handleDownloadResponse (fileName, res) {
      const content = res
      const blob = new Blob([content])
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL 对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, fileName)
      }
    },
    formatSize (size, pointLength) {
      var unit
      var units = [ 'B', 'K', 'M', 'G', 'TB' ]
      while ((unit = units.shift()) && size > 1024) {
          size = size / 1024
      }
      return (unit === 'B' ? size : size.toFixed(pointLength === undefined ? 2 : pointLength)) + ' ' + unit
    }
  }
}
</script>
