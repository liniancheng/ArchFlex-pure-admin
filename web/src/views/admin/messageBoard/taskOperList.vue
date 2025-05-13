<template>
  <vue-scroll>
    <ul class="ant_list">
      <li v-for="item in opers" :key="item.instanceId">
        <a>{{ item.userName }}</a>
        <div class="listItemMmeta">
          <p>{{ item.agreeFlag === '1' ? '通过' : '退回' }} {{ formatDate(item.operTime) }}</p>
          {{ item.operRmk }}
        </div>
      </li>
    </ul>
    <!--
      <a-list-item slot="renderItem" slot-scope="item">
        <a-card :title="item.userName">
          <p>{{ item.agreeFlag === '1' ? '通过' : '退回' }} {{ formatDate(item.operTime) }}</p>
          {{ item.operRmk }}
        </a-card>
      </a-list-item>
-->
  </vue-scroll>
</template>
<script>
export default {
  name: 'TaskOperList',
  props: {
    opers: {
      type: Array,
      default: null
    }
  },
  data () {
    return {}
  },
  mounted () {},
  methods: {
    formatDate (date) {
      return date.substring(0, 10)
    }
  }
}
</script>
<style lang="less">
.ant_list {
  max-height: 286px;
  margin: 0;
  padding: 0;
  .listItemMmeta {
    margin: 10px 0;
    padding: 10px;
    color: #333;
    font-size: 12px;
    line-height: 22px;
    background: rgba(240, 240, 240, 1);
    border-radius: 4px;
  }
}
</style>
