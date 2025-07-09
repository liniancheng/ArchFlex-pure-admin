<template>
    <div class="w-full h-full">
        <!--TODO: 以下是从后端获取首页相关布局展示-->
        <div class="flex items-center justify-center w-full h-full">
            努力开发中...<br>
            (爽歪的麻雀)
        </div>

        <!--TODO: 以下是前端自定义显示内容-->
        <el-row :gutter="12" class="tobacco-index__charts-row">
            <!--柱状图-->
            <el-col :span="16">

            </el-col>
            <!--饼状图-->
            <el-col :span="8">

            </el-col>
        </el-row>
        <el-row :gutter="12" type="flex" class="tobacco-index__charts-row">
            <!--雷达图-->
            <el-col :span="8">

            </el-col>
            <!--表格-->
            <el-col :span="16">

            </el-col>
        </el-row>
        <el-row :gutter="12" class="tobacco-index__charts-row">
            <!--面积图-->
        </el-row>
    </div>
</template>

<script lang="ts" setup>

</script>

<style lang="scss" scoped>

</style>
