<template>
    <div>
        <!--以下是从后端获取首页相关布局展示-->
        <layout-card v-if="visible" ref="layoutCard" :listItems="listItems"/>
        <!--以下是前端自定义显示内容-->

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
