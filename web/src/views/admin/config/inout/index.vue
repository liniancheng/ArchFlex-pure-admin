<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-button icon="reload" @click="handleLoad" style="margin-right:8px;">刷新</a-button>
          <a-upload
            :showUploadList="false"
            :before-upload="beforeUpload"
            :customRequest="customRequest"
          >
            <a-button type="primary" icon="upload" style="margin-right:8px;">导入</a-button>
          </a-upload>
          <a-button type="primary" icon="download" @click="handleDownload">导出</a-button>
        </a-form>
      </div>
      <br/>
      <a-table
        ref="table"
        size="default"
        rowKey="key"
        :columns="columns"
        :data-source="data"
        :loading="loading"
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :pagination="{ showSizeChanger: true, showTotal: total => `共有 ${total} 条数据` }"
      >
      </a-table>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { fetch, download, upload } from '@/api/config/inout/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      // 表头
      columns: [
        {
          title: '资源名称',
          dataIndex: 'value',
          key: 'value'
        }
      ],
      selectedRowKeys: [],
      selectedRows: [],
      // custom table alert & rowSelection
      options: {
        alert: { show: false, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: null
      },
      optionAlertShow: false,
      data: [],
      loading: true
    }
  },
  mounted () {
    this.handleLoad()
  },
  methods: {
    handleLoad () {
      this.loading = true
      fetch().then(res => {
        if (res.code === 0) {
          this.data = res.data
        }
      }).finally(() => {
        this.loading = false
      })
    },
    handleDownload () {
      download(this.selectedRowKeys).then(res => {
        if (!res) {
          this.$message.error('数据导出失败')
        } else {
          const content = res
          const blob = new Blob([content])
          const fileName = 'export-' + this.formatDate(new Date(), 'yyyy/MM/DD hh:mm:ss') + '.bak'
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
          this.confirmLoading = false
        }
      })
    },
    beforeUpload (file, fileList) {
      const _this = this
      return new Promise((resolve, reject) => {
        if (!file.name.endsWith('.bak')) {
          _this.$message.error(file.name + '文件格式不正确，请检查！')
          // eslint-disable-next-line prefer-promise-reject-errors
          reject()
        }
        if (file.size / 1024 / 1024 > 10) {
          _this.$message.error(file.name + '大于10M，请检查！')
          // eslint-disable-next-line prefer-promise-reject-errors
          reject()
        } else {
          resolve()
        }
      })
    },
    customRequest (data) {
      // 上传提交
      upload(data.file).then((res) => {
        if (res.code === 0) {
          this.$message.success(res.message)
          this.handleLoad()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleOk () {
      this.handleLoad()
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
