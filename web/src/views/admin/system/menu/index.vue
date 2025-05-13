<template>
  <div class="indexSystem">
    <a-card :bordered="false">
      <div>
        <div class="table-operator">
          <a-button type="primary" icon="plus" @click="handleAdd" v-if="authBtns.insert">新增</a-button>
          <a-button icon="sync" @click="loadData" v-if="authBtns.select">刷新</a-button>
        </div>
        <!-- <a-table
          ref="table"
          size="default"
          defaultExpandAll
          rowKey="id"
          :columns="columns"
          :data-source="data"
          :loading="loading"
          :expandRowByClick="true"
          :scroll="{ x: 1200 }">
          <span slot="tip" slot-scope="text">
            <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
          </span>
          <span slot="icon" slot-scope="text">
            <a-icon :type="text" />
          </span>
          <span slot="status" slot-scope="text">
            <a-icon :type="text === '0' ? 'bars': 'youtube'" /> {{ text === '0'? '菜单':'按钮' }}
          </span>
          <span slot="action" slot-scope="text, record">
            <template>
              <a
                class="table_blue"
                @click="$refs.createModal.add(record.id)"
                v-if="record.type === '0' && authBtns.insert">【新增】</a>
              <a
                class="table_blue"
                v-if="authBtns.update"
                @click="handleEdit(record.id)">【修改】</a>
              <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(record.id)">
                <a class="table_orange" >【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table> -->
        <a-tree
          :replaceFields="{children:'children', title:'name', key:'id' }"
          :blockNode="true"
          :selectable="false"
          :tree-data="data">
          <template slot="name" slot-scope="item">
            <span><a-icon :type="item.icon" v-if="item.icon" /> {{ item.name }}</span>
            <span style="float:right;">
              <div class="imoia-tree-type">
                <a-tag :color="item.type === '0' ? 'geekblue' : 'green'">{{ item.type ==='0'?'菜单':'按钮' }}</a-tag>
              </div>
              <div class="imoia-tree-action">
                <a @click="$refs.createModal.add(item.id)" v-if="item.type === '0' && authBtns.insert">【新增】</a>
                <a v-if="authBtns.update" @click="handleEdit(item.id)">【修改】</a>
                <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(item.id)">
                  <a class="table_orange" >【删除】</a>
                </a-popconfirm>
              </div>
            </span>
            <!-- <a-row>
              <a-col span="12"><a-icon :type="item.icon" v-if="item.icon" /> {{ item.name }}</a-col>
              <a-col span="6"><a-tag :color="item.type === '0' ? 'geekblue' : 'green'">{{ item.type ==='0'?'菜单':'按钮' }}</a-tag></a-col>
              <a-col span="6" style="text-align:right;">
                <a @click="$refs.createModal.add(item.id)" v-if="item.type === '0' && authBtns.insert">【新增】</a>
                <a v-if="authBtns.update" @click="handleEdit(item.id)">【修改】</a>
                <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(item.id)">
                  <a class="table_orange" >【删除】</a>
                </a-popconfirm>
              </a-col>
            </a-row> -->
          </template>
        </a-tree>
        <data-form ref="createModal" @ok="handleOk" />
      </div>
    </a-card>
  </div>
</template>
<script>
import { getMenus, deleteMenu, getMenuById } from '@/api/admin/menu/index'
import { Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
const columns = [
  { title: '资源名称', width: 300, fixed: 'left', dataIndex: 'name', key: 'name' },
  { title: '资源类型', dataIndex: 'type', width: 120, align: 'center', key: 'type', scopedSlots: { customRender: 'status' } },
  { title: '资源图标', dataIndex: 'icon', width: 120, align: 'center', key: 'icon', scopedSlots: { customRender: 'icon' } },
  { title: '路径名称', dataIndex: 'routeName', width: 200, key: 'routeName', ellipsis: true },
  { title: '资源链接', dataIndex: 'path', key: 'path', width: 200, ellipsis: true },
  { title: '资源权限', dataIndex: 'permission', key: 'permission', width: 200, ellipsis: true },
  { title: '操作', dataIndex: '', key: 'x', fixed: 'right', width: 220, align: 'center', scopedSlots: { customRender: 'action' } }
]

export default {
    components: {
      Ellipsis,
      DataForm
    },
    data () {
      return {
        authBtns: {
          insert: this.checkButtonAuth('/admin/system/menu:insert'),
          update: this.checkButtonAuth('/admin/system/menu:update'),
          delete: this.checkButtonAuth('/admin/system/menu:delete'),
          select: this.checkButtonAuth('/admin/system/menu:select')
        },
        data: [],
        loading: false,
        columns,
        selectedRowKeys: [],
        selectedRows: [],
        expandedRowKeys: []
        }
    },
    mounted () {
      this.loadData()
   },
  methods: {
    handleAdd () {
      this.$refs.createModal.add('-1')
    },
    handleEdit (id) {
      getMenuById(id).then(res => {
        if (res.code === 0) {
          this.$refs.createModal.edit(res.data)
        }
      })
    },
    handleDelete (id) {
        deleteMenu(id).then(res => {
          if (res.code === 0) {
            this.$message.success('删除成功!')
            this.handleOk()
          } else {
            this.$message.error('删系除失败，请联管理员！' + res.message)
          }
        })
    },
    loadData () {
      this.loading = true
       getMenus().then(res => {
          // this.data = res.data
          this.data = this.exchangeNodes(res.data)
       }).finally(() => {
          this.loading = false
        })
    },
    exchangeNodes (nodes) {
      if (nodes && nodes.length > 0) {
        return nodes.map(item => {
          const node = { ...item, scopedSlots: { title: 'name' }, children: this.exchangeNodes(item.children) }
          return node
        })
      } else {
        return null
      }
    },
    handleOk () {
      this.loadData()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    onClearSelected () {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    handleExpandedRowsChange (expandedRows) {
      this.expandedRowKeys = expandedRows
    }
  }
}
</script>
<style lang="less">
  .ant-tree > li:nth-child(2n) {
      background: #f9f9f9;
  }
  .imoia-tree-action{
    display: inline-block;width: 200px;text-align: right;
  }
  .imoia-tree-type{
    display:inline-block;
  }
</style>
