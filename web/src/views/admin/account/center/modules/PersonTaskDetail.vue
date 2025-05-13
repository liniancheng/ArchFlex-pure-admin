<template>
  <!-- 任务详细 -->
  <div class="taskDetails">
    <div class="person_title">
      <img src="~@/assets/img/png_35.png" />
      <h2>{{ taskInfo.nodeName }}</h2>
    </div>
    <a-row>
      <a-col :span="24">
        <keep-alive>
          <component
            v-if="taskInfo.detailUrl"
            :is="taskDetailComponent"
            :instanceId="taskInfo.instanceId"
          ></component>
        </keep-alive>
      </a-col>
    </a-row>
    <a-row v-if="agreeFlag === ''">
      <a-col :span="14">
        <p class="from_head">审批操作：</p>
        <a-select v-model="taskInfo.agreeFlag" style="width: 160px">
          <a-select-option value="1">
            通过
          </a-select-option>
          <a-select-option value="0">
            退回
          </a-select-option>
        </a-select>
      </a-col>
    </a-row>
    <a-row style="margin-top:20px">
      <a-col :span="14">
        <p class="from_head">审批记录：</p>
        <div class="messageBoard">
          <taskOperList :opers="taskOpers"></taskOperList>
          <a-textarea v-if="agreeFlag === ''" placeholder="审批说明" v-model="taskInfo.operRmk"/>
        </div>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="24" style="text-align: center;pading-top:20px">
        <a-button shape="round" v-if="agreeFlag === ''" type="primary" @click="operTask">确定</a-button>
        <a-button shape="round" @click="returnClick(4)" style="margin-left:20px;">返回</a-button>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import { operTask } from '@/api/admin/account'
import events from '@/utils/events'
import taskOperList from '../../../messageBoard/taskOperList'

export default {
  components: {
    taskOperList
  },
  props: {
    taskInfo: { type: Object, required: true }
  },
  data () {
    return {
      agreeFlag: this.taskInfo.agreeFlag,
      taskOpers: this.taskInfo.opers
    }
  },
  computed: {
    taskDetailComponent () {
      if (!this.taskInfo.detailUrl) {
        return null
      }
      return () => import(`@/${this.taskInfo.detailUrl}`)
    }
  },
  methods: {
    operTask () {
      if (this.taskInfo.agreeFlag === null || this.taskInfo.agreeFlag === '') {
        this.$message.error('请选择审批操作')
      } else {
        operTask(this.taskInfo).then(res => {
          if (res.code === 0) {
            this.$message.success('审批成功！')
            this.taskInfo.opers.push(res.data)
            events.$emit('OnTaskStatusChange', this.taskInfo)
            this.returnClick()
            this.type = 4
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    returnClick () {
      this.$emit('showTask', null)
    }
  }
}
</script>
