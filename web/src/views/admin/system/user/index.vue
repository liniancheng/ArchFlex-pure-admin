<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="登录名">
                <a-input v-model="queryParam.loginName" placeholder="请输入登录名"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="用户名">
                <a-input v-model="queryParam.username" placeholder="请输入用户名"/>
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
                  <a-select placeholder="请选择" default-value="0" v-model="queryParam.delFlag">
                    <a-select-option value="0">有效</a-select-option>
                    <a-select-option value="1">无效</a-select-option>
                    <a-select-option value="9">锁定</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-dropdown style="margin-left: 8px" type="primary" icon="plus" @click="$refs.createModal.add()">
                  <a-menu slot="overlay" @click="handleMenuClick">
                    <a-menu-item key="1" @click="handleImport()" v-if="authBtns.import">
                      导入用户
                    </a-menu-item>
                  </a-menu>
                  <a-button style="margin-left: 8px" type="primary" icon="down" @click="$refs.createModal.add()" v-if="authBtns.insert">新增</a-button>
                </a-dropdown>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div class="table-operator">
      </div>
      <s-table
        ref="table"
        size="default"
        rowKey="userId"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
        :scroll="{ x: 1200 }"
      >
        <span slot="roles" slot-scope="text, record">
          <a-tag v-for="(role, index) in record.sysRoleVoList" :key="index" :color="role.roleName.length > 5 ? 'blue' : 'green'">{{ role.roleName }}</a-tag>
        </span>
        <span slot="delFlag" slot-scope="text, record">
          <a :class="record.delFlag == '1' ? 'table_orange': (record.delFlag == '0' ?'table_black':'table_ash')">{{ record.delFlag == '1' ? '无效': (record.delFlag == '0' ?'有效':'锁定') }}</a>
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="$refs.createModal.edit(record)" v-if="authBtns.update">【修改】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.userId)" v-if="authBtns.delete">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
      <data-form ref="createModal" @ok="handleOk" />
      <impt-form ref="imptForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
// import Vue from 'vue'
import moment from 'moment'
import { STable } from '@/components'
import DataForm from './modules/DataForm'
import ImptForm from './modules/ImptForm'
import { getUserList, deleteUser } from '@/api/admin/user/index'

export default {
  name: 'TableList',
  components: {
    STable,
    DataForm,
    ImptForm
  },
  data () {
    return {
      authBtns: {
        insert: this.checkButtonAuth('/admin/system/user:insert'),
        update: this.checkButtonAuth('/admin/system/user:update'),
        delete: this.checkButtonAuth('/admin/system/user:delete'),
        import: this.checkButtonAuth('/admin/system/user:import'),
        select: this.checkButtonAuth('/admin/system/user:select')
      },
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 列表展示的列，初始为空。
      // columns: [],
       // 保存勾选的列设置
      settingColumns: [],
      // 定义列表可以展示的列信息
      columns: [
        {
            title: '登录名',
            dataIndex: 'loginName',
            width: 140,
            key: 'loginName'
        },
        {
            title: '用户名',
            dataIndex: 'userName',
            width: 160,
            key: 'userName'
        },
        {
            title: '邮箱',
            dataIndex: 'userEmail',
            width: 180,
            key: 'userEmail'
        },
        {
            title: '手机号',
            dataIndex: 'userMobTel',
            width: 150,
            align: 'center',
            key: 'userMobTel'
        },
        {
            title: '所属机构',
            dataIndex: 'branchNo',
            width: 180,
            key: 'branchNo'
        },
        {
          title: '角色',
          dataIndex: 'roles',
          width: 180,
          key: 'roles',
          scopedSlots: { customRender: 'roles' }
        },
        {
            title: '状态',
            dataIndex: 'delFlag',
            key: 'delFlag',
            width: 120,
            align: 'center',
            scopedSlots: {
            customRender: 'delFlag'
         }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 160,
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
       return getUserList(Object.assign(parameter, this.queryParam)).then(res => {
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
      // options: {
      //   alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
      //   rowSelection: {
      //     selectedRowKeys: this.selectedRowKeys,
      //     onChange: this.onSelectChange
      //   }
      // },
      options: {
        alert: { show: false, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: null
      },
      optionAlertShow: false
    }
  },
  methods: {
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handleDelete (id) {
        deleteUser(id).then(res => {
          if (res.code === 0) {
            // this.$message.success(res.message)
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
    },
    handleImport () {
      // 导入用户
      this.$refs.imptForm.show()
    }
  }
}
</script>
