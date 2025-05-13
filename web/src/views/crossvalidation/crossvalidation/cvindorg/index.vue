<template>
  <!-- 指标法人配置-->
  <div class="indexSystem">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">
            <a-col :md="6" :sm="24">
              <a-form-item label="机构/法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择" v-model="queryParam.orgVal" :allowClear="true">
                  <a-select-option v-for="org in orgList" :value="org.branchNo" :key="org.branchName">
                    {{ org.branchName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <!-- 使用单选模式可收索属性:show-search-->
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
              <span>
                <a-button type="primary" icon="search" @click="handleOk">查询</a-button>
                <a-button icon="reload" style="margin-left: 8px" @click="handleCancel">重置</a-button>
                <a-button
                  style="margin-left: 8px"
                  type="primary"
                  icon="plus"
                  @click="$refs.dataForm.add()">新增指标法人配置</a-button>
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
        :scroll="{ x: 1000 }"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="$refs.mend.edit(record)">【<a-icon type="tool"/>】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.id)">
              <a>【<a-icon type="rest" style="color: red"/>】</a>
            </a-popconfirm>
          </template>
        </span>
        <!-- 自定义类设置 begin -->
        <div slot="filterDropdown">
          <a-card>
            <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
              <a-row>
                <template v-for="(item,index) in defColumns">
                  <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                    <a-col :span="30" :key="index">
                      <a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox>
                    </a-col>
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type="setting" :style="{ fontSize:'16px',color: '#108ee9' }"/>
        <!-- 自定义类设置 end -->
      </s-table>
      <data-form ref="dataForm" @ok="handleOk"/>
      <data-mend ref="mend" @ok="handleOk"/>
    </a-card>
  </div>
</template>

<script>
  import { treeNode } from '@/api/crossvalidation/cvchkind/index'
  import { STable, Ellipsis } from '@/components'
  import { fetch, removeById } from '@/api/crossvalidation/cvindorg/index'
  import Vue from 'vue'
  import { fetchBranchs } from '@/api/admin/branch/index'
  import DataForm from './modules/DataForm'
  import DataMend from './modules/DataMend'

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
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        // 指标
        data: [],
        // 法人
        orgList: [],
        // 高级搜索 展开/关闭  默认关闭
        advanced: false,
        treeData: [],
        // 查询参数
        queryParam: {},
        // 表头
        columns: [],
        // 列设置
        settingColumns: [],
        // 列定义
        defColumns: [
          {
            key: 'index',
            title: '序号',
            width: 150,
            fixed: 'left',
            dataIndex: 'index',
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
            key: 'orgNm',
            title: '机构/法人',
              // 超出宽度变成省略号
          ellipsis: true,
            dataIndex: 'orgNm'
          },
          {
            key: 'isUse',
            title: '法人适用',
            dataIndex: 'isUse',
            customRender: (text, record, index) => {
              if (text === 'Y') {
                return '是'
              } else {
                return '否'
              }
            }
          },
          {
            key: 'isShow',
            title: '参与对比',
            width: 200,
            dataIndex: 'isShow',
            customRender: (text, record, index) => {
              if (text === 'Y') {
                return '是'
              } else {
                return '否'
              }
            }
          },
          {
            title: '编辑/删除',
            dataIndex: 'action',
            width: 150,
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
       // 重置
    handleCancel () {
      this.queryParam = {}
      this.form = this.$form.createForm(this)
      this.handleOk()
    },
      // 删除
      handleDelete (id) {
        removeById(id).then(res => {
          if (res.data) {
            this.$message.success('删除成功！')
            this.handleOk()
          } else {
            this.$message.error('删除失败!')
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
