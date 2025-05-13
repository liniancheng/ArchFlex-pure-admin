<template>
  <div :class="prefixCls" :style="{ transition: '0.3s all' }">
    <div style="float: left;">
      <slot name="extra">{{ extra }}</slot>
    </div>
    <div class="footer-right">
      <a>{{ currentTime }} </a>
      <a> 版本：{{ version }}</a>
      <a><a-icon type="global"/> 测试环境</a>
      <a><a-icon type="user"/> ADTEC</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooterToolBar',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-footer-toolbar'
    },
    collapsed: {
      type: Boolean,
      default: false
    },
    isMobile: {
      type: Boolean,
      default: false
    },
    siderWidth: {
      type: Number,
      default: undefined
    },
    extra: {
      type: [String, Object],
      default: ''
    }
  },
  data () {
    return {
      version: 'V-3.1',
      currentTime: this.formatDate(new Date())
    }
  },
  mounted () {
    const _this = this
    this.timer = setInterval(() => {
      _this.currentTime = _this.formatDate(new Date())
    }, 1000)
  },
  beforeDestroy () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>

<style lang="less" scoped>
@import '../theme.less';
  .ant-pro-footer-toolbar{
    position: absolute;
    height: auto;
    line-height: 24px;
    background: @primary-color;
    // color: @whiteColor;
    padding: 0;
  }

  .footer-right{
    float: right;
    a{
      color: @whiteColor;
      display: inline-block;
      padding: 0 5px;
      font-size: 12px;
    }
    a:hover{
      background: @primary-4;
    }
  }
  .imoia-sider-button:hover{
    background: @primary-4;
  }
</style>
