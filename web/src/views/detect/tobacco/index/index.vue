<template>
  <div id="box">
    <div id="left">
      <a-card title="指标层级" :headStyle="indBack">
        <div class="but">
          <a-popconfirm title="确定要删除?" @confirm="() => handleDelete()">
            <a-button>
              <a-icon type="rest" style="color: red"/>
              删除
            </a-button>
          </a-popconfirm>
          <a-button>
            <a-icon type="login" style="color: #0097f4"/>
            导入
          </a-button>
          <a-button>
            <a-icon type="logout"/>
            导出
          </a-button>
        </div>
        <div style="margin-top: -10px">
          <a-button @click="treeunfold">
            展开/收起
          </a-button>
          <a-input-search style="width:153px" placeholder="请输入指标名称" v-model="searchValue" @search="onSearch"/>
          <a-tree
            v-if="data.length"
            :defaultExpandAll="up"
            :replaceFields="{ children: 'children', title: 'title', key: 'key' }"
            :blockNode="true"
            :selectable="false"
            :tree-data="data"
            @select="tab"
            show-icon
            :icon="getIcon"
          >
          <!--  -->
          </a-tree>
        </div>
      </a-card>
    </div>
    <div id="right" style="border-top: 1px solid #b5b9a9">
      <div id="definition"><h3>指标定义</h3></div>
      <div>
        <a-button @click="handleAddParent">
          <a-icon type="plus"/>
          添加一级指标
        </a-button>
      </div>
      <div>
        <a-table
          rowKey="indNo"
          :columns="columns"
          :data-source="dataSource"
          size="default"
          :scroll="{ x: 2500, y: 399 }"
          :pagination="{ showTotal: total => `共有${total} 条数据`,
                         defaultPageSize:10,
                         showSizeChanger:true,
                         pageSizeOptions: ['10', '30', '50'],
                         onShowSizeChange:(current, pageSize)=>this.pageSize = pageSize
          }"
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a class="table_blue" @click="$refs.dataForm.add(record)">【<a-icon type="file-add"/>】</a>
              <a class="table_blue" @click="handleEdit(record.indNo)">【<a-icon type="tool"/>】</a>
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
        </a-table>
      </div>
      <data-form ref="dataForm" @ok="handleOk"/>
      <data-mend ref="mend" @ok="handleOk"/>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { treeNode, fetchByIndNo, fetchOne, removeById, treeSearch } from '@/api/crossvalidation/cvchkind/index'
  import DataForm from './modules/DataForm'
  import DataMend from './modules/DataMend'

  export default {
    name: 'Cvchkind',
    components: {
      DataForm,
      DataMend
    },
    data () {
      return {
        searchValue: '',
        // tree展开
        up: false,
        fetchOrRemove: false,
        indNo: '',
        showIcon: true,
        data: [],
        indBack: { background: '#0097f4' },
        // 列设置
        settingColumns: [],
        // 表头
        columns: [],
        dataSource: [],
        // 列定义
        defColumns: [
          // columns: [
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
            key: '1',
            title: '指标编号',
            width: 200,
            dataIndex: 'indNo',
            // 超出宽度变成省略号
            ellipsis: true
          },
          {
            title: '指标名称',
            dataIndex: 'indNm',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true
          },
          {
            title: '指标体系',
            dataIndex: 'indSys',
            // 超出宽度变成省略号
            ellipsis: true,
            width: 200
          },
          {
            title: '指标层级',
            width: 200,
            dataIndex: 'indLevel',
            ellipsis: true
          },
          {
            title: '指标释义',
            dataIndex: 'indExpr',
            width: 200,
            ellipsis: true
          },
          {
            title: '参考指标',
            dataIndex: 'busDirec',
            width: 200,
            // 超出宽度变成省略号
            ellipsis: true
          },
          {
            title: '触警级别',
            dataIndex: 'alarmLevel',
            width: 200,
            ellipsis: true
          },
          {
            title: '业务归口部门',
            dataIndex: 'deptNo',
            width: 200,
            ellipsis: true
          },
          {
            title: '币种',
            dataIndex: 'currencyVal',
            width: 200,
            ellipsis: true
          },
          {
            title: '频度',
            dataIndex: 'dateVal',
            width: 200,
            ellipsis: true
          },
          {
            title: '新增/编辑',
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
        ]
      }
    },
    created () {
      this.initColumns()
      this.loadData()
    },
    mounted () {
      this.hierarchytitle()
    },
    methods: {
      // tree图标
       getIcon (props) {
            const { type } = props
            if (type === null) {
                return <svg t="1656646759612" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8913" width="32" height="32"><path d="M835.3 240.8H606.9c0-52.5-42.5-95-95-95H227c-52.4 0-95 42.5-95 95v455h760V297.4c0-31.3-25.4-56.6-56.7-56.6z" fill="#FFC41F" p-id="8914"></path><path d="M227 531.2V321.7c0-20.8 16.9-37.7 37.7-37.7h494.6c20.8 0 37.7 16.9 37.7 37.7v209.5c0 20.8-16.9 37.7-37.7 37.7H264.7c-20.9 0-37.7-16.9-37.7-37.7z" fill="#FFFFFF" p-id="8915"></path><path d="M891.9 755.9v-369c0-26.2-21.3-47.5-47.5-47.5H179.5c-26.2 0-47.5 21.3-47.5 47.5v368.8c0 32.3 26.2 58.6 58.6 58.6h642.8c32.3 0.2 58.5-26.1 58.5-58.4z" fill="#FFC41F" p-id="8916"></path><path d="M527.8 459.9l24.6 49.9c2.6 5.3 7.6 8.9 13.4 9.7l55 7.9c14.6 2.1 20.4 20 9.8 30.4l-39.9 38.7c-4.2 4.1-6.1 9.9-5.1 15.7L595 667c2.5 14.6-12.8 25.5-25.8 18.8L520 659.9c-5.2-2.7-11.4-2.7-16.6 0l-49.2 25.9c-13 6.9-28.2-4.2-25.8-18.8l9.4-54.8c0.9-5.8-0.9-11.6-5.1-15.7l-39.6-38.6c-10.5-10.3-4.7-28.2 9.8-30.4l55-7.9c5.8-0.9 10.8-4.5 13.4-9.7l24.6-49.9c6.6-13.4 25.4-13.4 31.9-0.1z" fill="#FFFFFF" p-id="8917"></path></svg>
            }
        },
      // 指标树展开/收起
      treeunfold () {
        this.up = !this.up
        this.loadData()
      },
      // 指标层级背景颜色
      hierarchytitle () {
        var elem = document.getElementById('definition')
        var bgc = getComputedStyle(elem).backgroundColor
        if (this.indBack.background !== bgc) {
          this.indBack.background = bgc
        }
      },
      // 删除
      handleDelete () {
        this.fetchOrRemove = true
        if (this.indNo === '') {
          this.$message.error('请选择要删除的指标！')
          this.fetchOrRemove = false
          return
        }
        this.tab(this.indNo)
        this.fetchOrRemove = false
      },
      // 添加一级
      handleAddParent () {
        this.$refs.dataForm.addOne()
      },
      // 修改
      handleEdit (id) {
        fetchOne(id).then((res) => {
          if (res.code === 0) {
            this.$refs.mend.edit(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 列表请求
      tab (indNo) {
        if (indNo.length === 1) {
          this.indNo = indNo
        } else {
          indNo = this.indNo
          this.indNo = ''
        }
        if (!this.fetchOrRemove) {
          fetchByIndNo(indNo).then((res) => {
            if (res.code === 0) {
              this.dataSource = res.data.records
            } else {
              this.$message.error(res.message)
            }
          })
        } else {
          removeById(indNo).then((res) => {
            if (res.data) {
              this.$message.success(res.message)
              this.handleOk()
            } else {
              this.$message.error(res.message)
            }
          })
        }
      },
      // 搜索
      onSearch () {
        this.up = true
        if (this.searchValue.replaceAll(' ', '').length === 0) {
          this.loadData()
          return
        }
        this.data = []
        treeSearch(this.searchValue).then((res) => {
          if (res.code === 0) {
            this.data = this.exchangeNodes(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // tree请求
      loadData () {
        this.data = []
        treeNode().then((res) => {
          if (res.code === 0) {
            this.data = this.exchangeNodes(res.data)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      exchangeNodes (nodes) {
        if (nodes != null) {
          if (nodes && nodes.length > 0) {
            return nodes.map((item) => {
              const node = { ...item, scopedSlots: { title: 'indNm' }, children: this.exchangeNodes(item.children) }
              return node
            })
          }
        } else {
          return null
        }
      },
      handleOk () {
        this.indNo = ''
        this.loadData()
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
    }
  }
</script>

<style scoped>
.icon{
  width:25px;
  height: 24px;
  margin-right: 5px;
}
  #box {
    width: 100%;
    height: 570px;
    position: relative;
    overflow: hidden;
  }

  #left {
    width: calc(25% - 5px);
    height: 100%;
    float: left;
    overflow: auto;
  }

  #resize {
    position: relative;
    width: 5px;
    height: 100%;
    cursor: w-resize;
    float: left;
  }

  #right {
    width: 75%;
    height: 100%;
    float: left;
    overflow: hidden;
    padding-right: 10px;
  }

  .but {
    height: 40px;
    margin-top: -23px;
    display: flex;
  }

  #definition {
    width: 100%;
    height: 58px;
    background-color: #1890ff;
    line-height: 58px;
    padding-left: 30px;
  }
</style>
