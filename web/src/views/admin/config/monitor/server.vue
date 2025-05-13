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
        rowKey="sys.computerIp"
        :pagination="false"
        :columns="columns"
        :data-source="data"
        :loading="loading"
      >
        <span slot="cpu" slot-scope="text, record">
          <template>
            <span>核数：{{ record.cpu.cpuNum }}</span><br/>
            <span v-if="record.cpu.used/record.cpu.total*100>=90" style="color:red;">已用：{{ (record.cpu.used/record.cpu.total*100).toFixed(2) }}%</span>
            <span v-if="record.cpu.used/record.cpu.total*100<90" style="color:green;">已用：{{ (record.cpu.used/record.cpu.total*100).toFixed(2) }}%</span>
          </template>
        </span>
        <span slot="mem" slot-scope="text, record">
          <template>
            <span>总数：{{ (record.mem.total/1024/1024/1024).toFixed(0) }}G</span><br/>
            <span v-if="record.mem.used/record.mem.total*100>=90" style="color:red;">已用：{{ (record.mem.used/record.mem.total*100).toFixed(2) }}%</span>
            <span v-if="record.mem.used/record.mem.total*100<90" style="color:green;">已用：{{ (record.mem.used/record.mem.total*100).toFixed(2) }}%</span>
          </template>
        </span>
        <span slot="files" slot-scope="text, record">
          <template>
            <p v-for="item in record.sysFiles" :key="item.dirName">
              磁盘：{{ item.dirName }}&nbsp;&nbsp;&nbsp;&nbsp;{{ item.total }}
              <span v-if="item.usage>=90" style="color:red;">&nbsp;&nbsp;已用：{{ item.usage }}%</span>
              <span v-if="item.usage<90" style="color:green;">&nbsp;&nbsp;已用：{{ item.usage }}%</span>
            </p>
          </template>
        </span>
      </a-table>
    </a-card>
  </div>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import { servers, clearServer } from '@/api/config/monitor/index'
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
        { title: '主机名', dataIndex: 'sys.computerName', key: 'computerName', width: 120 },
        { title: 'IP地址', dataIndex: 'sys.computerIp', key: 'computerIp', width: 120 },
        { title: '操作系统', dataIndex: 'sys.osName', key: 'osName', width: 120 },
        { title: 'CPU', dataIndex: 'cpu.cpuNum', key: 'cpu', width: 100, scopedSlots: { customRender: 'cpu' } },
        { title: '内存', dataIndex: 'mem.total', key: 'mem', width: 100, scopedSlots: { customRender: 'mem' } },
        { title: '存储', dataIndex: 'row.sysFiles', key: 'files', width: 200, scopedSlots: { customRender: 'files' } }
      ]
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      this.loading = true
      servers(this.queryParam).then(res => {
            this.data = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    clearData () {
      clearServer().then(res => {
            this.loadData()
        })
    }
  }
}
</script>
