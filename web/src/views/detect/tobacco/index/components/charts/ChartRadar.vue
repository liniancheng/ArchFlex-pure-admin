<template>
    <el-card class="chart-card" shadow="never">
        <div class="flex justify-between">
            <span class="text-md font-medium">{{ transformI18n(title) }}</span>
        </div>
        <div class="flex justify-between items-start mt-3">
            <div ref="chartRef" style="width: 100%; height: 365px" />
        </div>
    </el-card>
</template>

<script setup lang="ts">
import { ref, computed, type PropType } from "vue";
import { useDark, useECharts } from "@pureadmin/utils";
import { $t, transformI18n } from "@/plugins/i18n";
import { radarChartData } from "@/views/detect/tobacco/index/data";

const props = defineProps({
    data: {
        type: Array as PropType<Array<AnyObject>>,
        default: radarChartData
    },
    title: {
        type: String,
        default: $t("detect.tobacco.confidence"),
    }
});

const { isDark } = useDark();
const theme = computed(() => (isDark.value ? "dark" : "default"));

const chartRef = ref();
const { setOptions } = useECharts(chartRef, { theme });

setOptions({
    tooltip: {},
    legend: {
        top: 10,
        left: "left",
        data: ["图像", "视频"]
    },
    radar: {
        // shape: 'circle',
        radius: "60%",
        indicator: [
            { name: "用户一" },
            { name: "用户二" },
            { name: "用户三" },
            { name: "用户四" },
            { name: "用户五" }
        ]
    },
    series: [
        {
            name: "图像 vs 视频",
            type: "radar",
            data: props.data
        }
    ]
});
</script>

<style lang="scss" scoped>
.chart-card {
    height: 100%;
}
</style>
