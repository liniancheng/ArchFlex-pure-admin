<template>
  <!-- 帮助文档 -->
  <div class="message">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>帮助文档</h2>
      <a-input-search
        v-model="fileName"
        placeholder="请输入搜索的文档名称"
        size="large"
        class="search"
        @search="loadData(true)"
      >
        <a-button slot="enterButton" type="primary" icon="search">搜索</a-button>
      </a-input-search>
    </div>
    <a-table
      rowKey="fileId"
      :columns="dataColumns"
      :data-source="data"
      :pagination="dataPagination"
      @change="handleTableChange"
      :loading="loading"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a class="table_blue" @click="downloadFile(record)">【下载】</a>
        </template>
      </span>
    </a-table>
  </div>
</template>
<script>

import { fetch, download } from '@/api/config/helpFile/index'
export default {
  data () {
    return {
      loading: false,
      fileName: '',
      data: [],
      dataPagination: {},
      dataColumns: [
        { title: '文件名称', dataIndex: 'fileName' },
        {
          title: '文件大小',
          dataIndex: 'fileSize',
          customRender: (text, row, index) => {
            return this.formatSize(text, 2)
          }
        },
        { title: '操作', dataIndex: '', key: 'x', align: 'center', scopedSlots: { customRender: 'action' } }
      ]
    }
  },
  methods: {
    load () {
      this.loadData(false)
    },
    downloadFile (record) {
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
    loadData (search) {
      if (search) {
        this.dataPagination = { current: 1 }
      }
      this.loading = true
      fetch(
        Object.assign(this.dataPagination, { fileName: this.fileName })
      ).then(res => {
        this.data = res.data.records
        this.dataPagination = { current: res.data.current, pageSize: res.data.size, total: res.data.total }
      }).finally(() => {
        this.loading = false
      })
    },
    handleTableChange (pagination, filters, sorter) {
      const pager = { ...this.dataPagination }
      pager.current = pagination.current
      this.dataPagination = pager
      this.loadMessageData()
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
<style lang="less" scoped>
.tool{
  .ant-radio-button-wrapper:not(:first-child)::before{
    background-color: #ffffff;
  }
}

a[disabled='disabled'] {
  color: #e1e1e1;
}
</style>
