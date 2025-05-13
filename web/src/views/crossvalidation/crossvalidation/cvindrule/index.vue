<template>
  <!-- 指标规则校验设置 -->
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-tree-select
                  show-search
                  tree-node-filter-prop="title"
                  style="width: 100%"
                  :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                  placeholder="请选择"
                  allow-clear
                  tree-default-expand-all
                  :treeData="data"
                  v-model="queryParam.indNo"
                >
                </a-tree-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="系统" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择" v-model="queryParam.sysVal" allow-clear>
                  <a-select-option v-for="sys in sysList" :value="sys.itemValue" :key="sys.itemText">
                    {{ sys.itemText }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择" v-model="queryParam.orgVal" allow-clear>
                  <a-select-option v-for="org in orgList" :value="org.branchNo" :key="org.branchName">
                    {{ org.branchName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template>
              <a-col :md="8" :sm="24">
                <a-form-item label="指标类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select placeholder="请选择" v-model="queryParam.ruleVal" allow-clear>
                    <a-select-option value="00">全部类型</a-select-option>
                    <a-select-option value="01">通用指标</a-select-option>
                    <a-select-option value="02">特色指标</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24">
              <span>
                <a-button type="primary" icon="search" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button icon="reload" style="margin-left: 8px" @click="handleOk">重置</a-button>
                <a-button
                  style="margin-left: 8px"
                  type="primary"
                  icon="plus"
                  @click="$refs.dataForm.add()">新增指标校验规则</a-button>
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
        :scroll="{ x: 1300 }"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleEdit(record.id)">【<a-icon type="tool"/>】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.id)">
              <a class="table_orange">【<a-icon type="rest" style="color: red"/>】</a>
            </a-popconfirm>
          </template>
        </span>
        <!-- 自定义类设置 begin -->
        <div slot="filterDropdown">
          <a-card>
            <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
              <a-row>
                <template v-for="(item, index) in defColumns">
                  <template v-if="item.key != 'rowIndex' && item.dataIndex != 'action'">
                    <a-col
                      :span="30"
                      :key="index"
                    >
                      <a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox>
                    </a-col
                    >
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type="setting" :style="{ fontSize: '16px', color: '#108ee9' }"/>
        <!-- 自定义类设置 end -->
      </s-table>
      <data-form ref="dataForm" @ok="handleOk"/>
      <data-mend ref="mend" @ok="handleOk"/>
    </a-card>
  </div>
</template>

<script>
  import { STable, Ellipsis } from '@/components'
  import { fetchOne, fetch, removeById } from '@/api/crossvalidation/cvindrule/index'
  import { treeNode } from '@/api/crossvalidation/cvchkind/index'
  import { fetchSys } from '@/api/crossvalidation/cvdict/index'
  import { fetchBranchs } from '@/api/admin/branch/index'
  import DataForm from './modules/DataForm.vue'
  import DataMend from './modules/DataMend.vue'
  import Vue from 'vue'

  export default {
    name: 'TableList',
    components: {
      STable,
      Ellipsis,
      DataForm,
      DataMend
    },
    data () {
      return {
        // 系统
        sysList: [],
        dictType: '',
        // 法人
        orgList: [],
        // 指标
        data: [],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        // 高级搜索 展开/关闭  默认关闭
        advanced: false,
        // 查询参数
        queryParam: { ruleVal: '00' },
        // 表头
        columns: [],
        // 列设置
        settingColumns: [],
        // 列定义
        defColumns: [
          {
            key: 'index',
            title: '序号',
            width: 100,
            fixed: 'left',
            dataIndex: 'index',
            // customRender函数来渲染序号的数据
            customRender: (text, record, index) => index + 1
          },
          {
            key: 'indNm',
            title: '指标名称',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true,
            dataIndex: 'indNm'
          },
          {
            key: 'sysNm',
            title: '系统',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true,
            dataIndex: 'sysNm'
          },
          {
            key: 'chkSql',
            title: '校验SQL',
            dataIndex: 'chkSql',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true
          },
          {
            key: 'orgNm',
            title: '法人',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true,
            dataIndex: 'orgNm'
          },
          {
            key: 'state',
            title: '是否可用',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true,
            dataIndex: 'state',
            customRender: (text, record, index) => {
              if (text === '0') {
                return '否'
              }
              if (text === '1') {
                return '是'
              }
            }
          },
          {
            title: '编辑/删除',
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
        // 加载数据方法 必须为 Promise 对象
        loadData: (parameter) => {
          return fetch(Object.assign(parameter, this.queryParam)).then((res) => {
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
    created () {
      this.initColumns()
      this.loadTreeList()
      this.loadSysList()
      this.loadOrgList()
    },
    methods: {
      // 指标
      loadTreeList () {
        treeNode().then((res) => {
          if (res.code === 0) {
            this.data = this.exchangeNodes(res.data)
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      exchangeNodes (nodes) {
        if (nodes && nodes.length > 0) {
          return nodes.map((item) => {
            const node = { ...item, scopedSlots: { title: 'indNm' }, children: this.exchangeNodes(item.children) }
            return node
          })
        } else {
          return null
        }
      },
      // 系统
      loadSysList () {
        this.dictType = 'IND_SYS'
        fetchSys(this.dictType).then((res) => {
          if (res.code === 0) {
            this.sysList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 法人
      loadOrgList () {
        fetchBranchs().then((res) => {
          if (res.code === 0) {
            this.orgList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 修改
      handleEdit (id) {
        fetchOne(id).then(res => {
          if (res.code === 0) {
            this.$refs.mend.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 删除
      handleDelete (id) {
        removeById(id).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.handleOk()
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 刷新
      handleOk () {
        this.queryParam = {}
        this.$refs.table.refresh(true)
      },
      onSelectChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      toggleAdvanced () {
        this.advanced = !this.advanced
      },
      // 自定义列设置 begin
      onColSettingsChange (checkedValues) {
        var key = this.$route.name + ':colsettings'
        Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
        this.settingColumns = checkedValues
        const cols = this.defColumns.filter((item) => {
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
          const cols = this.defColumns.filter((item) => {
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
