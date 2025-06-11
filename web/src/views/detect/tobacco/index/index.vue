<template>
    <div>
        <!--以下是从后端获取首页相关布局展示-->
        <layout-card v-if="visible" ref="layoutCard" :listItems="listItems"/>
        <!--以下是前端自定义显示内容-->

        <a-row :gutter="12" class="tobacco-index__charts-row">
            <!--柱状图-->
            <a-col :span="16">
                <bar :data="barData" :title="$t('detect.tobacco.class')" :loading="loading"/>
            </a-col>
            <!--饼状图-->
            <a-col :span="8">
                <sector :scale="pieScale" :data-source="pieData" :sectorStyle="pieStyle" :loading="loading"></sector>
            </a-col>
        </a-row>
        <a-row :gutter="12" class="tobacco-index__charts-row">
            <!--TODO: 雷达图-->
            <a-col :span="8">
                <a-card
                    class="antd-pro-pages-dashboard-analysis-salesCard"
                    :loading="loading"
                    :bordered="false"
                    :title="$t('detect.tobacco.confidence')"
                    :style="{ height: '100%' }"
                >
                    <div slot="extra" style="height: inherit;">
                        <span class="dashboard-analysis-iconGroup">
                            <a-dropdown :trigger="['click']" placement="bottomLeft">
                                <a-icon type="ellipsis" class="ant-dropdown-link"/>
                                <a-menu slot="overlay">
                                    <a-menu-item>
                                        <a href="javascript:">{{ $t('dashboard.analysis.dropdown-option-one') }}</a>
                                    </a-menu-item>
                                    <a-menu-item>
                                        <a href="javascript:">{{ $t('dashboard.analysis.dropdown-option-two') }}</a>
                                    </a-menu-item>
                                </a-menu>
                            </a-dropdown>
                        </span>
                        <div class="analysis-salesTypeRadio">
                            <a-radio-group defaultValue="a">
                                <a-radio-button value="a">{{ $t('dashboard.analysis.channel.all') }}</a-radio-button>
                                <a-radio-button value="b">{{ $t('dashboard.analysis.channel.online') }}</a-radio-button>
                                <a-radio-button value="c">{{ $t('dashboard.analysis.channel.stores') }}</a-radio-button>
                            </a-radio-group>
                        </div>
                    </div>
                    <h4>{{ $t('dashboard.analysis.sales') }}</h4>
                    <div>
                        <div>
                            <v-chart :force-fit="true" :height="405" :data="pieData" :scale="pieScale">
                                <v-tooltip :showTitle="false" dataKey="item*percent"/>
                                <v-axis/>
                                <v-legend dataKey="item"/>
                                <v-pie position="percent" color="item" :vStyle="pieStyle"/>
                                <v-coord type="theta" :radius="0.75" :innerRadius="0.6"/>
                            </v-chart>
                        </div>
                    </div>
                </a-card>
            </a-col>
            <!--表格-->
            <a-col :span="16">
                <a-card
                    :loading="loading"
                    :bordered="false"
                    :title="$t('detect.tobacco.real')"
                    :style="{ height: '100%' }"
                >
                    <a-dropdown :trigger="['click']" placement="bottomLeft" slot="extra">
                        <a class="ant-dropdown-link" href="#">
                            <a-icon type="ellipsis"/>
                        </a>
                        <a-menu slot="overlay">
                            <a-menu-item>
                                <a href="javascript:;">{{ $t('dashboard.analysis.dropdown-option-one') }}</a>
                            </a-menu-item>
                            <a-menu-item>
                                <a href="javascript:;">{{ $t('dashboard.analysis.dropdown-option-two') }}</a>
                            </a-menu-item>
                        </a-menu>
                    </a-dropdown>
                    <div class="ant-table-wrapper">
                        <a-table
                            row-key="index"
                            size="large"
                            :columns="searchTableColumns"
                            :dataSource="searchData"
                            :pagination="{ pageSize: 5 }"
                        >
                            <span slot="range" slot-scope="text, record">
                              <trend :flag="record.status === 0 ? 'up' : 'down'">
                                  {{ text }}%
                              </trend>
                            </span>
                        </a-table>
                    </div>
                </a-card>
            </a-col>
        </a-row>
        <a-row :gutter="12" class="tobacco-index__charts-row">
            <!--面积图-->
            <smooth-area
                :dataSource="searchUserData"
                :scale="searchUserScale"
                :title="$t('detect.tobacco.ten')"
                :loading="loading"
            ></smooth-area>
        </a-row>

    </div>
</template>

<script>
import LayoutCard from '@/views/admin/system/layout/bus/modules/LayoutCard'
import events from '@/utils/events'
import { mapActions } from 'vuex'
import { ChartCard, Bar, SmoothArea, Sector, NumberInfo, Trend } from '@/components'
import moment from 'moment/moment'

const barData = []
const tobaccoWorms = ['烟草甲', '蚜虫', '线虫', '烟草粉螟']
for (let i = 0; i < 4; i += 1) {
    barData.push({
        x: tobaccoWorms[i],
        y: Math.floor(Math.random() * 1000) + 200
    })
}

