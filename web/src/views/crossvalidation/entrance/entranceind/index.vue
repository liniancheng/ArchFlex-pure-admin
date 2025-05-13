<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <template>
              <a-col :md="8" :sm="24">
                <a-form-item label="系统名">
                  <a-select placeholder="请选择" v-model="sysNm" allow-clear>
                    <a-select-option v-for="sys in sysList" :value="sys" :key="sys">
                      {{ sys }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="检核表名">
                  <a-select placeholder="请选择" v-model="sysTabNm" allow-clear>
                    <a-select-option v-for="sysTab in sysTabList" :value="sysTab" :key="sysTab">
                      {{ sysTab }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="检核字段名">
                  <a-select v-model="queryParam.colNm" placeholder="" >
                    <a-select-option v-for="sysColumn in sysColumnList" :value="sysColumn" :key="sysColumn">
                      {{ sysColumn }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
          </a-row>
          <a-row>
            <a-col :md="8" :sm="24">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="hanleReload">重置</a-button>
              <a-button
                style="margin-left: 8px"
                type="primary"
                icon="plus"
                @click="$refs.dataForm.add()"
              >新建</a-button
              >
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="ruleNo"
        :columns="columns"
        :data="loadData"
        showPagination="auto">
        <span slot="action" slot-scope="text, record">
          <template>
            <a href="javascript:;" @click="handleEdit(record.ruleNo)">【<a-icon type="tool"/>】</a>
            <a class="table_blue" @click="handleSelect(record)">【<a-icon type="profile"/>】</a>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
      <query-form ref="queryForm" @ok="handleOk"/>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
import QueryForm from './modules/QueryForm'
import { fetch, fetchOne, removeById, getSysNmList, getSysTabNmList, getSysColunmList } from '@/api/crossvalidation/entranceind/index'

export default {
  name: 'TableList',
  components: {
    STable,
    Ellipsis,
    DataForm,
    QueryForm
},
  data () {
    return {
      // 系统
        sysList: [],
        sysNm: '',
        // 检核表
        sysTabList: [],
        sysTabNm: '',
        // 字段
        sysColumnList: [],
      // 查询参数
      queryParam: { sysNm: this.sysNm, sysTabNm: this.sysTabNm },
      // 表头
      columns: [
        {
          title: '序号',
          dataIndex: 'index',
          align: 'center',
          width: 100,
          // customRender函数来渲染序号的数据
          customRender: (text, record, index) => index + 1,
          fixed: 'left'
        },
        {
          key: 'ruleNo',
          title: '规则编号',
          dataIndex: 'ruleNo',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'rltypDsc',
          title: '规则类型',
          dataIndex: 'rltypDsc',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'ruleDsc',
          title: '规则描述',
          dataIndex: 'ruleDsc',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'sysNm',
          title: '系统名',
          dataIndex: 'sysNm',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'sysTabNm',
          title: '检核表名',
          dataIndex: 'sysTabNm',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'colNm',
          title: '检核字段名',
          dataIndex: 'colNm',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          key: 'vldFlg',
          title: '是否启用',
          dataIndex: 'vldFlg',
          // 超出宽度变成省略号
          ellipsis: true
        },
        {
          title: '修改/查看',
          dataIndex: 'action',
          width: 150,
          align: 'right',
          scopedSlots: { customRender: 'action' }
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
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  created () {
      this.loadSysList()
    },
  // computed: {
  //   sysTabList: function () {
  //     const sysNm = this.queryParam.sysNm
  //     if (sysNm) {
  //     return this.loadSysTabList(sysNm)
  //     } else {
  //       return []
  //     }
  //   }
  // },
  watch: {
    sysNm: function (newVal) {
      this.sysNm = newVal
      this.loadSysTabList(this.sysNm)
    },
    sysTabNm: function (newVal) {
      this.sysTabNm = newVal
      this.loadSysColumnList(this.sysTabNm)
    }
  },
  methods: {
    // 系统
      loadSysList () {
        getSysNmList().then((res) => {
          if (res.code === 0) {
            this.sysList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 表
      loadSysTabList (sysNm) {
        getSysTabNmList(sysNm).then((res) => {
          if (res.code === 0) {
            this.sysTabList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 字段
      loadSysColumnList (sysTabNm) {
        getSysColunmList(sysTabNm).then((res) => {
          if (res.code === 0) {
            this.sysColumnList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
    handleEdit (id) {
      fetchOne(id).then((res) => {
        if (res.code === 0) {
          this.$refs.dataForm.edit(res.data)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleSelect (record) {
      this.$refs.queryForm.select(record)
    },
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
    hanleReload () {
      this.queryParam = {}
      this.sysNm = ''
      this.sysTabNm = ''
      this.handleOk()
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
