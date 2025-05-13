<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="50%"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <a-spin :spinning="confirmLoading">
      <div>
        <a-card :bordered="false">
          <div class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :md="8" :sm="24">
                  <a-form-item label="明细名称">
                    <a-input v-model="queryParam.itemText" placeholder="明细名称" />
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <span class="table-page-search-submitButtons">
                    <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                    <a-button
                      style="margin-left: 8px"
                      type="primary"
                      icon="plus"
                      @click="$refs.dataForm.add(queryParam.dictId)"
                    >新增</a-button
                    >
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>

          <s-table
            ref="table"
            size="default"
            rowKey="id"
            :columns="columns"
            :data="loadData"
            :alert="options.alert"
            :rowSelection="options.rowSelection"
            showPagination="auto"
          >
            <span slot="action" slot-scope="text, record">
              <template>
                <a href="javascript:;" class="table_blue" @click="handleEdit(record.id)">【修改】</a>
                <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.id)">
                  <a class="table_orange">【删除】</a>
                </a-popconfirm>
              </template>
            </span>
          </s-table>
          <item-data-form ref="dataForm" @ok="handleOk" />
        </a-card>
      </div>
    </a-spin>
  </a-modal>
</template>
<script>
import { STable, Ellipsis } from '@/components'
import ItemDataForm from './ItemDataForm'
import { fetch, fetchOne, removeById } from '@/api/admin/dict/item/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    ItemDataForm
  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      modalWidth: window.innerWidth * 0.7,
      modalTitle: '',
      // 查询参数
      queryParam: {},
       form: this.$form.createForm(this),
      // 表头
      columns: [
        {
          key: 'itemText',
          title: '明细名称',
          dataIndex: 'itemText'
        },
        {
          key: 'itemValue',
          title: '明细数据值',
          dataIndex: 'itemValue'
        },
        {
            key: 'description',
            title: '明细描述',
            dataIndex: 'description'
        },
        {
            key: 'sortOrder',
            title: '明细排序',
                        dataIndex: 'sortOrder'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 200,
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetch(Object.assign(parameter, this.queryParam)).then(res => {
          if (res.code === 0) {
            return res.data
          } else {
            return {}
          }
        })
      },
      selectedRowKeys: [],
      selectedRows: [],
      // custom table alert & rowSelection
      options: {
        alert: {
          show: false,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: null
      },
      optionAlertShow: false
    }
  },
  methods: {
    edit (record) {
      this.resetSearchForm(record.dictId)
      this.modalTitle = '配置 - 字典明细'
      // 因为是异步请求，页面渲染刚开始的时候还没有拿到这个值，所以会报错。你需要在节点上用if判断一下，在有数据的时候再进行渲染。
      if (this.$refs.table !== undefined) {
          this.$refs.table.refresh(true)
      }
    },
     resetSearchForm (id) {
      this.queryParam = {
        dictId: id
      }
    },
    handleEdit (id) {
      fetchOne(id).then(res => {
        if (res.code === 0) {
          this.$refs.dataForm.edit(res.data)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleDelete (id) {
      removeById(id).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
          this.handleOk()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleOk () {
      this.$refs.table.refresh()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
      this.form.resetFields()
    }
  }
}
</script>
