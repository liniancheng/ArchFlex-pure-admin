<template>
  <div class="person">
    <!-- 左侧导航 -->
    <div class="imoia-person-left" v-if="type !== 5 && type !== 6">
      <a-tabs default-active-key="0" v-model="type" @change="leftTabChange" tab-position="left" >
        <a-tab-pane :key="0" tab="信息总览"></a-tab-pane>
        <a-tab-pane :key="2" tab="我的信息"></a-tab-pane>
        <a-tab-pane :key="1" tab="修改密码"></a-tab-pane>
        <a-tab-pane :key="3" tab="消息公告"></a-tab-pane>
        <a-tab-pane :key="4" tab="我的任务"></a-tab-pane>
        <a-tab-pane :key="7" tab="帮助文档"></a-tab-pane>
      </a-tabs>
    </div>
    <div class="imoia-person-right">
      <person-info :accountInfo="accountInfo" @leftTabChange="leftTabChange" v-if="type === 0" />
      <person-pwd v-else-if="type === 1" />
      <person-modify :accountInfo="accountInfo" v-else-if="type === 2" />
      <person-message ref="personMessage" @loadMessage="loadMessage" v-if="type === 3" />
      <person-task ref="personTask" @loadTask="loadTask" v-if="type === 4" />
      <person-message-detail @showMessage="showMessage" :messageInfo="messageInfo" v-if="type === 5" />
      <person-task-detail @showTask="showTask" :taskInfo="taskInfo" v-if="type === 6" />
      <help-file ref="helpFile" v-if="type === 7" />
    </div>
  </div>
</template>
<script>
import PersonInfo from './modules/PersonInfo'
import PersonPwd from './modules/PersonPwd'
import PersonModify from './modules/PersonModify'
import PersonMessage from './modules/PersonMessage'
import PersonMessageDetail from './modules/PersonMessageDetail'
import PersonTask from './modules/PersonTask'
import PersonTaskDetail from './modules/PersonTaskDetail'
import HelpFile from './modules/HelpFile'

import events from '@/utils/events'

import { getAccountInfo } from '@/api/admin/account'

export default {
  name: 'Person',
  components: {
    PersonInfo,
    PersonPwd,
    PersonModify,
    PersonMessage,
    PersonMessageDetail,
    PersonTask,
    PersonTaskDetail,
    HelpFile
  },
  data () {
    return {
      // 显示页面类型
      type: 0,
      accountInfo: {},
      messageInfo: {}, // 当前查看的消息
      taskType: '-1',
      taskInfo: {}
    }
  },

  mounted () {
    this.leftTabChange(this.$store.getters.getRouteParam || 0)
    // 监听tab变化
    const self = this
    events.$on('OnPersonTabChange', function (data) {
      self.leftTabChange(data)
    })
  },
  methods: {
    loadMessage (data) {
      this.messageInfo = data
      this.$nextTick(() => {
        this.type = 5
      })
    },
    showMessage (type) {
      this.type = 3
      this.$nextTick(() => {
        this.$refs.personMessage.load(type)
      })
    },
    loadTask (data, type) {
      this.taskInfo = data
      this.taskType = type
      this.$nextTick(() => {
        this.type = 6
      })
    },
    showTask (type) {
      this.type = 4
      this.$nextTick(() => {
        this.$refs.personTask.load(type || this.taskType)
      })
    },
    // 切换导航页签
    leftTabChange (key) {
      if (key === 0 || key === 1) {
        this.type = key
        this.$nextTick(() => {
          this.getAccountInfo()
        })
      } else if (key === 2) {
        this.type = key
      } else if (key === 3) {
        this.showMessage('all')
      } else if (key === 4) {
        this.showTask('-1')
      } else if (key === 7) {
        this.type = key
        this.$nextTick(() => {
          this.$refs.helpFile.load()
        })
      }
    },
    // 获取信息
    getAccountInfo () {
      getAccountInfo().then(res => {
        this.accountInfo = res.data
      })
    }
  }
}
</script>

