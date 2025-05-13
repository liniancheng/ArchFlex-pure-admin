<template>
  <!-- <div>布局设计器</div> -->
  <a-card size="small" :bodyStyle="bodyStyle">
    <a-button-group slot="extra" size="small">
      <a-button v-if="!systemFlag" size="small" icon="reload" @click="handleDefault">默认布局</a-button>
      <a-button size="small" icon="plus" @click="handleShowItem">数据项</a-button>
      <a-button size="small" icon="eye" @click="handlePreview">预览</a-button>
      <a-button size="small" icon="save" type="primary" @click="handleSubmit">保存</a-button>
    </a-button-group>
    <grid-layout
      :layout.sync="layout"
      :col-num="24"
      :row-height="5"
      :is-draggable="true"
      :is-resizable="true"
      :is-mirrored="false"
      :vertical-compact="true"
      :margin="[10, 10]"
      :use-css-transforms="true"
    >
      <grid-item
        v-for="item in layout"
        :x="item.x"
        :y="item.y"
        :w="item.w"
        :minW="item.minW"
        :h="item.h"
        :minH="item.minH"
        :i="item.i"
        :key="item.i">
        <a-icon class="imoia-grid-item-icon" type="close-circle" @click="removeItem(item.i)"/>
        <div class="imoia-grid-item-text">  {{ item.itemName }} </div>
      </grid-item>
    </grid-layout>

  </a-card>
</template>
<script>
import { listRels, saveLists } from '@/api/admin/layout/layoutItemRel/index'
import { removeById } from '@/api/admin/layout/layout/index'
import VueGridLayout from 'vue-grid-layout'
import { Modal } from 'ant-design-vue'

export default {
  name: 'LayoutDesignCard',
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem,
    Modal
  },
  props: {
    systemFlag: { type: Boolean, required: true }
  },
  data () {
    return {
      layId: '',
      bodyStyle: { minHeight: '500px' },
      layout: []
    }
  },
  methods: {
    handleLoad (id) {
      this.layId = id
      this.loadData()
    },
    handleDefault () {
      const _this = this
      Modal.confirm({
        title: '确定恢复默认布局？',
        content: '恢复默认布局会删除自定义布局。如需使用自定义布局，需要重新编辑！',
        onOk () {
          removeById(_this.layId).then(res => {
            if (res.code === 0) {
              _this.$message.success(res.message)
              setTimeout(() => {
                _this.$multiTab.close('/admin/custom/layoutDesign/' + _this.layId)
              }, 100)
            }
          })
        }
      })
    },
    loadData () {
      listRels(this.layId).then(res => {
        if (res.code === 0) {
          if (res.data && res.data.length > 0) {
            this.layout = res.data.map(item => {
              return {
                i: item.itemId,
                x: item.relX,
                y: item.relY,
                w: item.relW,
                h: item.relH,
                minW: item.minW,
                minH: item.minH,
                layId: item.layId,
                itemId: item.itemId,
                itemName: item.itemName
              }
            })
          }
        } else {

        }
      })
    },
    handleShowItem () {
      this.$emit('handleShow')
    },
    handlePreview () {
      this.$emit('handlePreview')
    },
    addItem (data) {
      if (this.layout.length > 0) {
        const length = this.layout.filter(item => item.i === data.itemId).length
        if (length > 0) {
          this.$message.success('success')
          return
        }
      }
      const item = {
        x: 0,
        y: 0,
        minW: data.minWidth,
        w: data.minWidth,
        minH: data.minHeight,
        h: data.minHeight,
        i: data.itemId,
        layId: this.layId,
        itemId: data.itemId,
        itemName: data.itemName
        }
      this.layout = [...this.layout, item]
      this.$message.success('success')
    },
    removeItem (id) {
      this.layout = this.layout.filter(item => item.i !== id).map(item => item)
    },
    handleSubmit () {
      const list = this.layout.map(item => {
        return {
          layId: this.layId,
          itemId: item.itemId,
          itemName: item.itemName,
          relX: item.x,
          relY: item.y,
          relW: item.w,
          relH: item.h }
      })
      saveLists(list, this.layId).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
        } else {
          this.$message.fail(res.message)
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.vue-grid-item {
  background: #c7cdd1;
}
.imoia-grid-item-icon{
  float: right;
  padding: 5px;
  color: #ffffff;
}
.imoia-grid-item-text{
  position: absolute;
  width: 100%;
  top: 50%;
  text-align: center;
  font-size: 16px;
  color: #333333;
}
</style>
