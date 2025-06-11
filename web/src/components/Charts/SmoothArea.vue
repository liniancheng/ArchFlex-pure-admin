<template>
    <a-card
        class="antd-pro-pages-dashboard-analysis-salesCard"
        :loading="loading"
        :bordered="false"
        :title="title"
        :style="{ height: '100%' }"
    >
        <v-chart :force-fit="true" :height="height" :data="dataSource" :scale="scale" :padding="['auto', 'auto', 'auto', 'auto']">
            <v-tooltip/>
            <v-axis/>
            <v-smooth-line position="x*y" :size="2"/>
            <v-smooth-area position="x*y"/>
        </v-chart>
    </a-card>
</template>

<script>
export default {
    name: 'SmoothArea',
    props: {
        prefixCls: {
            type: String,
            default: 'ant-pro-smooth-area'
        },
        scale: {
            type: [Object, Array],
            required: true
        },
        dataSource: {
            type: Array,
            required: true
        },
        height: {
            type: Number,
            default: 300
        },
        title: {
            type: String,
            default: ''
        },
        loading: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {

        }
    }
}
</script>

<style lang="less" scoped>
</style>
