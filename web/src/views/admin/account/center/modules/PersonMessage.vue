<template>
  <!-- 消息公告 -->
  <div class="message">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>消息公告</h2>
      <a-checkbox @change="messageReadFlagChange">仅看未读消息</a-checkbox>
      <a-input-search
        v-model="searchName"
        placeholder="请输入搜索的标题和消息内容"
        size="large"
        class="search"
        @search="loadMessageData(true)"
      >
        <a-button slot="enterButton" type="primary" icon="search">搜索</a-button>
      </a-input-search>
    </div>
    <div class="tool">
      <a-radio-group default-value="all" button-style="solid" @change="messageTypeChange">
        <a-radio-button value="all">全部</a-radio-button>
        <a-radio-button value="anno">系统公告</a-radio-button>
        <a-radio-button value="mess">我的消息</a-radio-button>
      </a-radio-group>
      <br />
      <a-button type="primary" class="read" icon="mail" :disabled="!isSelectMessage" @click="handleListOper('read')" >标记已读</a-button >
      <a-button type="primary" class="delete" icon="close" :disabled="!isSelectMessage" @click="handleListOper('delete')" >删除</a-button>
    </div>
    <a-table
      v-if="messageType === 'all'"
      rowKey="id"
      :rowSelection="{ selectedRowKeys: allDataSelectedRowKeys, onChange: allDataOnSelectChange }"
      :columns="allDataColumns"
      :data-source="allData"
      :pagination="allDataPagination"
      @change="handleTableChange"
      :loading="loading"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.status === null || record.status === '0'"
            @click="handleReadFlag(record.typeId, record.id)"
          >【未读】</a
          >
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.status !== null && record.status === '1'"
            disabled="true"
          >【已读】</a
          >
          <a-popconfirm title="确定要删除?" @confirm="() => handleDeleFlag(record.typeId, record.id)">
            <a class="table_orange">【删除】</a>
          </a-popconfirm>
          <a class="table_blue" @click="viewMessageDetails(record)">【查看】</a>
        </template>
      </span>
    </a-table>
    <a-table
      v-if="messageType === 'anno'"
      rowKey="annoId"
      :rowSelection="{ selectedRowKeys: annoDataSelectedRowKeys, onChange: annoDataOnSelectChange }"
      :columns="annoDataColumns"
      :data-source="annoData"
      :pagination="annoDataPagination"
      @change="handleTableChange"
      :loading="loading"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.readFlag === null || record.readFlag === '0'"
            @click="handleReadFlag('anno', record.annoId)"
          >【未读】</a
          >
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.readFlag !== null && record.readFlag === '1'"
            disabled="true"
          >【已读】</a
          >
          <a-popconfirm title="确定要删除?" @confirm="() => handleDeleFlag('anno', record.annoId)">
            <a class="table_orange">【删除】</a>
          </a-popconfirm>
          <a class="table_blue" @click="viewMessageDetails(record)">【查看】</a>
        </template>
      </span>
    </a-table>
    <a-table
      v-if="messageType === 'mess'"
      rowKey="messageId"
      :rowSelection="{ selectedRowKeys: messDataSelectedRowKeys, onChange: messDataOnSelectChange }"
      :columns="messDataColumns"
      :data-source="messData"
      :pagination="messDataPagination"
      @change="handleTableChange"
      :loading="loading"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.messageStatus === null || record.messageStatus === '0'"
            @click="handleReadFlag('mess', record.messageId)"
          >【未读】</a
          >
          <a
            href="javascript:;"
            class="table_blue"
            v-if="record.messageStatus !== null && record.messageStatus === '1'"
            disabled="true"
          >【已读】</a
          >
          <a-popconfirm title="确定要删除?" @confirm="() => handleDeleFlag('mess', record.messageId)">
            <a class="table_orange">【删除】</a>
          </a-popconfirm>
          <a class="table_blue" @click="viewMessageDetails(record)">【查看】</a>
        </template>
      </span>
    </a-table>
  </div>
</template>
<script>

