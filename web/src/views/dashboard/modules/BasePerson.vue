<template>
  <a-card class="content_zh_item a_card" :bodyStyle="bodyStyle" :loading="loading">
    <div slot="title">
      <iconUser height="20px" width="20px" />个人中心
    </div>
    <a-row class="text-ellipsis">
      <a-col v-if="clientWidth > 900" :span="5">
        <span class="content_max_title_0097F4">{{ accountInfo.loginName }}</span>
        <a-tooltip
          placement="topLeft"
          :title="`${accountInfo.loginName}用户,欢迎您使用${codeMap['system.login.logo.text']}`"
        >用户,欢迎您使用{{ codeMap['system.login.logo.text'] }}</a-tooltip >
      </a-col>
      <a-col :span="clientWidth > 900 ? 5 : 6">
        <span class="content_title_999">员工名称：</span>
        <a-tooltip placement="topLeft" :title="accountInfo.userName">{{ accountInfo.userName }}</a-tooltip>
      </a-col>
      <a-col :span="clientWidth > 900 ? 5 : 6">
        <span class="content_title_999">平台角色：</span>
        <a-tooltip placement="topLeft" :title="accountInfo.roleNames">{{ accountInfo.roleNames }}</a-tooltip>
      </a-col>
      <a-col :span="clientWidth > 900 ? 5 : 6">
        <span class="content_title_999">所属机构：</span>
        <a-tooltip placement="topLeft" :title="accountInfo.branchName">{{ accountInfo.branchName }}</a-tooltip>
      </a-col>
      <a-col :span="clientWidth > 900 ? 4 : 6">
        <span class="content_title_999">上次登录时间：</span>
        <a-tooltip placement="topLeft" :title="accountInfo.lastLoginTime">{{ accountInfo.lastLoginTime }}</a-tooltip>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import events from '@/utils/events'
import { iconUser } from '@/core/icons'
export default {
  name: 'BasePerson',
  components: {
    iconUser
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    }
  },
  data () {
    return {
      size: 'small',
      bodyStyle: { overflow: 'hidden', padding: '0 15px' },
      loading: true,
      clientWidth: document.body.clientWidth,
      accountInfo: {}
    }
  },
  mounted () {
    window.addEventListener('resize', () => {
      this.clientWidth = document.body.clientWidth
    })
    this.loadUserInfo()
    const self = this
    // 监听与更新账号信息
    events.$on('OnAccountInfoChange', function (data) {
      self.accountInfo = data
    })
  },
  methods: {
    loadUserInfo () {
      this.accountInfo = this.$store.getters.userInfo
      this.loading = false
    }
  }
}
</script>
<style lang="less">
.content_zh {
  .content_max_title_0097F4 {
    font-size: 14px;
    font-weight: bold;
    color: rgba(8, 154, 226, 1);
  }
  .content_title_999 {
    color: #999;
  }
}
.text-ellipsis {
  .ant-col {
    padding-top: 5px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>
