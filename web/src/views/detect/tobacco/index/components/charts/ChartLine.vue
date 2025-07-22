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
import { type PropType, ref, computed } from "vue";
import { useDark, useECharts } from "@pureadmin/utils";
import { $t, transformI18n } from "@/plugins/i18n";
import { lineChartData } from "@/views/detect/tobacco/index/data";

const props = defineProps({
    data: {
        type: Array as PropType<Array<number>>,
        default: lineChartData
    },
    title: {
        type: String,
        default: $t("detect.tobacco.real"),
    }
});

const { isDark } = useDark();
const theme = computed(() => (isDark.value ? "dark" : "default"));

const chartRef = ref();
const { setOptions } = useECharts(chartRef, { theme });

setOptions({
    xAxis: {
        type: "category",
        data: props.data
    },
    yAxis: {
        type: "value"
    },
    series: [
        {
            data: props.data,
            type: "line",
            smooth: true,
            areaStyle: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0, color: 'rgba(140, 162, 228, 1)' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'rgba(140, 162, 228, 0.2)' // 100% 处的颜色
                    }],
                    global: false // 缺省为 false
                }
            }
        }
    ]
});
</script>

<style lang="scss" scoped>
.chart-card {
    height: 100%;
}
</style>
