<template>
  <!-- 我的任务 -->
  <div class="task">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>我的任务</h2>
    </div>
    <div class="tool">
      <a-radio-group v-model="taskType" default-value="-1" button-style="solid" @change="taskTypeChange">
        <a-radio-button value="-1">全部</a-radio-button>
        <a-radio-button value="0">待处理</a-radio-button>
        <a-radio-button value="1">已处理</a-radio-button>
      </a-radio-group>
    </div>
    <a-table
      v-show="!loading"
      rowKey="instanceId"
      :rowSelection="{ selectedRowKeys: taskDataSelectedRowKeys, onChange: taskDataOnSelectChange }"
      :columns="taskDataColumns"
      :data-source="taskData"
      :pagination="taskDataPagination"
      :loading="loading"
      @change="filterTable"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="viewTask(record)">【查看】</a>
        </template>
      </span>
    </a-table>
  </div>
</template>
<script>
import { getTasks } from '@/api/admin/account'
import { Ellipsis } from '@/components'
import moment from 'moment'
export default {
  components: {
    Ellipsis
  },
  data () {
    return {
      loading: true,
      taskType: '-1',
      taskData: [],
      taskDataPagination: {},
      taskDataColumns: [
        { title: '任务名称', dataIndex: 'instanceName', ellipsis: true },
        { title: '发起用户', dataIndex: 'createUserName', width: 150 },
        {
          title: '发起时间',
          width: 170,
          dataIndex: 'createTime',
          customRender: (text, row, index) => {
            return this.formatDate(new Date(text))
          }
        },
        {
          title: '任务状态',
          dataIndex: 'operStatus',
          width: 110,
          align: 'center',
          customRender: (text, row, index) => {
            if (text === '0') {
              return <span class="pending">【待处理】</span>
            } else if (text === '1') {
              return <span class="processing">【已处理】</span>
            }
          }
        },
        { title: '操作', dataIndex: '', key: 'x', width: 90, align: 'center', scopedSlots: { customRender: 'action' } }
      ],
      taskDataSelectedRowKeys: [],
      taskInfo: {},
      agreeFlag: '',
      taskOpers: []
    }
  },
   // 处理公告相关 begin
  filters: {
    dateFrm: function (e1) {
      return moment(e1).format('YYYY年MM月DD日')
    }
  },
  methods: {
    load (type) {
      this.taskType = type
      this.$nextTick(() => {
        this.loadTaskData()
      })
    },
    loadTaskData () {
      this.loading = true
      let status0 = '0'
      let status1 = '0'
      if (this.taskType === '-1') {
        status0 = '1'
        status1 = '1'
      } else if (this.taskType === '0') {
        status0 = '1'
        status1 = '0'
      } else if (this.taskType === '1') {
        status0 = '0'
        status1 = '1'
      }
      getTasks(Object.assign(this.taskDataPagination, { current: 1, pageSize: 10, total: 0, status0: status0, status1: status1 }))
        .then(res => {
          this.taskData = res.data.records
          this.taskDataPagination = { current: res.data.current, pageSize: res.data.size, total: res.data.total }
        })
        .finally(() => {
          this.loading = false
        })
    },
    filterTable (pagination, filters, sorter, { currentDataSource }) {
      this.taskDataPagination = pagination
      this.loadTaskData()
    },
    taskTypeChange (e) {
      this.taskType = e.target.value
      this.loadTaskData()
    },

    taskDataOnSelectChange (selectedRowKeys, selectedRows) {
      this.taskDataSelectedRowKeys = selectedRowKeys
    },
    viewTask (record) {
      if (record.inodeId === '' && record.detailUrl !== '') {
        const url = record.detailUrl + record.instanceId + '?customTitle=' + record.instanceName
        this.$router.push({ path: url })
      } else {
        this.$emit('loadTask', record, this.taskType)
      }
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
</style>
