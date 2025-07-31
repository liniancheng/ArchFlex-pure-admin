<template>
    <el-card class="chart-card" shadow="never">
        <div class="flex justify-between">
            <span class="text-md font-medium">{{ transformI18n(title) }}</span>
        </div>
        <div class="flex justify-between items-start mt-3">
            <pure-table
                row-key="id"
                alignWhole="center"
                showOverflowTooltip
                :loading="loading"
                :loading-config="{ background: 'transparent' }"
                :data="dataList.slice(
                    (pagination.currentPage - 1) * pagination.pageSize,
                    pagination.currentPage * pagination.pageSize
                )"
                :columns="columns"
                :pagination="pagination"
                @page-current-change="onCurrentChange"
            >
                <template #empty>
                    <el-empty description="暂无数据" :image-size="60">
                        <template #image>
                            <Empty/>
                        </template>
                    </el-empty>
                </template>
                <template #operation="{ row }">
                    <el-button
                        plain
                        circle
                        size="small"
                        :title="`查看序号为${row.id}的详情`"
                        :icon="useRenderIcon('ri:search-line')"
                    />
                </template>
            </pure-table>
        </div>
    </el-card>
</template>

<script setup lang="ts">
import { useColumns } from "./columns";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { $t, transformI18n } from "@/plugins/i18n";

const { loading, columns, dataList, pagination, Empty, onCurrentChange } = useColumns();

const props = defineProps({
    title: {
        type: String,
        default: $t("detect.tobacco.real"),
    }
});
</script>

<style lang="scss">
.pure-table-filter {
    .el-table-filter__list {
        min-width: 80px;
        padding: 0;

        li {
            line-height: 28px;
        }
    }
}
</style>

<style lang="scss" scoped>
:deep(.el-table) {
    --el-table-border: none;
    --el-table-border-color: transparent;

    .el-empty__description {
        margin: 0;
    }

    .el-scrollbar__bar {
        display: none;
    }
}

.chart-card {
    height: 100%;
}
</style>