import { download } from '@/api/admin/home'
import { getAllMessages, getMessages, getAnnos, readMessage, deleteMessage } from '@/api/admin/account'
export default {
  data () {
    return {
      mdl: {},
      loading: false,
      // 消息公告
      messageType: 'all',
      searchName: '',
      messageStatus: '',
      attrList: [],
      allData: [],
      messData: [],
      annoData: [],
      allDataPagination: {},
      messDataPagination: {},
      annoDataPagination: {},
      allDataColumns: [
        { title: '消息标题', dataIndex: 'title' },
        {
          title: '接收时间',
          dataIndex: 'fromTime',
          customRender: (text, row, index) => {
            return this.formatDate(new Date(text))
          }
        },
        { title: '消息类别', dataIndex: 'typeName' },
        { title: '操作', dataIndex: '', key: 'x', scopedSlots: { customRender: 'action' } }
      ],
      messDataColumns: [
        { title: '消息标题', dataIndex: 'messageTitle' },
        {
          title: '接收时间',
          dataIndex: 'createTime',
          customRender: (text, row, index) => {
            return this.formatDate(new Date(text))
          }
        },
        {
          title: '消息类别',
          dataIndex: 'messageType',
          customRender: (text, row, index) => {
            return '我的消息'
          }
        },
        { title: '操作', dataIndex: '', key: 'x', scopedSlots: { customRender: 'action' } }
      ],
      annoDataColumns: [
        { title: '消息标题', dataIndex: 'annoTitle' },
        {
          title: '接收时间',
          dataIndex: 'createTime',
          customRender: (text, row, index) => {
            return this.formatDate(new Date(text))
          }
        },
        { title: '消息类别', dataIndex: 'typeName' },
        { title: '操作', dataIndex: '', key: 'x', scopedSlots: { customRender: 'action' } }
      ],
      allDataSelectedRowKeys: [],
      annoDataSelectedRowKeys: [],
      messDataSelectedRowKeys: [],
      allDataSelectedRows: [],
      messageInfo: {} // 当前查看的消息
    }
  },
  computed: {
    isSelectMessage () {
      if (this.messageType === 'all') {
        return this.allDataSelectedRowKeys.length > 0
      } else if (this.messageType === 'anno') {
        return this.annoDataSelectedRowKeys.length > 0
      } else if (this.messageType === 'mess') {
        return this.messDataSelectedRowKeys.length > 0
      }
      return false
    }
  },

  // 处理公告相关 end
  methods: {
    load (type) {
      this.messageType = type
      this.loadMessageData()
    },
    // -------------------------消息公告-----------------------------
    // 未读消息
    messageReadFlagChange (e) {
      if (e.target.checked) {
        this.messageStatus = '0'
      } else {
        this.messageStatus = ''
      }
      this.loadMessageData(true)
    },

    // 查看消息详情
    viewMessageDetails (record) {
      if (this.messageType === 'all') {
        if (record.status === null || record.status === '0') {
          this.handleReadFlag(record.typeId, record.id)
        }
        // this.getAttract(record.id)
        this.messageInfo = {
          id: record.id,
          title: record.title,
          content: record.content,
          time: record.fromTime,
          user: record.loginName,
          type: 'all',
          attr: []
        }
      } else if (this.messageType === 'anno') {
        if (record.readFlag === null || record.readFlag === '0') {
          this.handleReadFlag('anno', record.annoId)
        }
        // this.getAttract(record.annoId)
        this.messageInfo = {
          id: record.annoId,
          title: record.annoTitle,
          content: record.annoContent,
          time: record.createTime,
          type: 'anno',
          user: record.loginName
        }
      } else if (this.messageType === 'mess') {
        if (record.messageStatus === null || record.messageStatus === '0') {
          this.handleReadFlag('mess', record.messageId)
        }
        this.messageInfo = {
          title: record.messageTitle,
          content: record.messageContent,
          time: record.createTime,
          user: '',
          type: 'mess',
          attr: []
        }
      }
      this.$emit('loadMessage', this.messageInfo)
    },
    // 返回
    returnClick (type) {
      if (this.type === 6) {
        this.taskInfo.agreeFlag = this.agreeFlag
      }
      this.type = type
    },
    // 处理公告相关 begin
    handleExport (record) {
      download(record.uid).then(res => {
        this.handleDownloadResponse(record.name, res)
      })
    },
    handleDownloadResponse (acceName, res) {
      const content = res
      const blob = new Blob([content])
      const fileName = acceName
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
      // this.confirmLoading = false
    },
    // 处理公告相关 end
    loadMessageData (search) {
      const messageType = this.messageType
      this.messageType = ''
      if (search) {
        this.allDataPagination = { current: 1 }
        this.messDataPagination = { current: 1 }
        this.annoDataPagination = { current: 1 }
      }
      this.loading = true
      if (messageType === 'all') {
        getAllMessages(
          Object.assign(this.allDataPagination, { keyword: this.searchName, messageStatus: this.messageStatus })
        )
          .then(res => {
            this.allData = res.data.records
            this.allDataPagination = { current: res.data.current, pageSize: res.data.size, total: res.data.total }
          })
          .finally(() => {
            this.loading = false
            this.messageType = messageType
          })
      } else if (messageType === 'mess') {
        getMessages(
          Object.assign(this.messDataPagination, { keyword: this.searchName, messageStatus: this.messageStatus })
        )
          .then(res => {
            this.messData = res.data.records
            this.messDataPagination = { current: res.data.current, pageSize: res.data.size, total: res.data.total }
          })
          .finally(() => {
            this.loading = false
            this.messageType = messageType
          })
      } else if (messageType === 'anno') {
        getAnnos(Object.assign(this.annoDataPagination, { keyword: this.searchName, readFlag: this.messageStatus }))
          .then(res => {
            this.annoData = res.data.records
            this.annoDataPagination = { current: res.data.current, pageSize: res.data.size, total: res.data.total }
          })
          .finally(() => {
            this.loading = false
            this.messageType = messageType
          })
      }
    },
    messageTypeChange (e) {
      this.messageType = e.target.value
      this.loadMessageData()
    },
    handleTableChange (pagination, filters, sorter) {
      if (this.messageType === 'all') {
        const pager = { ...this.allDataPagination }
        pager.current = pagination.current
        this.allDataPagination = pager
        this.loadMessageData()
      } else if (this.messageType === 'mess') {
        const pager = { ...this.messDataPagination }
        pager.current = pagination.current
        this.messDataPagination = pager
        this.loadMessageData()
      } else if (this.messageType === 'anno') {
        const pager = { ...this.annoDataPagination }
        pager.current = pagination.current
        this.annoDataPagination = pager
        this.loadMessageData()
      }
    },
    handleReadFlag (type, id) {
      readMessage(type, id).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
          this.loadMessageData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleDeleFlag (type, id) {
      deleteMessage(type, id).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
          this.loadMessageData(true)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleListOper (operType) {
      let annoIds = ''
      let messIds = ''
      if (this.messageType === 'all') {
        if (this.allDataSelectedRows.length > 0) {
          for (let i = 0; i < this.allDataSelectedRows.length; i++) {
            if (this.allDataSelectedRows[i].typeId === 'anno') {
              annoIds += this.allDataSelectedRows[i].id + ','
            } else if (this.allDataSelectedRows[i].typeId === 'mess') {
              messIds += this.allDataSelectedRows[i].id + ','
            }
          }
        }
      } else if (this.messageType === 'anno') {
        if (this.annoDataSelectedRowKeys.length > 0) {
          annoIds = this.annoDataSelectedRowKeys.join(',')
        }
      } else if (this.messageType === 'mess') {
        if (this.messDataSelectedRowKeys.length > 0) {
          messIds = this.messDataSelectedRowKeys.join(',')
        }
      }
      if (operType === 'read') {
        if (annoIds !== '' && messIds !== '') {
          readMessage('anno', annoIds).then(res => {
            if (res.code === 0) {
              readMessage('mess', messIds).then(res => {
                if (res.code === 0) {
                  this.$message.success(res.message)
                  this.loadMessageData(true)
                } else {
                  this.$message.error(res.message)
                }
              })
            } else {
              this.$message.error(res.message)
            }
          })
        } else if (annoIds !== '') {
          this.handleReadFlag('anno', annoIds)
        } else if (messIds !== '') {
          this.handleReadFlag('mess', messIds)
        }
      } else if (operType === 'delete') {
        if (annoIds !== '' && messIds !== '') {
          deleteMessage('anno', annoIds).then(res => {
            if (res.code === 0) {
              deleteMessage('mess', messIds).then(res => {
                if (res.code === 0) {
                  this.$message.success(res.message)
                  this.loadMessageData(true)
                } else {
                  this.$message.error(res.message)
                }
              })
            } else {
              this.$message.error(res.message)
            }
          })
        } else if (annoIds !== '') {
          this.handleDeleFlag('anno', annoIds)
        } else if (messIds !== '') {
          this.handleDeleFlag('mess', messIds)
        }
      }
    },
    allDataOnSelectChange (selectedRowKeys, selectedRows) {
      this.allDataSelectedRowKeys = selectedRowKeys
      this.allDataSelectedRows = selectedRows
    },
    annoDataOnSelectChange (selectedRowKeys, selectedRows) {
      this.annoDataSelectedRowKeys = selectedRowKeys
    },
    messDataOnSelectChange (selectedRowKeys, selectedRows) {
      this.messDataSelectedRowKeys = selectedRowKeys
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
