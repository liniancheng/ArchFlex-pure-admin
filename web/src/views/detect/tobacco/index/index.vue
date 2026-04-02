<template>
    <div>
        <!--TODO: 以下是从后端获取首页相关布局展示-->
        <!--<div class="flex items-center justify-center w-full">-->
        <!--    努力开发中...<br>-->
        <!--    (爽歪的麻雀)-->
        <!--</div>-->

        <!--TODO: 以下是前端自定义显示内容-->
        <el-row :gutter="12" class="tobacco-index_charts-row">
            <!--面积图-->
            <el-col :span="24">
                <chart-line></chart-line>
            </el-col>
        </el-row>
        <el-row :gutter="12" class="tobacco-index_charts-row">
            <!--柱状图-->
            <el-col :span="16">
                <chart-bar></chart-bar>
            </el-col>
            <!--饼状图-->
            <el-col :span="8">
                <chart-pie></chart-pie>
            </el-col>
        </el-row>
        <el-row :gutter="12" type="flex" class="tobacco-index_charts-row">
            <!--雷达图-->
            <el-col :span="6" >
                <!--<chart-radar></chart-radar>-->
                <custom-time-line></custom-time-line>
            </el-col>
            <!--表格-->
            <el-col :span="18">
                <tobacco-table></tobacco-table>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { ChartBar, ChartLine, ChartPie, ChartRadar } from "@/views/detect/tobacco/index/components/charts";
import TobaccoTable from "./components/table/index.vue";
import TimeLine from "@/views/components/timeline.vue";
import CustomTimeLine from "@/views/detect/tobacco/index/components/charts/CustomTimeLine.vue";

</script>

<style lang="scss" scoped>
.main-content {
    margin: 20px 20px 0 !important;
}

.tobacco-index {
    &_charts-row {
        margin-bottom: 12px;
    }
}
</style>
