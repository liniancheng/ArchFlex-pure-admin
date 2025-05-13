<template>
  <div>
    <a-card :bordered="false">
      <div>
        <div class="table-operator" v-if="authBtns.insert">
          <a-button type="primary" icon="plus" @click="handleAddParent">新增</a-button>
          <a-button icon="sync" @click="loadData">刷新</a-button>
          <a-button type="primary" v-if="authBtns.insert" icon="download" @click="handleExportXls">下载模板</a-button>
          <a-upload
            v-if="authBtns.insert"
            name="file"
            :multiple="false"
            @change="changeFile"
            :showUploadList="false"
            :customRequest="customRequest"
          >
            <a-button type="primary"> <a-icon type="upload" />导入机构</a-button>
          </a-upload>
        </div>
        <!-- <a-table
          ref="table"
          size="default"
          rowKey="branchNo"
          :columns="columns"
          :data-source="data"
          :loading="loading"
        >
          <span slot="virFlag" slot-scope="text">
            <a-tag :color="text === '0' ? 'geekblue' : 'green'">{{ text ==='0'?'是':'否' }}</a-tag>
          </span>
          <span slot="branchType" slot-scope="text">
            <a-tag color="#2db7f5">{{ text === 'P'?'默认':text }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record" v-if="authBtns.update || authBtns.delete || authBtns.insert" >
            <template>
              <a class="table_blue" v-if="authBtns.insert" @click="handleAdd(record)" >【新增】</a>
              <a class="table_blue" v-if="authBtns.update" @click="$refs.createModal.edit(record)">【修改】</a>
              <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(record.branchNo)">
                <a class="table_orange" >【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table> -->
        <a-tree
          :replaceFields="{ children: 'children', title: 'branchName', key: 'branchNo' }"
          :blockNode="true"
          :selectable="false"
          :tree-data="data"
        >
          <template slot="branchName" slot-scope="item">
            <a-row>
              <a-col span="6"> {{ item.branchNo }}</a-col>
              <a-col span="12">{{ item.branchName }}</a-col>
              <a-col span="6" style="text-align:right;">
                <a v-if="authBtns.insert" @click="handleAdd(item)">【新增】</a>
                <a v-if="authBtns.update" @click="$refs.createModal.edit(item)">【修改】</a>
                <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(item.branchNo)">
                  <a class="table_orange">【删除】</a>
                </a-popconfirm></a-col
              >
            </a-row>
          </template>
        </a-tree>
        <data-form ref="createModal" @ok="handleOk" />
      </div>
    </a-card>
  </div>
</template>
<script>
import moment from 'moment'
import { getBranchs, deleteBranch, download, upload } from '@/api/admin/branch/index'
import DataForm from './modules/DataForm'
import { Ellipsis } from '@/components'

const columns = [
  { title: '机构号', dataIndex: 'branchNo', width: 150, key: 'branchNo' },
  { title: '机构名称', ellipsis: true, dataIndex: 'branchName', key: 'branchName' },
  { title: '机构简称', dataIndex: 'branchShortName', width: 150, key: 'branchShortName' },
  {
    title: '机构类型',
    dataIndex: 'branchType',
    width: 120,
    key: 'branchType',
    scopedSlots: { customRender: 'branchType' }
  },
  { title: '虚拟机构', dataIndex: 'virFlag', width: 120, key: 'virFlag', scopedSlots: { customRender: 'virFlag' } },
  { title: '操作', dataIndex: '', key: 'x', align: 'center', width: 250, scopedSlots: { customRender: 'action' } }
]

export default {
  components: {
    DataForm,
    Ellipsis
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/system/branch:insert'),
        update: this.checkButtonAuth('/admin/system/branch:update'),
        delete: this.checkButtonAuth('/admin/system/branch:delete'),
        select: this.checkButtonAuth('/admin/system/branch:select')
      },
      data: [],
      loading: false,
      columns,
      selectedRowKeys: [],
      selectedRows: [],
      expandedRowKeys: [],
      fileList: [],
      uploading: false
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    handleAddParent () {
      this.$refs.createModal.visible = true
      const record = { parentBranchNo: '-1', branchType: 'P', etFlag: '1', virFlag: '1' }
      this.$refs.createModal.addParent(record)
    },
    handleAdd (result) {
      this.$refs.createModal.visible = true
      const record = {
        parentBranchNo: result.branchNo,
        branchType: result.branchType,
        etFlag: '1',
        virFlag: result.virFlag
      }
      this.$refs.createModal.add(record)
    },
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    handleDelete (id) {
      deleteBranch(id)
        .then(res => {
          if (res.code === 0) {
            this.$message.success('删除成功!')
            this.handleOk()
          } else {
            this.$message.error('删除失败，请联系管理员！' + res.message)
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    loadData () {
      this.loading = true
      getBranchs()
        .then(res => {
          this.data = this.exchangeNodes(res.data)
        })
        .finally(() => {
          this.loading = false
        })
    },
    exchangeNodes (nodes) {
      if (nodes && nodes.length > 0) {
        return nodes.map(item => {
          const node = { ...item, scopedSlots: { title: 'branchName' }, children: this.exchangeNodes(item.children) }
          return node
        })
      } else {
        return null
      }
    },
    handleOk () {
      this.loadData()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    onClearSelected () {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    handleExpandedRowsChange (expandedRows) {
      this.expandedRowKeys = expandedRows
    },
    handleExportXls () {
      const fileName = '机构导入模板_' + moment().format('YYYYMMDDhhmmss') + '.xlsx'
      download().then(data => {
        // debugger
        if (!data) {
          this.$message.warning('文件下载失败')
          return
        }
        const blob = new Blob([data])
        // const fileName = fileName
        if ('download' in document.createElement('a')) {
          // 非IE下载
          const elink = document.createElement('a')
          elink.download = fileName
          elink.style.display = 'none'
          elink.href = URL.createObjectURL(blob)
          document.body.appendChild(elink)
          elink.click()
          URL.revokeObjectURL(blob) // 释放URL 对象
          document.body.removeChild(elink)
        } else {
          // IE10+下载
          navigator.msSaveBlob(blob, fileName)
        }
      })
    },
    // handleExportXls () {
    //   const fileName = '机构导入模板_' + moment().format('YYYYMMDDhhmmss') + '.xlsx'
    //   download().then((data) => {
    //     if (!data) {
    //       this.$message.warning('文件下载失败')
    //       return
    //     }
    //     const blob = new Blob([data])
    //     if (typeof window.navigator.msSaveBlob !== 'undefined') {
    //       window.navigator.msSaveBlob(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }), fileName)
    //     } else {
    //       const url = window.URL.createObjectURL(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }))
    //       const link = document.createElement('a')
    //       link.style.display = 'none'
    //       link.href = url
    //       link.setAttribute('download', fileName)
    //       document.body.appendChild(link)
    //       link.click()
    //       document.body.removeChild(link)
    //       window.URL.revokeObjectURL(url)
    //     }
    //   })
    // },
    changeFile (info) {
      // 上传文件
      if (info.file.status === 'done') {
        this.$message.success(`${info.file.name} 上传成功`)
      } else if (info.file.status === 'error') {
        this.$message.error(`${info.file.name} 上传失败.`)
      }
    },
    customRequest (data) {
      // 上传提交
      const formData = new FormData()
      formData.append('file', data.file)
      upload(formData)
        .then(res => {
          if (res.code === 0) {
            this.$message.success('上传成功!')
            this.handleOk()
          } else {
            this.$message.error(res.message)
            this.handleOk()
          }
        })
        .catch(function (error) {
          this.$message.error(error)
        })
    }
  }
}
</script>
<style lang="less">
.ant-tree > li:nth-child(2n) {
  background: #f9f9f9;
}
</style>
