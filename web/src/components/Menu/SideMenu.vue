<template>
  <a-layout-sider
    :class="['sider', isDesktop() ? null : 'shadow', theme, fixSiderbar ? 'ant-fixed-sidemenu' : null ]"
    :collapsible="collapsible"
    v-model="collapsed"
    :trigger="null"
    :width="width">
    <vue-scroll>
      <s-menu
        :collapsed="collapsed"
        :menu="menus"
        :theme="theme"
        :mode="mode"
        @select="onSelect"
        style="padding: 16px 0px;"></s-menu>
    </vue-scroll>
  </a-layout-sider>

</template>

<script>
import SMenu from './index'
import { mixin, mixinDevice } from '@/utils/mixin'

export default {
  name: 'SideMenu',
  components: { SMenu },
  mixins: [mixin, mixinDevice],
  props: {
    mode: {
      type: String,
      required: false,
      default: 'inline'
    },
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
    menus: {
      type: Array,
      required: true
    },
    width: {
      type: Number,
      required: false,
      default: 200
    }
  },
  methods: {
    onSelect (obj) {
      this.$emit('menuSelect', obj)
    }
  }
}
</script>
