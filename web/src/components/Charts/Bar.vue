<template>
    <a-card
        class="antd-pro-pages-dashboard-analysis-salesCard"
        :loading="loading"
        :bordered="false"
        :title="title"
        :style="{ height: '100%' }"
    >
        <v-chart
            :height="height"
            :data="data"
            :forceFit="true"
            :padding="['auto', 'auto', '40', '50']">
            <v-tooltip/>
            <v-axis/>
            <v-bar position="x*y"/>
        </v-chart>
    </a-card>
</template>

<script>
export default {
    name: 'Bar',
    props: {
        title: {
            type: String,
            default: ''
        },
        data: {
            type: Array,
            default: () => {
                return []
            }
        },
        scale: {
            type: Array,
            default: () => {
                return [{
                    dataKey: 'x',
                    min: 2
                }, {
                    dataKey: 'y',
                    title: '时间',
                    min: 1,
                    max: 22
                }]
            }
        },
        tooltip: {
            type: Array,
            default: () => {
                return [
                    'x*y',
                    (x, y) => ({
                        name: x,
                        value: y
                    })
                ]
            }
        },
        height: {
            type: Number,
            default: 300
        },
        loading: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {}
    }
}
</script>
<style lang="less" scoped>

</style>
