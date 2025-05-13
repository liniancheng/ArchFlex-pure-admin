<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <template>
              <a-col :md="8" :sm="24">
                <a-form-item label="法人">
                  <a-select v-model="queryParam.lgprInstCd" placeholder="请选择" >
                    <a-select-option v-for="lgpr in lgprList" :value="lgpr" :key="lgpr">
                      {{ lgpr }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

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
                <a-form-item label="数据日期">
                  <a-date-picker v-model="queryParam.dataDt" @change="handleChange"/>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons" :style="{ float: 'right', overflow: 'hidden' } ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>

              </span>
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
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a class="table_blue" @click="handleSelect(record)">【<a-icon type="profile"/>】</a>
          </template>
        </span>
      </s-table>
      <data-form ref="dataForm" @ok="handleOk" />
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import DataForm from './modules/DataForm'
import { fetch, fetchOne, removeById, getlgprList, getSysNmList, getSysTabNmList } from '@/api/crossvalidation/chkresult/index'

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
      lgprList: [],
      // 系统
        sysList: [],
        sysNm: '',
        // 检核表
        sysTabList: [],
      // 查询参数
      queryParam: { sysNm: this.sysNm },
      // 表头
      columns: [
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
            key: 'ruleNo',
            title: '规则编号',
                        dataIndex: 'ruleNo',
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
            key: 'errNum',
            title: '错误条数',
                        dataIndex: 'errNum',
          // 超出宽度变成省略号
          ellipsis: true
        },
                                        {
            key: 'rcrdNum',
            title: '记录数',
                        dataIndex: 'rcrdNum',
          // 超出宽度变成省略号
          ellipsis: true
        },
                            {
          title: '操作',
          dataIndex: 'action',
          width: 150,
          align: 'right',
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
        alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
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
      watch: {
    sysNm: function (newVal) {
      this.sysNm = newVal
      this.loadSysTabList(this.sysNm)
    }
  },
  methods: {
    // 法人
    loadlgprList () {
      getlgprList().then((res) => {
          if (res.code === 0) {
            this.sysTabList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
    },
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
    handleChange (date, dateString) {
      this.queryParam.data = dateString
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
