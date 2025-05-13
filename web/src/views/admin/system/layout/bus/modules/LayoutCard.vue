<template>
  <!-- 展示面板 -->
  <vue-scroll :ops="{scrollPanel:{maxHeight: '100%'}}" >
    <grid-layout
      style="margin:-10px;"
      :layout.sync="listItems"
      :col-num="24"
      :row-height="5"
      :is-draggable="false"
      :is-resizable="false"
      :is-mirrored="false"
      :vertical-compact="true"
      :margin="[10, 10]"
      :use-css-transforms="true"
    >
      <grid-item
        v-for="item in listItems"
        :x="item.x"
        :y="item.y"
        :w="item.w"
        :h="item.h"
        :i="item.i"
        :key="item.i">
        <async-component :path="item.attach.itemComp" />
      </grid-item>
    </grid-layout>
  </vue-scroll>
</template>
<script>
import VueGridLayout from 'vue-grid-layout'
import AsyncComponent from './AsyncComponent'
export default {
  name: 'LayoutCard',
  components: {
    AsyncComponent,
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem
  },
  props: {
    listItems: { type: Array, required: true }
  }
}
</script>
