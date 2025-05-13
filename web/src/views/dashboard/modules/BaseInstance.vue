<template>
  <a-card class="content_zh_item a_card" :bodyStyle="bodyStyle" :loading="loading">
    <div slot="title">
      <iconTodo width="18px" height="18px"/>待办任务
    </div>
    <iconMore slot="extra" width="30px" height="30px" @click="gotoPersonCenter(4)"/>
    <a-list class="imoia-task-list" size="small" :data-source="[...undoTaskList, ...doneTaskList]" :split="split">
      <a-list-item slot="renderItem" slot-scope="item" @click="viewTask(item)">
        <div>
          <span>{{ item.operStatus === '0' ? '【待处理】' : '【已处理】' }}</span> {{ item.instanceName }}
        </div>
        <span style="float:right;">{{ item.operTime | dateFrm }}</span>
      </a-list-item>
    </a-list>
    <task-view-form ref="taskViewForm" />
  </a-card>
</template>
<script>
import events from '@/utils/events'
import TaskViewForm from './TaskViewForm'
import moment from 'moment'
import { iconMore, iconTodo } from '@/core/icons'

export default {
  name: 'BaseInstance',
  components: {
    iconMore,
    iconTodo,
    TaskViewForm
  },
  // props: {
  //   dataSource: { type: Array, default: () => [] }
  // },
  data () {
    return {
      loading: true,
      split: false,
      bodyStyle: { height: '100%', width: '100%', overflow: 'hidden', padding: '0 15px' },
      dataSource: this.$store.getters.listTask
    }
  },
  mounted () {
    setTimeout(() => {
      this.loading = false
    }, 200)
    const self = this
    // 监听与更新我的任务
    events.$on('OnTaskStatusChange', function (taskInfo) {
      for (let i = 0; i < self.dataSource.length; i++) {
        if (self.dataSource[i].instanceId === taskInfo.instanceId) {
          self.dataSource[i] = taskInfo
          self.dataSource[i].operStatus = '1'
          break
        }
      }
    })
  },
  computed: {
    undoTaskList () {
      return this.dataSource.filter(task => task.operStatus === '0')
    },
    doneTaskList () {
      return this.dataSource.filter(task => task.operStatus === '1')
    }
  },
  filters: {
    dateFrm: function (e1) {
      return moment(e1).format('YYYY-MM-DD HH:mm')
    }
  },
  methods: {
    viewTask (record) {
      if (record.inodeId === '' && record.detailUrl !== '') {
        const url = record.detailUrl + record.instanceId + '?customTitle=' + record.instanceName
        this.$router.push({ path: url })
      } else {
        this.$refs.taskViewForm.show(record)
      }
    },
    gotoPersonCenter (type) {
      events.$emit('OnPersonTabChange', type)
      this.$store.commit('setRouteParam', type)
      this.$router.push({ name: 'menu.person' })
    }
  }
}
</script>
<style lang="less" scoped>
.imoia-task-list {
  .ant-list-item {
    font-size: 14px;
    cursor: pointer;
    padding: 1px 0;
    > div {
      float: left;
      width: calc(100% - 125px);
      display: block;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      span {
        color: #e13b08;
      }
    }
    > span {
      color: #333;
    }
  }
}
</style>
