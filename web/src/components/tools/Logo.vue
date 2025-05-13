<template>
  <div class="imoia-left-logo">
    <router-link :to="{ path: '/dashboard/workplace' }">
      <img src="../../assets/datalog.svg" width="50px"/>
      <span class="logoText">跨系统指标校验</span>
    </router-link>
    <!-- 基础平台原有logo大小 -->
    <!-- <img :src="`/logo/${codeMap['index.logo.image']}?inline`" /> -->
  </div>
</template>

<script>
export default {
  name: 'Logo',
  computed: {
    codeMap () {
      return this.$store.getters.config
    }
  }
}
</script>
<style lang="less" scoped>
@import '../theme.less';
.imoia-left-logo {
  //  @primary-7
  background-color:#1890ff;
  float: left;
  width: 200px;
  height: 64px;
  text-align: center;
}
.logoText{
  font-family: "微软雅黑";
  font-size: 20px;
  color: #FFFFFF;
}
</style>
