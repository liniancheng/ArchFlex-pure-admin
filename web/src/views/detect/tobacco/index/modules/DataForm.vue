<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    @ok="handleOk"
    okText="导出"
    cancelText="关闭"
    :destroyOnClose="true"
    width="1400"
    wrap-class-name="full-modal"
  >
    <el-table
      :data="data"
      style="width: 100%"
      @header-click="systemclick"
    >
      <el-table-column
        fixed
        label="序号"
        width="70px">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column
        fixed
        prop="indNo"
        label="指标编号"
        width="200">
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        prop="indNm"
        fixed
        label="指标名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="orgNm"
        label="机构/法人"
        width="200">
      </el-table-column>
      <el-table-column
        prop="currencyVal"
        label="币种"
        width="100">
      </el-table-column>
      <el-table-column
        prop="t1104PartVal"
        label="1104"
        width="200"
        class-name="t1104"
      >
      </el-table-column>
      <el-table-column
        prop="t1104Frq"
        label="1104频度"
        width="100"
        :formatter="SystemFrequency"
      >
      </el-table-column>
      <el-table-column
        prop="eastPartVal"
        label="EAST"
        width="200"
        class-name="eastItem"
      >
      </el-table-column>
      <el-table-column
        prop="eastFrq"
        label="EAST频度"
        width="100"
        :formatter="SystemFrequency"
      >
      </el-table-column>
      <el-table-column
        prop="bigFocusPartVal"
        label="金融统计大集中"
        width="200"
        class-name="bigFocusItem"
      >
      </el-table-column>
      <el-table-column
        prop="bigFocusFrq"
        label="大集中频度"
        width="100"
        :formatter="SystemFrequency"
      >
      </el-table-column>
      <el-table-column
        prop="custRskPartVal"
        label="客户风险"
        width="200"
        class-name="custRskItem"
      >
      </el-table-column>
      <el-table-column
        prop="custRskFrq"
        label="客户风险频度"
        width="100"
        :formatter="SystemFrequency"
      >
      </el-table-column>
      <el-table-column
        prop="finBaseDataPartVal"
        label="金融基础数据管理系统"
        width="200"
        class-name="finBaseItem"
      >
      </el-table-column>
      <el-table-column
        prop="finBaseDataFrq"
        label="金融基础数据频度"
        width="100"
        :formatter="SystemFrequency"
      >
      </el-table-column>
    </el-table>
  </a-modal>
