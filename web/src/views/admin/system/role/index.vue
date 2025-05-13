<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="角色代码">
                <a-input v-model="queryParam.roleCode" placeholder="请输入角色代码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="角色名称">
                <a-input v-model="queryParam.roleName" placeholder="请输入角色名称"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <!--  <a-col :md="8" :sm="24">
                <a-form-item label="创建日期">
                  <a-date-picker v-model="queryParam.createTime" style="width: 100%" placeholder="请输入创建日期"/>
                </a-form-item>
              </a-col> -->
              <a-col :md="8" :sm="24">
                <a-form-item label="状态">
                  <a-select placeholder="请选择" v-model="queryParam.delFlag">
                    <a-select-option value="0">有效</a-select-option>
                    <a-select-option value="1">无效</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" v-if="authBtns.select" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" v-if="authBtns.select" @click="() => queryParam = {}">重置</a-button>
                <a-button style="margin-left: 8px" type="primary" icon="plus" v-if="authBtns.insert" @click="$refs.createModal.add()">新增</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="roleId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
      >
        <span slot="roles" slot-scope="text, record">
          <a-tag v-for="(role, index) in record.sysRoleVoList" :key="index" :color="role.roleName.length > 5 ? 'geekblue' : 'green'">{{ role.roleName }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" v-if="authBtns.update" @click="handleEdit(record)">【修改】</a>
            <a-popconfirm title="确定要删除?" v-if="authBtns.delete" @confirm="() => handleDelete(record.roleId)">
              <a class="table_orange" >【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="createModal" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
// import Vue from 'vue'
import moment from 'moment'
import { STable } from '@/components'
import DataForm from './modules/DataForm'
import { getRolesPage, deleteRole } from '@/api/admin/role/index'

export default {
  name: 'TableList',
  components: {
    STable,
    DataForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/system/role:insert'),
        update: this.checkButtonAuth('/admin/system/role:update'),
        delete: this.checkButtonAuth('/admin/system/role:delete'),
        select: this.checkButtonAuth('/admin/system/role:select')
      },
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
       // 保存勾选的列设置
      settingColumns: [],
      // 定义列表可以展示的列信息
      columns: [
        {
            title: '角色代码',
            dataIndex: 'roleCode',
            key: 'loginName'
        },
        {
            title: '角色名称',
            dataIndex: 'roleName',
            key: 'roleName'
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            key: 'createTime',
            customRender: (text, row, index) => {
              return this.formatDate(new Date(text))
            }
        },
        {
            title: '状态',
            dataIndex: 'delFlag',
            key: 'delFlag',
            customRender: function (text) {
              if (text === '1') {
                return '无效'
              } else {
                return '有效'
              }
            }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 200,
          fixed: 'right',
          align: 'center',
          scopedSlots: {
            customRender: 'action',
            filterDropdown: 'filterDropdown',
            filterIcon: 'filterIcon'
         }
        }
      ],
     loadData: parameter => {
       return getRolesPage(Object.assign(parameter, this.queryParam)).then(res => {
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
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    handleDelete (id) {
        deleteRole(id).then(res => {
          if (res.code === 0) {
            this.$message.success('删除成功!')
            this.handleOk()
          } else {
            this.$message.error('删除失败，请联系管理员！' + res.message)
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
  }
}
</script>
