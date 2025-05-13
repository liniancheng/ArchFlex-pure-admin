<template>
  <a-dropdown v-if="currentUser && currentUser.name" placement="bottomRight">
    <span class="ant-pro-account-avatar">
      <img size="small" src="@/assets/logoimg.png" class="antd-pro-global-header-index-avatar" />
      <span class="currentUser">{{ currentUser.name }}</span>
    </span>
    <template v-slot:overlay>
      <a-menu class="ant-pro-drop-down menu" :selected-keys="[]">
        <a-menu-item v-if="menu" key="center" @click="handleToCenter">
          <a-icon type="user" />
          个人中心
        </a-menu-item>
        <a-menu-item v-if="menu" key="security" @click="modifyPassword">
          <a-icon type="setting" />
          修改密码
        </a-menu-item>
        <a-menu-divider v-if="menu" />
        <a-menu-item key="theme">
          <a-icon type="skin" />
          <a-radio-group :default-value="defaultTheme" :size="size" button-style="solid" class="themeColor" @change="getThemeName">
            <a-radio-button value="green"> </a-radio-button>
            <a-radio-button value="yellow"> </a-radio-button>
            <a-radio-button value="blue"> </a-radio-button>
            <a-radio-button value="purple"> </a-radio-button>
          </a-radio-group>
        </a-menu-item>
        <a-menu-divider v-if="menu" />
        <a-menu-item key="logout" @click="handleLogout">
          <a-icon type="logout" />
          退出登录
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
  <!-- <span v-else>
    <a-spin size="small" :style="{ marginLeft: 8, marginRight: 8 }" />
  </span> -->
</template>

<script>
import { Modal } from 'ant-design-vue'
import { mapActions } from 'vuex'
import events from '@/utils/events'
import { updateTheme } from '@/components/SettingDrawer/settingConfig'

export default {
  name: 'AvatarDropdown',
  props: {
    currentUser: {
      type: Object,
      default: () => null
    },
    menu: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      size: 'small',
      defaultTheme: localStorage.getItem('theme')
    }
  },
  methods: {
    ...mapActions(['Logout']),
    handleToCenter () {
      events.$emit('OnPersonTabChange', 0)
      this.$store.commit('setRouteParam', 0)
      this.$router.push({ name: 'menu.person' })
    },
    modifyPassword () {
      this.$emit('modifyPassword')
    },
    handleLogout (e) {
      Modal.confirm({
        title: '提醒',
        content: '确定退出？',
        onOk: () => {
          this.Logout().then(res => {
            window.location.href = '/user/login'
          })
        },
        onCancel () {}
      })
    },
    // 切换主题
    getThemeName (e) {
      var theme = e.target.value
      this.$root.theme = theme
      localStorage.setItem('theme', theme)
      var color = ''
      if (theme === 'green') {
        color = '#35af54'
      } else if (theme === 'blue') {
        color = '#0097f4'
      } else if (theme === 'yellow') {
        color = '#ef9e1d'
      } else if (theme === 'purple') {
        color = '#a47af4'
      }
      updateTheme(color)
    }
  }
}
</script>

<style lang="less" scoped>
.ant-pro-drop-down {
  /deep/ .action {
    margin-right: 8px;
  }
  /deep/ .ant-dropdown-menu-item {
    min-width: 160px;
  }
}
.ant-pro-account-avatar {
  .currentUser {
    color: #ffffff;
  }
}
.ant-pro-global-header-index-right {
  .antd-pro-global-header-index-avatar {
    width: 24px;
    height: 24px;
    margin-right: 8px;
    border-radius: 50px;
  }
}
.themeColor {
  .ant-radio-button-wrapper {
    height: 20px;
    width: 20px;
    line-height: 18px;
    margin-right: 2px;
    border-radius: 0;
  }
  .ant-radio-button-wrapper:nth-child(1) {
    background-color: #35af54 !important;
  }
  .ant-radio-button-wrapper:nth-child(2) {
    background-color: #ef9e1d !important;
  }
  .ant-radio-button-wrapper:nth-child(3) {
    background-color: #0097f4 !important;
  }
  .ant-radio-button-wrapper:nth-child(4) {
    background-color: #a47af4 !important;
  }
  .ant-radio-button-wrapper-checked:not(.ant-radio-button-wrapper-disabled) {
    border-color: #000000;
  }
}
</style>
