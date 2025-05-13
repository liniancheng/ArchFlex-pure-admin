<template>
  <div class="moia-layout-header">
    <logo :primaryColor="settings.primaryColor" />
    <img v-if="codeMap['index.banner']" :src="`/img/${codeMap['index.banner']}`" class="subTitle-image" />
    <div v-else class="subTitle-text">{{ codeMap['index.banner.text'] }}</div>

    <slot name="middleMenu">
      <!-- 头部主菜单 -->
      <a-menu :theme="theme" mode="horizontal" @click="handleClick" :style="{ lineHeight: '64px' }">
        <a-menu-item v-for="(item, index) in menus" :key="index">
          <a-icon type="mail" />{{ item.name }}
        </a-menu-item>
      </a-menu>
    </slot>
    <slot name="rightContent">头像、登出、多语言</slot>
    <slot>
      <a-badge @click="gotoPersonCenter(7)">
        <a-tooltip>
          <template slot="title">帮助文档</template>
          <a-icon type="question-circle" theme="filled" :style="{fontSize: '26px', color: '#fff'}" />
        </a-tooltip>
      </a-badge>
      <a-badge :dot="existNewTask" @click="gotoPersonCenter(4)">
        <a-tooltip>
          <template slot="title">我的任务</template>
          <span><iconTask height="26px" width="26px" fill="#ffffff" /></span>
        </a-tooltip>
      </a-badge>
      <a-badge :dot="existNewMessage" @click="gotoPersonCenter(3)">
        <a-tooltip>
          <template slot="title">消息公告</template>
          <a-icon type="bell" theme="filled" :style="{fontSize: '26px', color: '#fff'}" />
        </a-tooltip>
      </a-badge>
      <a-input v-model="searchKey" placeholder="快捷搜索" @pressEnter="search">
        <a-icon slot="prefix" type="search" @click="search" />
      </a-input>
    </slot>
  </div>
</template>
<script>
import Logo from '@/components/tools/Logo'
import events from '@/utils/events'
import { iconTask } from '@/core/icons'

export default {
  name: 'CustomHeader',
  components: { Logo, iconTask },
  props: {
    settings: { type: Object, required: true },
    title: { type: String, default: '' },
    menus: { type: Array, required: true },
    theme: { type: String, default: 'light' }
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    }
  },
  data () {
    return {
      searchKey: '',
      existNewMessage: false,
      existNewTask: false
    }
  },
  watch: {
    codeMap (newVal, oldVal) {
      this.indexBanner = newVal['index.banner']
    }
  },
  mounted () {
    // 监听新消息/新任务
    const _this = this
    events.$on('OnMessageOrTaskChange', function (data) {
      console.info('OnMessageOrTaskChange', data)
      _this.existNewMessage = data.existNewMessage
      _this.existNewTask = data.existNewTask
    })
  },
  methods: {
    handleClick (e) {
      this.$emit('handleLoadMenu', e.key)
    },
    search (e) {
      this.$router.push({ name: 'menu.search', params: { searchKey: this.searchKey } })
      events.$emit('doSearchEvent', this.searchKey)
    },
    gotoPersonCenter (type) {
      events.$emit('OnPersonTabChange', type)
      this.$store.commit('setRouteParam', type)
      this.$router.push({ name: 'menu.person' })
    }
  }
}
</script>
<style lang="less">
@import '../theme.less';
.moia-layout-header {
  height: 64px;
  background: @primary-color;
  .ant-menu {
    background: @primary-color;
    color: @whiteColor ;
  }
  .ant-menu-item-active,
  .ant-menu-item-selected,
  .ant-menu-submenu-selected {
    color: @whiteColor !important;
    border-bottom: 3px solid @whiteColor !important;
  }
  .subTitle-image {
    float: left;
    margin: 16px 10px 0 20px;
  }
  .subTitle-text {
    width: 108px;
    height: 63px;
    float: left;
    line-height: 60px;
    font-size: 20px;
    font-weight: bold;
    color: @whiteColor;
    text-align: center;
  }
  .ant-menu {
    float: left;
    width: calc(~'100% - 780px');
  }
  .ant-menu-horizontal > .ant-menu-item{
    top: 0px;
    padding-top: 18px;
    height: 63px;
    .anticon{
      display: block;
      margin: 0;
      position: absolute;
      left: 40%;
      top: 20px;
      font-size: 18px;
    }
  }
  .ant-input-affix-wrapper {
    width: 150px;
  }
  > span {
    float: right;
    margin: 19px 10px;
    .ant-input {
      height: 30px;
      line-height: 30px;
      border-radius: 50px;
    }
  }
}
</style>
