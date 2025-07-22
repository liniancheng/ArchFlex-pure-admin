<template>
    <el-card class="chart-card" shadow="never">
        <div class="flex justify-between">
            <span class="text-md font-medium">{{ transformI18n(title) }}</span>
            <Segmented v-model="curDetectType" :options="optionsBasis" />
        </div>
        <div class="flex justify-between items-start mt-3">
            <div ref="chartRef" style="width: 100%; height: 365px" />
        </div>
    </el-card>
</template>

<script setup lang="ts">
import { type PropType, ref, computed, watch, nextTick } from "vue";
import { useDark, useECharts } from "@pureadmin/utils";
import { $t, transformI18n } from "@/plugins/i18n";
import { barChartData, pests } from "@/views/detect/tobacco/index/data";
import Segmented, { type OptionsType } from "@/components/ReSegmented";

const props = defineProps({
    data: {
        type: Array as PropType<Array<AnyObject>>,
        default: barChartData
    },
    title: {
        type: String,
        default: $t("detect.tobacco.class"),
    }
});

const { isDark } = useDark();
const theme = computed(() => (isDark.value ? "dark" : "default"));

const chartRef = ref();
const { setOptions } = useECharts(chartRef, {
    theme
});

let curDetectType = ref(0); // 0上周、1本周
const optionsBasis: Array<OptionsType> = [
    {
        label: "单次"
    },
    {
        label: "批量"
    }
];

watch(
    () => props.data,
    () => {
        doSetOptions();
    },
    {
        deep: true,
        immediate: true
    }
);

watch(curDetectType, () => {
    doSetOptions();
});

async function doSetOptions() {
    await nextTick(); // 确保DOM更新完成后再执行
    setOptions({
        color: ["#41b6ff", "#e85f33"],
        tooltip: {
            trigger: "axis",
            axisPointer: {
                type: "none"
            }
        },
        grid: {
            top: "20px",
            left: "50px",
            right: 0
        },
        legend: {
            data: ["图像检测", "视频检测"],
            textStyle: {
                color: "#606266",
                fontSize: "0.875rem"
            },
            bottom: 0
        },
        xAxis: [
            {
                type: "category",
                data: pests,
                axisLabel: {
                    fontSize: "0.875rem"
                },
                axisPointer: {
                    type: "shadow"
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                axisLabel: {
                    fontSize: "0.875rem"
                },
                splitLine: {
                    show: false // 去网格线
                }
                // name: "单位: 个"
            }
        ],
        series: props.data.map(item => {
            return {
                name: item.name,
                type: "bar",
                barWidth: 10,
                itemStyle: {
                    color: item.color,
                    borderRadius: [10, 10, 0, 0]
                },
                data: item.data[curDetectType.value]
            }
        })
    });
}
</script>

<style lang="scss" scoped>
.chart-card {
    height: 100%;
}
</style>
