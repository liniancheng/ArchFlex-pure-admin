<template>
  <div>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" icon="reload" @click="loadData">刷新</a-button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a-button type="primary" icon="close" @click="clearData">清空</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <a-table
        ref="table"
        size="default"
        rowKey="serviceName"
        :pagination="false"
        :columns="columns"
        :data-source="data"
        :loading="loading"
      >
        <span slot="health" slot-scope="text, record">
          <template>
            <span v-if="!record.health" style="color:red;">异常</span>
            <span v-if="record.health" style="color:green;">正常</span>
          </template>
        </span>
      </a-table>
    </a-card>
  </div>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { services, clearService } from '@/api/config/monitor/index'
export default {
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      data: [],
      queryParam: {},
      loading: false,
      columns: [
        { title: '服务', dataIndex: 'serviceName', key: 'serviceName', width: 200 },
        { title: 'IP地址', dataIndex: 'sys.computerIp', key: 'computerIp', width: 120 },
        { title: '状态', dataIndex: 'health', key: 'health', width: 120, scopedSlots: { customRender: 'health' } },
        { title: '最后活动时间', dataIndex: 'createTimeStr', key: 'createTimeStr', width: 100 }
      ]
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      this.loading = true
      services(this.queryParam).then(res => {
            this.data = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    clearData () {
      clearService().then(res => {
            this.loadData()
        })
    }
  }
}
</script>
