<script setup lang="ts">
import { latestNewsData } from "@/views/welcome/data";
import { markRaw } from "vue";
import { useRenderFlicker } from "@/components/ReFlicker";
import { randomGradient } from "@/views/welcome/utils";
</script>

<template>
    <div class="timeLine-card">
        <el-card shadow="never">
            <div class="flex justify-between">
                <span class="text-md font-medium">最新动态</span>
            </div>
            <el-scrollbar max-height="400" class="mt-3">
                <el-timeline>
                    <el-timeline-item
                        v-for="(item, index) in latestNewsData"
                        :key="index"
                        center
                        placement="top"
                        :icon="
                            markRaw(
                                useRenderFlicker({
                                    background: randomGradient({
                                        randomizeHue: true
                                    })
                                })
                            )
                        "
                        :timestamp="item.date"
                    >
                        <p class="text-text_color_regular text-sm">
                            {{
                                `新增 ${item.requiredNumber} 条问题，${item.resolveNumber} 条已解决`
                            }}
                        </p>
                    </el-timeline-item>
                </el-timeline>
            </el-scrollbar>
        </el-card>
    </div>
</template>

<style scoped lang="scss">
.timeLine-card {
    :deep(.el-card) {
        --el-card-border-color: none;

        /* 解决概率进度条宽度 */
        .el-progress--line {
            width: 85%;
        }

        /* 解决概率进度条字体大小 */
        .el-progress-bar__innerText {
            font-size: 15px;
        }

        /* 隐藏 el-scrollbar 滚动条 */
        .el-scrollbar__bar {
            display: none;
        }

        /* el-timeline 每一项上下、左右边距 */
        .el-timeline-item {
            margin: 0 6px;
        }
    }
}
</style>