const DataSet = require('@antv/data-set')

const sourceData = [
    { item: '用户1', count: 32.2 },
    { item: '用户2', count: 21 },
    { item: '用户3', count: 17 },
    { item: '用户4', count: 13 },
    { item: '用户5', count: 9 },
    { item: 'admin', count: 7.8 }
]

const searchData = []
for (let i = 0; i < sourceData.length; i += 1) {
    // 随机生成时间
    const randomDate = new Date(
        Date.now() - Math.random() * (Date.now() - new Date(2025, 0, 1).getTime())
    )

    // 直接将 Date 对象转换为字符串
    const time = randomDate.toISOString().replace('T', ' ').substring(0, 19)
    searchData.push({
        index: i + 1,
        keyword: sourceData[i].item,
        count: Math.floor(Math.random() * 1000),
        range: Math.floor(Math.random() * 100),
        status: Math.floor((Math.random() * 10) % 2),
        AI: '不使用AI',
        time: time
    })
}

const pieScale = [{
    dataKey: 'percent',
    min: 0,
    formatter: '.0%'
}]

const dv = new DataSet.View().source(sourceData)
dv.transform({
    type: 'percent',
    field: 'count',
    dimension: 'item',
    as: 'percent'
})
const pieData = dv.rows

const searchUserData = []
for (let i = 0; i < 7; i++) {
    searchUserData.push({
        x: moment().add(i, 'days').format('YYYY-MM-DD'),
        y: Math.ceil(Math.random() * 10)
    })
}
const searchUserScale = [
    {
        dataKey: 'x',
        alias: '时间'
    },
    {
        dataKey: 'y',
        alias: '用户数',
        min: 0,
        max: 10
    }
]

export default {
    name: 'Tobacco',
    components: {
        Sector,
        Trend,
        SmoothArea,
        NumberInfo,
        Bar,
        ChartCard,
        LayoutCard
    },
    data() {
        return {
            loading: true,
            visible: false,
            listItems: [],
            barData,
            searchData,
            pieScale,
            pieData,
            sourceData,
            searchUserData,
            searchUserScale,
            pieStyle: {
                stroke: '#fff',
                lineWidth: 1
            }
        }
    },
    mounted() {
        this.getHomePage()
    },
    computed: {
        searchTableColumns() {
            return [
                {
                    dataIndex: 'index',
                    title: this.$t('detect.tobacco.table.rank'),
                    width: 90
                },
                {
                    dataIndex: 'keyword',
                    title: this.$t('detect.tobacco.table.user')
                },
                {
                    dataIndex: 'count',
                    title: this.$t('detect.tobacco.table.weight')
                },
                {
                    dataIndex: 'range',
                    title: this.$t('detect.tobacco.table.confidence'),
                    align: 'right',
                    sorter: (a, b) => a.range - b.range,
                    scopedSlots: { customRender: 'range' }
                },
                {
                    dataIndex: 'AI',
                    title: this.$t('detect.tobacco.table.AI')
                },
                {
                    dataIndex: 'time',
                    title: this.$t('detect.tobacco.table.time')
                }
            ]
        }
    },
    methods: {
        ...mapActions(['GetHomePage']),
        // 获取首页信息
        getHomePage() {
            const { GetHomePage } = this
            GetHomePage().then(res => {
                if (res.code === 0) {
                    events.$emit('OnMessageOrTaskChange', {
                        existNewMessage: res.data.existUnReadMessage,
                        existNewTask: res.data.existUnProcessTask
                    })
                    this.listItems = res.data.layout.listItems.map(item => {
                        return {
                            i: item.itemId,
                            x: item.relX,
                            y: item.relY,
                            w: item.relW,
                            h: item.relH,
                            layId: item.layId,
                            itemId: item.itemId,
                            itemName: item.itemName,
                            attach: item
                        }
                    })
                    this.$nextTick(() => {
                        this.visible = true
                        this.loading = false
                    })
                }
            }).finally(() => {
            })
        }

    }
}
</script>

<style lang="less" scoped>
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;

  .extra-item {
    display: inline-block;
    margin-right: 24px;

    a {
      margin-left: 24px;
    }
  }
}

.antd-pro-pages-dashboard-analysis-twoColLayout {
  position: relative;
  display: flex;
  flex-flow: row wrap;
}

.antd-pro-pages-dashboard-analysis-salesCard {
  height: calc(100% - 24px);
  :deep(.ant-card-head) {
    position: relative;
  }
}

.dashboard-analysis-iconGroup {
  i {
    margin-left: 16px;
    color: rgba(0,0,0,.45);
    cursor: pointer;
    transition: color .32s;
    color: black;
  }
}
.analysis-salesTypeRadio {
  position: absolute;
  right: 54px;
  bottom: 12px;
}

.tobacco-index__charts-row {
    margin-bottom: 12px;
}
</style>
