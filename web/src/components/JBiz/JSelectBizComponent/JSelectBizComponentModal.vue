<template>
  <a-modal
    centered
    :title="'选择 - '+name"
    :width="width"
    :visible="visible"
    @ok="handleOk"
    @cancel="close"
    cancelText="关闭"
  >
    <a-row :gutter="18">
      <a-col :span="16">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :span="14">
                <a-form-item :label="queryParamText || name">
                  <a-input
                    v-model="queryParam[queryParamCode || valueKey]"
                    :placeholder="'请输入' + (queryParamText || name)"
                    @pressEnter="searchQuery"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                  <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <a-table
          size="small"
          bordered
          :rowKey="rowKey"
          :columns="innerColumns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :scroll="{ y: 240 }"
          :rowSelection="{ selectedRowKeys, onChange: onSelectChange, type: multiple ? 'checkbox' : 'radio' }"
          :customRow="customRowFn"
          @change="handleTableChange"
        >
        </a-table>
      </a-col>
      <a-col :span="8">
        <a-alert type="info" :showIcon="true">
          <div slot="message" style="height:18px">
            已选{{ name }}数: {{ selectedRowKeys.length }}
          </div>
        </a-alert>
        <br>
        <a-card :bordered="false" :head-style="{ padding: 0 }" :body-style="{ padding: 0 }">
          <a-table size="small" :rowKey="rowKey" bordered v-bind="selectedTable">
            <span slot="action" slot-scope="text, record, index">
              <a @click="handleDeleteSelected(record, index)" class="table_orange">【删除】</a>
            </span>
          </a-table>

        </a-card>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
