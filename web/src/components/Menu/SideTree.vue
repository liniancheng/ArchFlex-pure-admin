<template>
  <a-layout-sider
    :class="['sider', isDesktop() ? null : 'shadow', theme, fixSiderbar ? 'ant-fixed-sidemenu' : null ]"
    :treeUrl="treeUrl"
    :collapsible="collapsible"
    v-model="collapsed"
    :trigger="null"
    :width="width">
    <vue-scroll>
      <a-input-search v-if="myTreeSearch" placeholder="" @search="onSearch" style="padding:1px;"/>
      <a-tree :load-data="onLoadData" :tree-data="myTreeData" @select="selectChange"/>
    </vue-scroll>
  </a-layout-sider>
</template>
<script>
import { mixin, mixinDevice } from '@/utils/mixin'
import { tree } from '../_util/pub'
import MultiTab from '@/components/MultiTab/index'
// import Router from 'vue-router'
export default {
  name: 'SideTree',
  components: {
    MultiTab
  },
  mixins: [mixin, mixinDevice],
  props: {
    treeUrl: {
      type: String,
      required: true,
      default: ''
    },
    treeData: {
      type: Array,
      required: true,
      default: null
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
    width: {
      type: Number,
      required: false,
      default: 200
    }
  },
  data () {
    return {
      myTreeData: this.treeData,
      myTreeSearchBeforeData: null, // 存储非搜索状态下的数据
      mySearchFlag: false, // 是否搜索
      myTreeSearch: this.treeUrl.indexOf('search=1') > -1 // url 中包含 search=1 时显示搜索框
    }
  },
  watch: {
    myTreeData (val) {
      if (!this.mySearchFlag) {
        this.myTreeSearchBeforeData = this.myTreeData
      }
    }
  },
  methods: {
    selectChange (selectedKeys, info) {
      if (info.node.dataRef.value.indexOf('.LOADMORE') > -1) { // 加载更多节点
        tree(this.treeUrl, info.node.dataRef.value, '').then((res) => {
          info.node.$parent.dataRef.children.splice(info.node.$parent.dataRef.children.length - 1, 1)
          info.node.$parent.dataRef.children = info.node.$parent.dataRef.children.concat(res.data)
        })
      } else { // 正常节点
        const url = info.node.dataRef.url + info.node.dataRef.value + '?customTitle=' + info.node.dataRef.title
        this.$router.push({ path: url })
      }
    },
    onLoadData (treeNode) {
      return new Promise(resolve => {
        if (treeNode.dataRef.children) {
          // 已加载
          resolve()
        } else {
          // 未加载
          tree(this.treeUrl, treeNode.value).then((res) => {
            treeNode.dataRef.children = res.data
            this.myTreeData = [...this.myTreeData]
            resolve()
          })
        }
      })
    },
    onSearch (value) {
      if (value === '') {
        this.myTreeData = this.myTreeSearchBeforeData
        this.mySearchFlag = false
      } else {
        this.mySearchFlag = true
        tree(this.treeUrl, '-1', value).then(res => {
          if (res.data === '') {
            this.myTreeData = []
          } else {
            this.myTreeData = res.data
          }
        })
      }
    }
  }
}
</script>
