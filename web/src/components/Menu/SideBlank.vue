<template>
  <a-layout-sider
    :class="['sider', isDesktop() ? null : 'shadow', theme, fixSiderbar ? 'ant-fixed-sidemenu' : null ]"
    :collapsible="collapsible"
    v-model="collapsed"
    :trigger="null"
    :width="width">
    <vue-scroll>
      <a-tree :tree-data="myTreeData"/>
      <a-spin />
    </vue-scroll>
  </a-layout-sider>
</template>
<script>
import { mixin, mixinDevice } from '@/utils/mixin'
import MultiTab from '@/components/MultiTab/index'
export default {
  name: 'SideTree',
  components: {
    MultiTab
  },
  mixins: [mixin, mixinDevice],
  props: {
    theme: {
      type: String,
      required: false,
      default: 'dark'
    },
    collapsible: {
      type: Boolean,
      required: false,
      default: false
    },
    collapsed: {
      type: Boolean,
      required: false,
      default: false
    },
    width: {
      type: Number,
      required: false,
      default: 200
    }
  },
  data () {
    return {
      myTreeData: []
    }
  }
}
</script>
