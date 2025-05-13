<template>
  <div>
    <a-card :bordered="false">
      <a-row :gutter="24">
        <a-col :span="5">
          <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 12px;"> -->
          <a-alert type="info" :showIcon="true">
            <div slot="message">
              知识类型：<span v-if="this.currSelected.title">{{ getCurrSelectedTitle() }}</span>
              <a v-if="this.currSelected.title" style="margin-left: 10px" @click="clearSelected">取消选择</a>
            </div>
          </a-alert>
          <!-- </div> -->
          <a-card :bordered="true" style="min-height: calc(100vh - 274px);">
            <a-tree
              v-if="treeData !== null && treeData.length > 0"
              defaultExpandAll
              :selectedKeys="selectedKeys"
              :show-line="showLine"
              :treeData="treeData"
              @select="selectChange"
            ></a-tree>
          </a-card>
        </a-col>
        <a-col :span="18">
          <div class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :md="8" :sm="24">
                  <a-form-item label="知识标题">
                    <a-input v-model="queryParam.knowledgeTitle" placeholder="知识标题"/>
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <a-form-item label="知识描述">
                    <a-input v-model="queryParam.knowledgeRmk" placeholder="知识描述"/>
                  </a-form-item>
                </a-col>
                <template v-if="advanced">
                  <a-col :md="8" :sm="24">
                    <a-form-item label="知识状态">
                      <a-select v-model="queryParam.knowledgeStatus" placeholder="请选择">
                        <a-select-option :value="1">有效</a-select-option>
                        <a-select-option :value="0">无效</a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                </template>
                <a-col :md="!advanced && 8 || 24" :sm="24">
                  <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                    <a-button v-if="authBtns.select" icon="search" type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                    <a-button v-if="authBtns.select" icon="reload" style="margin-left: 8px" @click="reset">重置</a-button>
                    <a v-if="authBtns.select" @click="toggleAdvanced" style="margin-left: 8px">
                      {{ advanced ? '收起' : '展开' }}
                      <a-icon :type="advanced ? 'up' : 'down'"/>
                    </a>
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <div class="table-operator">
            <a-button type="primary" v-if="authBtns.insert" icon="plus" @click="handleAddDir">新增类型</a-button>
            <a-button icon="sync" @click="refresh">刷新</a-button>
            <a-button type="primary" v-if="authBtns.insert" icon="plus" @click="handleAdd">新增知识</a-button>
          </div>
          <s-table
            ref="table"
            size="default"
            rowKey="knowledgeId"
            :columns="columns"
            :data="loadData"
            :alert="options.alert"
            :rowSelection="options.rowSelection"
            showPagination="auto"
          >
            <span slot="knowledgeContent" slot-scope="text">
              <span v-html="text"> {{ text }}</span>
            </span>
            <span slot="tip" slot-scope="text">
              <ellipsis :length="20" tooltip>{{ text }}</ellipsis>
            </span>
            <span slot="knowledgeStatus" slot-scope="text">
              <a-tag :color="text === '1' ? 'green' : 'red'">{{ text ==='1'?'有效':'无效' }}</a-tag>
            </span>
            <span slot="action" slot-scope="text, record">
              <template>
                <!-- <a v-if="authBtns.update" class="table_blue" @click="downloadShow(record.knowledgeId)">【下载】</a> -->
                <a v-if="authBtns.update" class="table_blue" @click="handleEdit(record.knowledgeId)">【修改】</a>
                <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.knowledgeId)">
                  <a class="table_orange" >【删除】</a>
                </a-popconfirm>
              </template>
            </span>
          </s-table>
          <data-form ref="dataForm" @ok="handleOk" />
          <dir-form ref="dirForm" @ok="handleOk" @clearSelected="refresh"/>
          <data-list ref="dataList" @ok="handleOk" />
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DirForm from './modules/DirForm'
import DataForm from './modules/DataForm'
import DataList from './modules/DataList'
import { fetch, fetchOne, removeById } from '@/api/admin/knowledge/index'
import { getTreeNodes } from '@/api/admin/dir/index'
export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm,
    DataList,
    DirForm
  },
  data () {
    return {
      showLine: true,
      treeData: [],
      currSelected: {},
      selectedKeys: [],
      authBtns: {
        insert: this.checkButtonAuth('/admin/knowledge/manage:insert'),
        update: this.checkButtonAuth('/admin/knowledge/manage:update'),
        delete: this.checkButtonAuth('/admin/knowledge/manage:delete'),
        select: this.checkButtonAuth('/admin/knowledge/manage:select')
      },
      // 高级搜索 展开/关闭  默认关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
            key: 'knowledgeTitle',
            title: '知识标题',
            dataIndex: 'knowledgeTitle',
            scopedSlots: { customRender: 'tip' }
        },
         {
            key: 'dirId',
            title: '知识类型',
            dataIndex: 'dirId',
            scopedSlots: { customRender: 'tip' }
        },
        {
            key: 'knowledgeRmk',
            title: '知识描述',
            dataIndex: 'knowledgeRmk',
            scopedSlots: { customRender: 'tip' }
        },
        {
            key: 'keyWords',
            title: '关键词',
            dataIndex: 'keyWords',
            scopedSlots: { customRender: 'tip' }
        },
        {
            key: 'knowledgeStatus',
            title: '有效状态',
            dataIndex: 'knowledgeStatus',
            scopedSlots: { customRender: 'knowledgeStatus' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 250,
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
        alert: { show: false, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: null
      },
      optionAlertShow: false
    }
  },
  methods: {
    getCurrSelectedTitle () {
      return !this.currSelected.title ? '' : this.currSelected.title
    },
    clearSelected () {
      this.currSelected = {}
      this.selectedKeys = []
      this.queryParam.dirId = null
      this.handleOk()
    },
    selectChange (selectedKeys, info) {
      const record = info.node.dataRef
      this.queryParam.dirId = record.key
      this.currSelected = Object.assign({}, record)
      this.selectedKeys = [record.key]
      this.handleOk()
    },
    refresh () {
      this.initTree()
      this.$refs.table.refresh()
      this.$refs.dataForm.loadTreeList()
      this.clearSelected()
    },
    reset () {
       this.queryParam = {}
       this.clearSelected()
    },
    downloadShow (id) {
      this.$refs.dataList.add(id)
    },
    handleAddDir () {
      // this.$refs.dirForm.add()
      this.$refs.dirForm.add()
      // this.$refs.dirForm.visible = true
    },
    handleAdd () {
      const record = {
        dirId: this.queryParam.dirId,
        knowledgeStatus: '1',
        knowledgeContent: ''
      }
      this.$refs.dataForm.add(record)
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
      initTree () {
        getTreeNodes().then(res => {
          if (res.code === 0) {
            this.treeData = res.data
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
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  },
  mounted () {
    this.initTree()
  }
}
</script>
