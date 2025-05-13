<template>
  <div>
    <layout-design-card :systemFlag="false" ref="layoutDesignCard" :layId="id" @handleShow="handleShow" @handlePreview="handlePreview"/>
    <layout-item-drawer :systemFlag="false" ref="layoutItemDrawer" @handleAdd="addItem"/>
    <layout-preview-drawer ref="layoutPreviewDrawer" />
  </div>
</template>
<script>
import LayoutItemDrawer from './modules/LayoutItemDrawer'
import LayoutDesignCard from './modules/LayoutDesignCard'
import LayoutPreviewDrawer from './modules/LayoutPreviewDrawer'
export default {
  components: {
    LayoutItemDrawer,
    LayoutDesignCard,
    LayoutPreviewDrawer
  },
  data () {
    return {
      visible: true,
      id: ''
    }
  },
  mounted () {
    const layId = this.$route.params.id
    this.id = layId
    this.$refs.layoutDesignCard.handleLoad(layId)
  },
  methods: {
    handleShow () {
      this.$refs.layoutItemDrawer.showDrawer()
    },
    addItem (data) {
      this.$refs.layoutDesignCard.addItem(data)
    },
    handlePreview () {
      this.$refs.layoutPreviewDrawer.show(this.id)
    }
  }
}
</script>
