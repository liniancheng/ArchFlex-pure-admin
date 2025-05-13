<template>
  <a-drawer
    title="预览布局"
    placement="left"
    width="100%"
    :closable="true"
    :visible="visible"
    :get-container="false"
    :wrap-style="{ position: 'absolute' }"
    class="s_table_class"
    @close="onClose"
  >
    <layout-card ref="layoutCard" :listItems="listItems" />
  </a-drawer>
</template>
<script>
import { preview } from '@/api/admin/layout/layout/index'
import AsyncComponent from './AsyncComponent'
import LayoutCard from './LayoutCard'

export default {
  name: 'LayoutPreviewDrawer',
  components: {
    AsyncComponent,
    LayoutCard
  },
  data () {
    return {
      listItems: [],
      visible: false,
      agencyTaskList: [],
      messageContentList: {}
    }
  },
  mounted () {
  },
  methods: {
    show (id) {
      this.$nextTick(() => {
        this.loadItems(id)
      })
    },
    loadItems (id) {
      preview(id).then(res => {
        if (res.code === 0) {
          if (res.data.listItems) {
            this.listItems = res.data.listItems.map(item => {
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
          }
        }
      }).finally(() => {
        this.showDrawer()
      })
    },
    showDrawer () {
      this.visible = true
    },
    onClose () {
      this.visible = false
    }
  }
}
</script>