</template>
<script>
  import { Table, TableColumn } from 'element-ui'
  import { fetch, exportData } from '@/api/crossvalidation/cvchkresult/index'
  export default {
    name: 'TableList',
    components: {
      Table,
      TableColumn
    },
    data () {
      return {
        planName: '',
        labelCol: {
          xs: { span: 24 }, sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 }, sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        queryParam: {},
        modalTitle: '',
        data: []
      }
    },
    methods: {
      // 系统事件
      systemclick (column) {
        // 1104为基准对其余系统进行判断
        if (column.label === '1104') {
          const t1104List = document.querySelectorAll('.t1104')
          const eastList = document.querySelectorAll('.eastItem')
          const bigFocusList = document.querySelectorAll('.bigFocusItem')
          const custRskList = document.querySelectorAll('.custRskItem')
          const finBaseList = document.querySelectorAll('.finBaseItem')
          this.data.forEach((item, index) => {
            if (item.t1104PartVal) t1104List[index + 1].style.color = 'black'
            if (item.t1104PartVal !== item.eastPartVal) {
              eastList[index + 1].style.color = 'red'
            }
            if (item.t1104PartVal !== item.bigFocusPartVal) {
              bigFocusList[index + 1].style.color = 'red'
            }
            if (item.t1104PartVal !== item.custRskPartVal) {
              custRskList[index + 1].style.color = 'red'
            }
            if (item.t1104PartVal !== item.finBaseDataPartVal) {
              finBaseList[index + 1].style.color = 'red'
            }
          })
        }
        // EAST为基准对其余系统进行判断
        if (column.label === 'EAST') {
          const t1104List = document.querySelectorAll('.t1104')
          const eastList = document.querySelectorAll('.eastItem')
          const bigFocusList = document.querySelectorAll('.bigFocusItem')
          const custRskList = document.querySelectorAll('.custRskItem')
          const finBaseList = document.querySelectorAll('.finBaseItem')
          this.data.forEach((item, index) => {
            if (item.eastPartVal) eastList[index + 1].style.color = 'black'
            if (item.eastPartVal !== item.t1104PartVal) {
              t1104List[index + 1].style.color = 'red'
            }
            if (item.eastPartVal !== item.bigFocusPartVal) {
              bigFocusList[index + 1].style.color = 'red'
            }
            if (item.eastPartVal !== item.custRskPartVal) {
              custRskList[index + 1].style.color = 'red'
            }
            if (item.eastPartVal !== item.finBaseDataPartVal) {
              finBaseList[index + 1].style.color = 'red'
            }
          })
        }
        // 金融统计大集中为基准对其余系统进行判断
        if (column.label === '金融统计大集中') {
          const t1104List = document.querySelectorAll('.t1104')
          const eastList = document.querySelectorAll('.eastItem')
          const bigFocusList = document.querySelectorAll('.bigFocusItem')
          const custRskList = document.querySelectorAll('.custRskItem')
          const finBaseList = document.querySelectorAll('.finBaseItem')
          this.data.forEach((item, index) => {
            if (item.bigFocusPartVal) bigFocusList[index + 1].style.color = 'black'
            if (item.bigFocusPartVal !== item.t1104PartVal) {
              t1104List[index + 1].style.color = 'red'
            }
            if (item.bigFocusPartVal !== item.eastPartVal) {
              eastList[index + 1].style.color = 'red'
            }
            if (item.bigFocusPartVal !== item.custRskPartVal) {
              custRskList[index + 1].style.color = 'red'
            }
            if (item.bigFocusPartVal !== item.finBaseDataPartVal) {
              finBaseList[index + 1].style.color = 'red'
            }
          })
        }
        // 客户风险为基准对其余系统进行判断
        if (column.label === '客户风险') {
          const t1104List = document.querySelectorAll('.t1104')
          const eastList = document.querySelectorAll('.eastItem')
          const bigFocusList = document.querySelectorAll('.bigFocusItem')
          const custRskList = document.querySelectorAll('.custRskItem')
          const finBaseList = document.querySelectorAll('.finBaseItem')
          this.data.forEach((item, index) => {
            if (item.custRskPartVal) custRskList[index + 1].style.color = 'black'
            if (item.custRskPartVal !== item.t1104PartVal) {
              t1104List[index + 1].style.color = 'red'
            }
            if (item.custRskPartVal !== item.eastPartVal) {
              eastList[index + 1].style.color = 'red'
            }
            if (item.custRskPartVal !== item.bigFocusPartVal) {
              bigFocusList[index + 1].style.color = 'red'
            }
            if (item.custRskPartVal !== item.finBaseDataPartVal) {
              finBaseList[index + 1].style.color = 'red'
            }
          })
        }
        // 金融基础数据管理系统为基准对其余系统进行判断
        if (column.label === '金融基础数据管理系统') {
          const t1104List = document.querySelectorAll('.t1104')
          const eastList = document.querySelectorAll('.eastItem')
          const bigFocusList = document.querySelectorAll('.bigFocusItem')
          const custRskList = document.querySelectorAll('.custRskItem')
          const finBaseList = document.querySelectorAll('.finBaseItem')
          this.data.forEach((item, index) => {
            if (item.finBaseDataPartVal) finBaseList[index + 1].style.color = 'black'
            if (item.finBaseDataPartVal !== item.t1104PartVal) {
              t1104List[index + 1].style.color = 'red'
            }
            if (item.finBaseDataPartVal !== item.eastPartVal) {
              eastList[index + 1].style.color = 'red'
            }
            if (item.finBaseDataPartVal !== item.bigFocusPartVal) {
              bigFocusList[index + 1].style.color = 'red'
            }
            if (item.finBaseDataPartVal !== item.custRskPartVal) {
              custRskList[index + 1].style.color = 'red'
            }
          })
        }
      },
      // 系统频度文本判断
      SystemFrequency: function (row, column, text) {
        if (text === 'D') {
          return '日'
        }
        if (text === 'W') {
          return '周'
        }
        if (text === 'T') {
          return '旬'
        }
        if (text === 'M') {
          return '月'
        }
        if (text === 'Q') {
          return '季'
        }
        if (text === 'H') {
          return '半年'
        }
        if (text === 'Y') {
          return '年'
        }
        if (text === 'RHD') {
          return '人行日'
        }
      },
      select (record) {
        this.modalTitle = '校验结果展示'
        this.queryParam.planId = record.id
        this.queryParam.orgVal = record.orgVal
        this.queryParam.dataTime = record.dataTime
        this.planName = record.planName
        this.visible = true
        this.loadResultData()
      },
      loadResultData () {
        // 加载数据方法 必须为 Promise 对象
        fetch(this.queryParam).then(res => {
          if (res.code === 0) {
            this.data = res.data.records
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      handleOk () {
        this.loading = true
        exportData(this.queryParam).then(res => {
          if (!res) {
            this.$message.error('数据导出失败')
          } else {
            const content = res
            const blob = new Blob([content])
            const fileName = '指标校验结果_' + this.planName + '.xlsx'
            if ('download' in document.createElement('a')) {
              // 非IE下载
              const elink = document.createElement('a')
              elink.download = fileName
              elink.style.display = 'none'
              elink.href = URL.createObjectURL(blob)
              document.body.appendChild(elink)
              elink.click()
              URL.revokeObjectURL(elink.href) // 释放URL 对象
              document.body.removeChild(elink)
            } else {
              // IE10+下载
              navigator.msSaveBlob(blob, fileName)
            }
          }
        }).finally(() => {
          this.loading = false
        })
      },
      handleCancel () {
        this.visible = false
        this.confirmLoading = false
      }
    }
  }
</script>
<style lang="less">
.full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }
  .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: 100% ;
  }
  .ant-modal-body {
    flex: 1;
  }
}
  .mdl {
    border: solid 1px #bfc6cc;
    width: 300px;
    height: 40px;
  }

  .sql {
    border: solid 1px #bfc6cc;
    width: 300px;
    height: 100%;
  }

</style>
