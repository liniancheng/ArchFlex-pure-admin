<template>
  <!-- 校验结果展示 -->
  <div class="indexSystem">
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">
            <a-col :md="6" :sm="24">
              <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择" v-model="queryParam.orgVal" allowClear>
                  <a-select-option v-for="org in orgList" :value="org.branchNo" :key="org.branchName">
                    {{ org.branchName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="任务名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-model="queryParam.planName" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form :form="form">
                <a-form-item label="系统" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-select mode="tags" v-decorator="['sysVals']" :maxTagCount="4" :allowClear="true">
                    <a-select-option v-for="sys in sysList" :value="sys.itemValue" :key="sys.itemText" >
                      {{ sys.itemText }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-form>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="数据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker v-model="queryParam.dataTime" @change="handleChange"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="执行进度" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select placeholder="请选择" v-model="queryParam.planState" allowClear>
                  <a-select-option value="2">已完成</a-select-option>
                  <a-select-option value="1">未完成</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <span>
                <a-button type="primary" icon="search" @click="handleLoadData">查询</a-button>
                <a-button icon="reload" style="margin-left: 8px" @click="handleReload">重置</a-button>
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
        :scroll="{ x: 1900 }"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.id)">
              <a>【<a-icon type="rest" style="color: red"/>】</a>
            </a-popconfirm>
            <a class="table_blue" @click="handleSelect(record)">【<a-icon type="profile"/>】</a>
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
                    ><a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox></a-col
                    >
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type="setting" :style="{ fontSize: '16px', color: '#108ee9' }" />
        <!-- 自定义类设置 end -->
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { fetch, deletePlan } from '@/api/crossvalidation/cvchkplan/index'
import { fetchSys } from '@/api/crossvalidation/cvdict/index'
import DataForm from './modules/DataForm'
import { fetchBranchs } from '@/api/admin/branch/index'
import Vue from 'vue'
export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm
  },
  data () {
    return {
      // 法人
      orgList: [],
      form: this.$form.createForm(this),
      data: [],
      sysList: [],
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
          width: 100,
          fixed: 'left',
          dataIndex: 'index',
          // customRender函数来渲染序号的数据
          customRender: (text, record, index) => index + 1
        },
        {
          key: 'id',
          title: '计划编号',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'id'
        },
        {
          key: 'planName',
          title: '计划名称',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'planName'
        },
        {
          key: 'createUser',
          title: '创建人',
          dataIndex: 'createUser',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'createTime',
          title: '创建时间',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'createTime'
        },
        {
          key: 'dataTime',
          title: '数据时间',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'dataTime'
        },
        {
          key: 'planType',
          title: '任务执行时间',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'planType',
          customRender: (text, record, index) => {
            if (text === '1') {
              return '实时执行'
            }
            if (text === '2') {
              return '定时执行'
            }
          }
        },
        {
          key: 'planState',
          title: '任务执行状态',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'planState',
          customRender: (text, record, index) => {
            if (text === '1') {
              return '未完成'
            }
            if (text === '2') {
              return '已完成'
            }
          }
        },
        {
          key: 'orgNm',
          title: '机构/法人',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'orgNm'
        },
        {
          key: 'dateVal',
          title: '频度',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'dateVal'
        },
        {
          key: 'sysVal',
          title: '系统',
           width: 200,
          // 超出宽度变成省略号
          ellipsis: true,
          dataIndex: 'sysVal'
        },
        {
          title: '删除/查看详情',
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
     this.loadSysList()
     this.loadOrgList()
  },
  methods: {
    // 根据条件查询
    handleLoadData () {
     const { form: { validateFields } } = this
      validateFields((errors, values) => {
        this.queryParam.sysVals = values.sysVals
      })
      this.$refs.table.refresh(true)
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
    // 重置
    handleReload () {
      this.queryParam = {}
      this.form = this.$form.createForm(this)
      this.handleOk()
    },
    handleSelect (record) {
      this.$refs.dataForm.select(record)
    },
    handleDelete (planId) {
      deletePlan(planId).then(res => {
        if (res.data) {
          this.$message.success('删除成功！')
          this.handleOk()
        } else {
          this.$message.error('删除指标计划失败！')
        }
      })
    },
    handleChange (date, dateString) {
      this.queryParam.dataTime = dateString
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
