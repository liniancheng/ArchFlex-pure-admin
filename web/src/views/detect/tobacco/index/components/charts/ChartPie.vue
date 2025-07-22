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
import { pieChartData } from "@/views/detect/tobacco/index/data";

const props = defineProps({
    data: {
        type: Array as PropType<Array<AnyObject>>,
        default: pieChartData
    },
    title: {
        type: String,
        default: $t("detect.tobacco.user"),
    }
});

const { isDark } = useDark();
const theme = computed(() => (isDark.value ? "dark" : "default"));

const chartRef = ref();
const { setOptions } = useECharts(chartRef, { theme });

setOptions({
    tooltip: {
        trigger: "item"
    },
    legend: {
        top: "5%",
        left: "center"
    },
    series: [
        {
            name: "总个数",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: "#fff",
                borderWidth: 2
            },
            label: {
                show: false,
                position: "center"
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 40,
                    fontWeight: "bold"
                }
            },
            labelLine: {
                show: false
            },
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
