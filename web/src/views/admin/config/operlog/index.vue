<template>
  <div class="indexSystem">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="模块名称">
                <a-input v-model="queryParam.moduleName" allowClear placeholder="请输入模块名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="操作状态">
                <a-select placeholder="请选择" default-value="0" v-model="queryParam.logStatus">
                  <a-select-option value="0">成功</a-select-option>
                  <a-select-option value="1">失败</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' }">
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button>
                <a-popconfirm
                  v-if="authBtns.delete"
                  :disabled="selectedRowKeys.length === 0"
                  title="确定要批量删除选中数据?"
                  @confirm="() => handleDeleteSelected()">
                  <a-button
                    :disabled="selectedRowKeys.length === 0"
                    style="margin-left: 8px"
                    type="danger"
                    icon="delete">批量删除</a-button>
                </a-popconfirm>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="logId"
        :columns="columns"
        :selectedRowKeys="selectedRowKeys"
        :data="loadData"
        :alert="options.alert"
        :rowSelection="options.rowSelection"
        showPagination="auto"
        :scroll="{ x: 1900 }"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a-popconfirm v-if="authBtns.delete" title="确定要删除?" @confirm="() => handleDelete(record.logId)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
        <span slot="tip" slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </span>
        <span slot="status" slot-scope="text">
          <a-tag :color="text === '1' ? 'red' : 'green'">{{ text === '1' ?'失败':'成功' }}</a-tag>
        </span>
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <!-- 自定义类设置 begin -->
        <div slot="filterDropdown">
          <a-card>
            <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
              <a-row>
                <template v-for="(item,index) in defColumns">
                  <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                    <a-col :span="30" :key="index"><a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox></a-col>
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type="setting" :style="{ fontSize:'16px',color: '#108ee9' }" />
        <!-- 自定义类设置 end -->
      </s-table>
    </a-card>
  </div>
</template>
<script>
import Vue from 'vue'
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { fetch, removeSelected, removeById } from '@/api/config/operlog/index'
export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      authBtns: {
        delete: this.checkButtonAuth('/admin/oper/log:delete')
      },
      mdl: {},
      // 查询参数
      queryParam: {},

      // 表头
      // 表头
      columns: [],
      // 列设置
      settingColumns: [],
      // 列定义
      defColumns: [
        {
          title: '序号',
          width: 50,
          scopedSlots: { customRender: 'serial' }
        },
        {
            title: '用户名',
            dataIndex: 'createBy',
            width: 100,
            key: 'createBy'
        },
        {
            title: '模块名称',
            width: 100,
            dataIndex: 'moduleName',
            key: 'moduleName',
            scopedSlots: { customRender: 'tip' }
        },
        {
            title: '操作方法',
            width: 100,
            dataIndex: 'actionName',
            key: 'actionName',
            scopedSlots: { customRender: 'tip' }
        },
        {
            title: '操作状态',
            width: 100,
            dataIndex: 'logStatus',
            key: 'logStatus',
            scopedSlots: { customRender: 'status' }
        },
        {
            title: '操作IP',
            width: 100,
            dataIndex: 'remoteAddr',
            key: 'remoteAddr',
            scopedSlots: { customRender: 'tip' }
        },
        {
            title: '请求方式',
            width: 100,
            dataIndex: 'methodName',
            key: 'methodName'
        },
        {
            title: '请求参数',
            width: 100,
            dataIndex: 'params',
            key: 'params',
            scopedSlots: { customRender: 'tip' }
        },
         {
            title: '请求时长',
            width: 100,
            dataIndex: 'operTime',
            key: 'operTime'
        },
        {
            title: '请求URI',
            width: 100,
            dataIndex: 'requestUri',
            key: 'requestUri',
            scopedSlots: { customRender: 'tip' }
        },
        {
            title: 'User-Agent',
            width: 100,
            dataIndex: 'userAgent',
            key: 'userAgent',
            scopedSlots: { customRender: 'tip' }
        },
         {
            title: '创建时间',
            width: 150,
            dataIndex: 'createTime',
            key: 'createTime',
            customRender: (text, row, index) => {
              return this.formatDate(new Date(text))
            }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 100,
          fixed: 'right',
          align: 'center',
          scopedSlots: {
            customRender: 'action',
            filterDropdown: 'filterDropdown',
            filterIcon: 'filterIcon'
         }
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
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  created () {
      this.initColumns()
  },
  methods: {
    downloadShow (id) {
      this.$refs.dataList.add(id)
    },
    handleAdd () {
      const record = {
        annoLevel: '1',
        isValid: '1'
      }
      this.$refs.dataForm.visible = true
      this.$refs.dataForm.add(record)
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
    handleDeleteSelected () {
      removeSelected(this.selectedRowKeys).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.selectedRowKeys = []
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
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    },
     // 自定义列设置 begin
    onColSettingsChange (checkedValues) {
        var key = this.$route.name + ':colsettings'
        Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
        this.settingColumns = checkedValues
        const cols = this.defColumns.filter(item => {
          if (item.key === 'rowIndex' || item.dataIndex === 'action') {
            return true
          }
          if (this.settingColumns.includes(item.dataIndex)) {
            return true
          }
          return false
        })
        this.columns = cols
      },
      initColumns () {
        // 权限过滤（列权限控制时打开，修改第二个参数为授权码前缀）
        // this.defColumns = colAuthFilter(this.defColumns,'testdemo:')
        var key = this.$route.name + ':colsettings'
        const colSettings = Vue.ls.get(key)
        if (colSettings == null || colSettings === undefined) {
          const allSettingColumns = []
          this.defColumns.forEach(function (item, i, array) {
            allSettingColumns.push(item.dataIndex)
          })
          this.settingColumns = allSettingColumns
          this.columns = this.defColumns
        } else {
          this.settingColumns = colSettings
          const cols = this.defColumns.filter(item => {
            if (item.key === 'rowIndex' || item.dataIndex === 'action') {
              return true
            }
            if (colSettings.includes(item.dataIndex)) {
              return true
            }
            return false
          })
          this.columns = cols
        }
      }
   // 自定义列设置 end
  }
}
</script>