<style lang="less">
@import '../../../../components/theme.less';
.person {
  width: 100%;
  min-height: 100%;
  background: #fff;
  padding: 20px 0px 20px 0;
  display: flex;
  .person_top,.person_bottom{
    .anticon svg {
      fill: @primary-color;
    }
    .from_p {
      color: @primary-color;
    }
  }
  .ant-tabs-nav .ant-tabs-tab-active {
    background-color: #e1f0e3;
  }
  .imoia-person-left{
    width: 224px;
  }
  .imoia-person-right{
    flex: 1 1;
    padding: 8px 0;
  }
  .from_head {
    color: @blackColor;
    text-align: right;
    float: left;
    font-size: 16px;
    font-weight: 400;
    margin: 0;
    i {
      font-size: 22px;
      display: block;
      width: 20px;
      height: 20px;
      float: left;
      margin-right: 15px;
      background-repeat: no-repeat;
    }
  }
  .from_span {
    font-size: 16px;
    font-weight: bold;
  }
  .person_title {
    margin-bottom: 40px;
    margin-top: 15px;
    float: left;
    width: 100%;
    img {
      width: 4px;
      height: 21px;
      float: left;
      margin-right: 15px;
    }
    h2 {
      height: 20px;
      font-size: 20px;
      font-weight: bold;
      line-height: 17px;
      float: left;
    }
  }
  .processing{
    color: #e1e1e1;
  }
  .read.ant-btn {
    width: 87px;
    height: 27px;
    padding: 0px;
    margin-right: 20px;
    margin-top: 20px;
    span {
      font-size: 12px;
      font-family: SourceHanSansCN;
      font-weight: 400;
    }
  }
  .delete.ant-btn {
    width: 72px;
    height: 27px;
    padding: 0px;
    margin-top: -20px;
  }
  .ant-tabs {
    width: 182px;
    height: 100%;
    float: left;
    .ant-tabs-left-bar {
      width: 100%;
      .ant-tabs-tab {
        text-align: left;
      }
    }
    .ant-tabs-left-bar .ant-tabs-ink-bar {
      width: 4px;
    }
  }
  .person_bottom {
    .from_left {
      width: 80px;
      float: left;
      text-align: center;
      img {
        width: 40px;
        height: 40px;
      }
      .anticon {
        font-size: 42px;
      }
    }
    .poweroff.from_left{
      .anticon {
        svg{
          fill: #f77f5b;
        }
      }
    }
    .from_right {
      float: left;
      .from_p {
        margin: 0;
        margin-bottom: 0px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
      }
      .from_span {
        font-size: 14px;
        font-weight: 400;
      }
    }
    .from_right_margin {
      padding-bottom: 75px;
    }
  }

  .form_btn {
    text-align: center;
    .ant-btn {
      width: 100px;
      height: 30px;
      border-radius: 14px;
      text-align: center;
      line-height: 30px;
    }
    .ant-btn:last-child {
      margin-left: 30px;
    }
  }

  .message {
    .ant-checkbox-wrapper {
      float: left;
      margin-left: 45px;
      font-size: 12px;
    }
    .ant-input-group-wrapper {
      width: 380px;
      margin-top: -8px;
      margin-right: 30px;
      float: right;
    }
    .ant-input-group .ant-input {
      height: 35px;
      font-size: 14px;
    }
    .ant-btn-lg {
      height: 35px;
      font-size: 14px;
    }
  }
  .task {
    .ant-table {
      margin-top: 120px;
    }
    .pending {
      color: #f77f5b;
    }
    .true,
    .false {
      font-size: 14px;
    }
    .true {
      color: #f77f5b;
    }
    .false {
      color: rgba(0, 0, 0, 0.65);
    }
  }
  .ant-table {
    margin-top: 150px;
  }
  .messageDetails {
    // padding: 10px 70px;
    h2 {
      font-size: 18px;
      font-family: SourceHanSansCN;
      font-weight: bold;
      text-align: center;
    }
    hr {
      // border: none;
      height: 2px;
      margin: 25px 0 40px 0;
    }
    p {
      font-size: 14px;
      font-family: SourceHanSansCN;
      font-weight: 400;
      margin: 0;
    }
    .text_right {
      text-align: right;
      font-size: 14px;
      font-family: SourceHanSansCN;
      font-weight: 400;
    }
    .enclosure {
      span {
        font-size: 12px;
        display: inline-block;
        white-space: nowrap;
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .ant-col_ {
        margin-right: 16px;
        padding: 10px;
        margin-bottom: 10px;
      }
      .col_span {
        text-align: center;
      }
    }
  }
  .taskDetails {
    padding-left: 30px;
    .person_title {
      margin-bottom: 20px;
    }
  }
  .messageBoard {
    width: inherit;
    float: left;
    padding: 15px;
  }
}
</style>
