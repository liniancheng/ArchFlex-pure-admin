<template>
  <div :class="wrpCls">
    <avatar-dropdown @modifyPassword="modifyPassword" :menu="showMenu" :current-user="currentUser" :class="prefixCls" />
    <!-- 多语言，暂不使用，注释掉 -->
    <!-- <select-lang :class="prefixCls" /> -->
  </div>
</template>

<script>
import AvatarDropdown from './AvatarDropdown'
import SelectLang from '@/components/SelectLang'

export default {
  name: 'RightContent',
  components: {
    AvatarDropdown,
    SelectLang
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      showMenu: true,
      currentUser: {}
    }
  },
  computed: {
    wrpCls () {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    }
  },
  mounted () {
    this.currentUser = {
      name: this.$store.getters.userInfo.userName
    }
  },
  methods: {
    modifyPassword () {
      this.$emit('modifyPassword')
    }
  }
}
</script>
<style lang="less">
.ant-pro-global-header-index-right{
  margin-right:5px;
}
</style>
