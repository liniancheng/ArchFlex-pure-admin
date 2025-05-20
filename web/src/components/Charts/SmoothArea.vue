<template>
    <div>
        <v-chart :force-fit="true" :height="height" :data="dataSource" :scale="scale" :padding="[36, 0, 18, 0]">
            <v-tooltip/>
            <v-axis/>
            <v-smooth-line position="x*y" :size="2"/>
            <v-smooth-area position="x*y"/>
        </v-chart>
    </div>
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
