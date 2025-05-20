<template>
    <div>
        <!--以下是从后端获取首页相关布局展示-->
        <layout-card v-if="visible" ref="layoutCard" :listItems="listItems"/>
        <!--以下是前端自定义显示内容-->
        <a-row :gutter="24">
            <!--柱状图-->
            <a-col :span="16">
                <bar :data="barData" :title="$t('dashboard.analysis.sales-trend')"/>
            </a-col>
            <!--饼状图-->
            <a-col :span="8">
                <a-card
                    class="antd-pro-pages-dashboard-analysis-salesCard"
                    :loading="loading"
                    :bordered="false"
                    :title="$t('dashboard.analysis.the-proportion-of-sales')"
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
                        <Sector :scale="pieScale" :data-source="pieData" :sectorStyle="pieStyle"></Sector>
                    </div>
                </a-card>
            </a-col>
        </a-row>
        <a-row :gutter="24" style="background: white">
            <!--TODO: 雷达图-->
            <a-col :span="8">
                <a-card
                    class="antd-pro-pages-dashboard-analysis-salesCard"
                    :loading="loading"
                    :bordered="false"
                    :title="$t('dashboard.analysis.the-proportion-of-sales')"
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
                    :title="$t('dashboard.analysis.online-top-search')"
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
        <a-row :gutter="24" style="background: white">
            <!--面积图-->
            <smooth-area :dataSource="searchUserData" :scale="searchUserScale"></smooth-area>
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
for (let i = 0; i < 12; i += 1) {
    barData.push({
        x: `${i + 1}月`,
        y: Math.floor(Math.random() * 1000) + 200
    })
}

const searchData = []
for (let i = 0; i < 50; i += 1) {
    searchData.push({
        index: i + 1,
        keyword: `搜索关键词-${i}`,
        count: Math.floor(Math.random() * 1000),
        range: Math.floor(Math.random() * 100),
        status: Math.floor((Math.random() * 10) % 2)
    })
}

const DataSet = require('@antv/data-set')

const sourceData = [
    { item: '家用电器', count: 32.2 },
    { item: '食用酒水', count: 21 },
    { item: '个护健康', count: 17 },
    { item: '服饰箱包', count: 13 },
    { item: '母婴产品', count: 9 },
    { item: '其他', count: 7.8 }
]

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
    name: 'Workplace',
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
                    title: this.$t('dashboard.analysis.table.rank'),
                    width: 90
                },
                {
                    dataIndex: 'keyword',
                    title: this.$t('dashboard.analysis.table.search-keyword')
                },
                {
                    dataIndex: 'count',
                    title: this.$t('dashboard.analysis.table.users')
                },
                {
                    dataIndex: 'range',
                    title: this.$t('dashboard.analysis.table.weekly-range'),
                    align: 'right',
                    sorter: (a, b) => a.range - b.range,
                    scopedSlots: { customRender: 'range' }
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

<style lang="less">
@import './Workplace.less';

</style>
