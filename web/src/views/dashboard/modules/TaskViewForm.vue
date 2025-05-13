<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    width="800px"
  >
    <template slot="footer">
      <a-button @click="handleCancel">返回</a-button>
      <a-button v-if="agreeFlag === ''" type="primary" @click="handleSubmit">确定</a-button>
    </template>
    <a-spin :spinning="confirmLoading">
      <h2>{{ taskInfo.instanceName }}</h2>
      <hr />
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
        <a-col :span="18">
          <p class="from_head">审批记录：</p>
          <div class="messageBoard">
            <taskOperList :opers="opers"></taskOperList>
            <a-textarea v-if="agreeFlag === ''" placeholder="审批说明" v-model="taskInfo.operRmk" />
          </div>
        </a-col>
      </a-row>
    </a-spin>
  </a-modal>
</template>

<script>
import events from '@/utils/events'
import { operTask } from '@/api/admin/account'
import taskOperList from '../../admin/messageBoard/taskOperList'
export default {
  components: { taskOperList },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      visible: false,
      confirmLoading: false,
      taskInfo: {},
      agreeFlag: '',
      modalTitle: '',
      form: this.$form.createForm(this),
      opers: []
    }
  },
  methods: {
    show (record) {
      this.modalTitle = '我的任务'
      this.visible = true
      this.taskInfo = record
      this.agreeFlag = record.agreeFlag
      this.opers = record.opers
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleSubmit () {
      if (this.taskInfo.agreeFlag === null || this.taskInfo.agreeFlag === '') {
        this.$message.error('请选择审批操作')
      } else {
        this.confirmLoading = true
        operTask(this.taskInfo).then(res => {
          this.handleResponse(res)
        })
      }
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.code === 0) {
        this.$message.success(res.message)
        this.taskInfo.opers.push(res.data)
        events.$emit('OnTaskStatusChange', this.taskInfo)
        this.form.resetFields()
        this.visible = false
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.taskInfo.agreeFlag = this.agreeFlag
      this.visible = false
    }
  },
  computed: {
    taskDetailComponent () {
      if (!this.taskInfo.detailUrl) {
        return null
      }
      return () => import(`@/${this.taskInfo.detailUrl}`)
    }
  }
}
</script>
<style lang="less">
.messageBoard {
    width: inherit;
    float: left;
    border: 1px solid rgba(230, 235, 245, 1);
    padding: 15px;

  }
.from_head {
  width: max-content;
  float: left;
  font-size: 14px;
  font-weight: 400;
  color: rgba(86, 102, 141, 1);
  margin: 0;
  i {
    display: block;
    width: 20px;
    height: 20px;
    float: left;
    margin-right: 15px;
    background-repeat: no-repeat;
  }
}
</style>
