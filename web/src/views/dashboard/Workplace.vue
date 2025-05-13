<template>
  <layout-card v-if="visible" ref="layoutCard" :listItems="listItems" />
</template>
<script>

// import { getHomePage } from '@/api/admin/home'
import LayoutCard from '@/views/admin/system/layout/bus/modules/LayoutCard'
import events from '@/utils/events'
import { mapActions } from 'vuex'

export default {
  name: 'Workplace',
  components: {
    LayoutCard
  },
  data () {
    return {
      visible: false,
      listItems: []
    }
  },
  mounted () {
    this.getHomePage()
  },
  methods: {
    ...mapActions(['GetHomePage']),
    // 获取首页信息
    getHomePage () {
      const { GetHomePage } = this
      GetHomePage().then(res => {
        if (res.code === 0) {
          events.$emit('OnMessageOrTaskChange', {
            existNewMessage: res.data.existUnReadMessage,
            existNewTask: res.data.existUnProcessTask
          })
          this.listItems = res.data.layout.listItems.map(item => {
            return {
                i: item.itemId,
                x: item.relX,
                y: item.relY,
                w: item.relW,
                h: item.relH,
                layId: item.layId,
                itemId: item.itemId,
                itemName: item.itemName,
                attach: item
              }
          })
          this.$nextTick(() => {
            this.visible = true
          })
        }
      }).finally(() => {
      })
    }

  }
}
</script>

<style lang="less">
@import './Workplace.less';

</style>
