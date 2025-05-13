<template>
  <!-- <div>布局数据项抽屉</div> -->
  <a-drawer
    title="可选数据项"
    placement="left"
    :width="400"
    :closable="true"
    :visible="visible"
    :get-container="false"
    :wrap-style="{ position: 'absolute' }"
    class="s_table_class"
    @close="onClose"
  >
    <s-table
      ref="table"
      size="default"
      rowKey="itemId"
      :columns="columns"
      :data="loadData"
      :showSizeChanger="false">
      <a-button icon="plus" type="link" slot="action" slot-scope="text, record" @click="addItem(record)"/>
    </s-table>
  </a-drawer>
</template>
<script>
import { STable, Ellipsis } from '@/components'
import { fetch, fetchPerson } from '@/api/admin/layout/layoutItem/index'
export default {
  name: 'LayoutItemCard',
    components: {
    STable,
    Ellipsis
  },
  props: {
    // 管理端标识
    systemFlag: { required: true, type: Boolean }
  },
  data () {
    return {
      visible: false,
      // 表头
      columns: [
        { key: 'itemName', title: '数据项名称', dataIndex: 'itemName', ellipsis: true },
        { title: '操作', dataIndex: 'action', width: 80, align: 'center', scopedSlots: { customRender: 'action' } }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        if (this.systemFlag) {
          return fetch(Object.assign(parameter, {})).then(res => {
            if (res.code === 0) {
              return res.data
            } else {
              return {}
            }
          })
        } else {
          return fetchPerson(Object.assign(parameter, {})).then(res => {
            if (res.code === 0) {
              return res.data
            } else {
              return {}
            }
          })
        }
      }
    }
  },
  methods: {
    showDrawer () {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.table.refresh()
      })
    },
    onClose () {
      this.visible = false
    },

    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    addItem (data) {
      this.$emit('handleAdd', data)
    }
  }
}
</script>
