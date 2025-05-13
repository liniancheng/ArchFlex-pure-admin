<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <!-- <a-col :md="8" :sm="24">
              <a-form-item label="登录名">
                <a-input v-model="queryParam.loginName" allowClear placeholder="登录名"/>
              </a-form-item>
            </a-col> -->
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" icon="reload" @click="$refs.table.refresh(true)">刷新</a-button>
                <!-- <a-button style="margin-left: 8px" icon="reload" @click="() => queryParam = {}">重置</a-button> -->
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="access_token"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.access_token)">
              <a class="table_orange">【删除】</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>
    </a-card>
  </div>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { fetch, deleteToken } from '@/api/config/online/index'
export default {
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      queryParam: {},
      columns: [
        { title: '用户名', dataIndex: 'loginName', key: 'loginName', width: 150 },
        { title: '令牌', dataIndex: 'access_token', key: 'access_token', ellipsis: true },
        { title: '范围', dataIndex: 'scope', width: 100, key: 'scope' },
        { title: '过期时间', dataIndex: 'expires_in', key: 'expires_in', width: 100 },
        { title: '操作', dataIndex: 'action', width: 150, align: 'center', scopedSlots: { customRender: 'action' } }
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
      }
    }
  },
  methods: {
    handleDelete (id) {
      deleteToken(id).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
          if (sessionStorage.getItem('access_token') !== id) {
            this.handleOk()
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleOk () {
      this.$refs.table.refresh()
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