import { getAction } from '@/api/admin/manage'
import Ellipsis from '@/components/Ellipsis'
export default {
  name: 'JSelectBizComponentModal',
  components: { Ellipsis },
  props: {
    value: {
      type: Array,
      default: () => []
    },
    visible: {
      type: Boolean,
      default: false
    },
    valueKey: {
      type: String,
      required: true
    },
    multiple: {
      type: Boolean,
      default: true
    },
    width: {
      type: Number,
      default: 800
    },

    name: {
      type: String,
      default: ''
    },
    listUrl: {
      type: String,
      required: true,
      default: ''
    },
    // 根据 value 获取显示文本的地址，例如存的是 username，可以通过该地址获取到 realname
    valueUrl: {
      type: String,
      default: ''
    },
    displayKey: {
      type: String,
      default: null
    },
    columns: {
      type: Array,
      required: true,
      default: () => []
    },
    // 查询条件Code
    queryParamCode: {
      type: String,
      default: null
    },
    // 查询条件文字
    queryParamText: {
      type: String,
      default: null
    },
    rowKey: {
      type: String,
      default: ''
    },
    // 过长裁剪长度，设置为 -1 代表不裁剪
    ellipsisLength: {
      type: Number,
      default: 12
    }
  },
  data () {
    return {
      selectedRowKeys: [],
      selectionRows: [],
      queryParam: {},
      dataSource: [],
      innerValue: [],
      // 已选择列表
      selectedTable: {
        pagination: false,
        scroll: { y: 240 },
        columns: [
          {
            ...this.columns[0],
            width: this.columns[0].widthRight || this.columns[0].width
          },
          { title: '操作', dataIndex: 'action', align: 'center', width: 60, scopedSlots: { customRender: 'action' } }
        ],
        dataSource: []
      },
      renderEllipsis: value => <ellipsis length={this.ellipsisLength}>{value}</ellipsis>,
      url: { list: this.listUrl },
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '20', '30'],
        showTotal: (total, range) => {
          // return ' 共' + total + '条数据'
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      loading: false,
      options: [],
      dataSourceMap: {}
    }
  },
  computed: {
    // 表头
    innerColumns () {
      const columns = this.cloneObject(this.columns)
      columns.forEach(column => {
        // 给所有的列加上过长裁剪
        if (this.ellipsisLength !== -1) {
          column.customRender = text => this.renderEllipsis(text)
        }
      })
      return columns
    }
  },
  watch: {
    value: {
      deep: true,
      immediate: true,
      handler (val) {
        this.innerValue = this.cloneObject(val)
        this.selectedRowKeys = []
        this.valueWatchHandler(val)
        this.queryOptionsByValue(val)
      }
    },
    dataSource: {
      deep: true,
      handler (val) {
        this.emitOptions(val)
        this.valueWatchHandler(this.innerValue)
      }
    },
    selectedRowKeys: {
      immediate: true,
      deep: true,
      handler (val) {
        this.selectedTable.dataSource = val.map(key => {
          for (const data of this.dataSource) {
            if (data[this.rowKey] === key) {
              this.pushIfNotExist(this.innerValue, data[this.valueKey])
              return data
            }
          }
          for (const data of this.selectedTable.dataSource) {
            if (data[this.rowKey] === key) {
              this.pushIfNotExist(this.innerValue, data[this.valueKey])
              return data
            }
          }
          console.warn('未找到选择的行信息，key：' + key)
          return {}
        })
      }
    }
  },

  mounted () {
    this.loadData(1)
  },
  methods: {
    searchQuery () {
      this.loadData(1)
    },
    searchReset () {
      this.queryParam = {}
      this.loadData(1)
    },
    loadData (arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      // 加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() // 查询条件
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.code === 0) {
          this.dataSource = res.data.records
          this.ipagination.total = res.data.total
        } else {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    /** 关闭弹窗 */
    close () {
      this.$emit('update:visible', false)
    },
    onSelectChange (selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    onClearSelected () {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    handleTableChange (pagination, filters, sorter) {
      // 分页、排序、筛选变化时触发
      // TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = sorter.order === 'ascend' ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
    },
    getQueryParams () {
      // 获取查询条件
      const sqp = {}
      if (this.superQueryParams) {
        sqp['superQueryParams'] = encodeURI(this.superQueryParams)
        sqp['superQueryMatchType'] = this.superQueryMatchType
      }
      var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters)
      param.field = this.getQueryField()
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      return this.filterObj(param)
    },
    getQueryField () {
      // TODO 字段权限控制
      var str = 'id,'
      this.columns.forEach(function (value) {
        str += ',' + value.dataIndex
      })
      return str
    },
    valueWatchHandler (val) {
      val.forEach(item => {
        this.dataSource.concat(this.selectedTable.dataSource).forEach(data => {
          if (data[this.valueKey] === item) {
            this.pushIfNotExist(this.selectedRowKeys, data[this.rowKey])
          }
        })
      })
    },

    queryOptionsByValue (value) {
      if (!value || value.length === 0) {
        return
      }
      // 判断options是否存在value，如果已存在数据就不再请求后台了
      let notExist = false
      for (const val of value) {
        let find = false
        for (const option of this.options) {
          if (val === option.value) {
            find = true
            break
          }
        }
        if (!find) {
          notExist = true
          break
        }
      }
      if (!notExist) return
      getAction(this.valueUrl || this.listUrl, {
        // 这里最后加一个 , 的原因是因为无论如何都要使用 in 查询，防止后台进行了模糊匹配，导致查询结果不准确
        [this.valueKey]: value.join(',') + ',',
        pageNo: 1,
        pageSize: value.length
      }).then(res => {
        if (res.success) {
          let dataSource = res.result
          if (!(dataSource instanceof Array)) {
            dataSource = res.result.records
          }
          this.emitOptions(dataSource, data => {
            this.pushIfNotExist(this.innerValue, data[this.valueKey])
            this.pushIfNotExist(this.selectedRowKeys, data[this.rowKey])
            this.pushIfNotExist(this.selectedTable.dataSource, data, this.rowKey)
          })
        }
      })
    },

    emitOptions (dataSource, callback) {
      dataSource.forEach(data => {
        const key = data[this.valueKey]
        this.dataSourceMap[key] = data
        this.pushIfNotExist(this.options, { label: data[this.displayKey || this.valueKey], value: key }, 'value')
        // eslint-disable-next-line no-unused-expressions
        typeof callback === 'function' ? callback(data) : ''
      })
      this.$emit('options', this.options, this.dataSourceMap)
    },

    /** 完成选择 */
    handleOk () {
      const value = this.selectedTable.dataSource.map(data => data[this.valueKey])
      this.$emit('input', value)
      this.close()
    },

    /** 删除已选择的 */
    handleDeleteSelected (record, index) {
      this.selectedRowKeys.splice(this.selectedRowKeys.indexOf(record[this.rowKey]), 1)
      this.selectedTable.dataSource.splice(index, 1)
    },

    customRowFn (record) {
      return {
        on: {
          click: () => {
            const key = record[this.rowKey]
            if (!this.multiple) {
              this.selectedRowKeys = [key]
              this.selectedTable.dataSource = [record]
            } else {
              const index = this.selectedRowKeys.indexOf(key)
              if (index === -1) {
                this.selectedRowKeys.push(key)
                this.selectedTable.dataSource.push(record)
              } else {
                this.handleDeleteSelected(record, index)
              }
            }
          }
        }
      }
    },
    /**
     * 如果值不存在就 push 进数组，反之不处理
     * @param array 要操作的数据
     * @param value 要添加的值
     * @param key 可空，如果比较的是对象，可能存在地址不一样但值实际上是一样的情况，可以传此字段判断对象中唯一的字段，例如 id。不传则直接比较实际值
     * @returns {boolean} 成功 push 返回 true，不处理返回 false
     */
    pushIfNotExist (array, value, key) {
      for (const item of array) {
        if (key && item[key] === value[key]) {
          return false
        } else if (item === value) {
          return false
        }
      }
      array.push(value)
      return true
    },
    /**
     * 深度克隆对象、数组
     * @param obj 被克隆的对象
     * @return 克隆后的对象
     */
    cloneObject (obj) {
      return JSON.parse(JSON.stringify(obj))
    },
    filterObj (obj) {
      if (!(typeof obj === 'object')) {
        return
      }

      for (const key in obj) {
        if (obj.hasOwnProperty(key) && (obj[key] == null || obj[key] === undefined || obj[key] === '')) {
          delete obj[key]
        }
      }
      return obj
    }
  }
}
</script>
<style lang="less" scoped>
.person_title {
    margin-bottom: 40px;
    margin-top: 15px;
    float: left;
    width: 100%;
    img {
      width: 4px;
      height: 21px;
      float: left;
      margin-right: 15px;
    }
    h2 {
      height: 20px;
      font-size: 20px;
      font-weight: bold;
      color: rgba(51, 51, 51, 1);
      line-height: 17px;
      float: left;
    }
  }
</style>
