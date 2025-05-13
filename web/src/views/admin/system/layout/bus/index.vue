<template>
  <div>
    <keep-alive>
      <layout-design-card ref="layoutDesignCard" :systemFlag="true" :layId="id" @handleShow="handleShow" @handlePreview="handlePreview"/>
    </keep-alive>
    <layout-item-drawer ref="layoutItemDrawer" :systemFlag="true" @handleAdd="addItem"/>
    <layout-preview-drawer ref="layoutPreviewDrawer" />
  </div>
</template>
<script>
import VueGridLayout from 'vue-grid-layout'
import LayoutItemDrawer from './modules/LayoutItemDrawer'
import LayoutDesignCard from './modules/LayoutDesignCard'
import LayoutPreviewDrawer from './modules/LayoutPreviewDrawer'
export default {
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem,
    LayoutItemDrawer,
    LayoutDesignCard,
    LayoutPreviewDrawer
  },
  data () {
    return {
      visible: true,
      id: this.$route.params.id
    }
  },
  mounted () {
    this.$refs.layoutDesignCard.handleLoad(this.id)
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
