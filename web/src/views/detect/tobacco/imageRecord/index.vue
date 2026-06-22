<template>
    <div class="detect-tobacco-image-record_container">
        <div class="detect-tobacco-image-record_inner">
            <el-row :gutter="10" justify="space-between">
                <el-col :span="16">
                    <el-input
                        v-model="state.tableData.param.search1"
                        size="default"
                        placeholder="请输入检测类型"
                        style="max-width: 180px"
                    >
                    </el-input>
                    <el-input
                        v-model="state.tableData.param.search2"
                        size="default"
                        placeholder="请输入识别结果"
                        style="max-width: 180px; margin: 0 15px"
                    >
                    </el-input>
                    <el-button
                        size="default"
                        type="primary"
                        class="ml10"
                        @click="getTableData"
                    >
                        <el-icon>
                            <Search />
                        </el-icon>
                        <span class="ml-1">查询</span>
                    </el-button>
                    <el-button
                        size="default"
                        type="primary"
                        @click="onHandleExport"
                    >
                        <el-icon>
                            <Download />
                        </el-icon>
                        <span class="ml-1">数据导出</span>
                    </el-button>
                </el-col>
                <el-col
                    :span="8"
                    style="display: flex; justify-content: flex-end"
                >
                    <el-button
                        v-show="multipleSelection.length > 0"
                        size="default"
                        type="danger"
                        @click="onHandleBulkDelete"
                    >
                        <el-icon>
                            <Delete />
                        </el-icon>
                        <span>批量删除</span>
                    </el-button>
                </el-col>
            </el-row>

            <el-table
                v-loading="state.tableData.loading"
                class="detect-tobacco-image-record_table"
                :data="state.tableData.data"
                style="width: 100%"
                @selection-change="onHandleSelectionChange"
            >
                <el-table-column
                    type="selection"
                    width="40"
                    align="center"
                    fixed
                />
                <el-table-column type="expand">
                    <template #default="props">
                        <div>
                            <p
                                style="
                                    margin-left: 20px;
                                    font-size: 16px;
                                    font-weight: 800;
                                "
                            >
                                详细识别结果：
                            </p>
                            <el-table :data="props.row.family">
                                <el-table-column
                                    prop="label"
                                    label="识别结果"
                                    align="center"
                                />
                                <el-table-column
                                    prop="confidence"
                                    label="置信度"
                                    show-overflow-tooltip
                                    align="center"
                                ></el-table-column>
                                <el-table-column
                                    prop="startTime"
                                    label="识别时间"
                                    align="center"
                                />
                            </el-table>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="num"
                    label="序号"
                    width="80"
                    align="center"
                />
                <el-table-column
                    prop="inputImg"
                    label="原始图片"
                    width="120"
                    align="center"
                >
                    <template #default="scope">
                        <el-image
                            style="width: 120px; height: 80px"
                            :src="scope.row.inputImg"
                            :alt="scope.row.inputImg"
                            :preview-src-list="[scope.row.inputImg]"
                        />
                    </template>
                </el-table-column>
                <el-table-column
                    prop="outImg"
                    label="预测图片"
                    width="120"
                    align="center"
                >
                    <template #default="scope">
                        <el-image
                            style="width: 120px; height: 80px"
                            :src="scope.row.outImg"
                            :alt="scope.row.outImg"
                            :preview-src-list="[scope.row.inputImg]"
                        />
                    </template>
                </el-table-column>
                <el-table-column
                    prop="weight"
                    label="识别权重"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="conf"
                    label="最小阈值"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="allTime"
                    label="总用时"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="startTime"
                    label="识别时间"
                    width="200"
                    align="center"
                />
                <el-table-column
                    prop="username"
                    label="识别用户"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column label="操作" width="80">
                    <template #default="scope">
                        <el-button
                            size="small"
                            text
                            type="primary"
                            @click="onRowDel(scope.row)"
                            >删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="onHandleSizeChange"
                @current-change="onHandleCurrentChange"
                class="mt15"
                :pager-count="5"
                :page-sizes="[10, 20, 30]"
                v-model:current-page="state.tableData.param.pageNum"
                background
                v-model:page-size="state.tableData.param.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="state.tableData.total"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { ElMessageBox } from "element-plus";
import { message } from "@/utils/message";
import { storeToRefs } from "pinia";
import { useUserStoreHook } from "@/store/modules/user";
import { deleteRecord, getRecords } from "@/api/detect/image";
import Search from "~icons/ep/search";
import Delete from "~icons/ep/delete";
import Download from "~icons/ep/Download";

const stores = useUserStoreHook();
const { username } = storeToRefs(stores);

const state = reactive({
    tableData: {
        data: [] as any,
        total: 0,
        loading: false,
        param: {
            search: "",
            search1: "",
            search2: "",
            pageNum: 1,
            pageSize: 10
        }
    }
});
const multipleSelection = ref([]);

const getTableData = () => {
    state.tableData.loading = true;
    if (username.value != "admin") {
        state.tableData.param.search = username.value;
    }
    getRecords(state.tableData.param).then(res => {
        if (res.code == 0) {
            state.tableData.data = [];
            setTimeout(() => {
                state.tableData.loading = false;
            }, 500);
            for (let i = 0; i < res.data.records.length; i++) {
                const confidences = JSON.parse(res.data.records[i].confidence);
                const labels = JSON.parse(res.data.records[i].label);
                const transformedData = transformData(
                    res.data.records[i],
                    confidences,
                    labels
                );
                transformedData["num"] = i + 1;
                state.tableData.data[i] = transformedData;
            }
            state.tableData.total = res.data.total;
        } else {
            message(res.msg, { type: "error" });
        }
    });
};

const transformData = (originalData, confidences, labels) => {
    const family = labels.map((label, index) => ({
        label: label,
        confidence: confidences[index],
        startTime: originalData.startTime
    }));

    return {
        id: originalData.id,
        inputImg: originalData.inputImg,
        outImg: originalData.outImg,
        weight: originalData.weight,
        allTime: originalData.allTime,
        conf: originalData.conf,
        startTime: originalData.startTime,
        username: originalData.username,
        family: family
    };
};

// 删除
const onRowDel = (row: any) => {
    ElMessageBox.confirm(`此操作将永久删除该信息，是否继续?`, "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
    })
        .then(() => {
            console.log(row);
            deleteRecord(row.id).then(res => {
                if (res.code == 0) {
                    console.log(res.data);
                    message("删除成功！", { type: "success" });
                } else {
                    message(res.msg, { type: "error" });
                }
            });
            setTimeout(() => {
                getTableData();
            }, 500);
        })
        .catch(() => {});
};
// 分页改变
const onHandleSizeChange = (val: number) => {
    state.tableData.param.pageSize = val;
    getTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
    state.tableData.param.pageNum = val;
    getTableData();
};

const onHandleSelectionChange = (val: AnyArray) => {
    multipleSelection.value = val;
};

const onHandleExport = () => {};

const onHandleBulkDelete = () => {
    ElMessageBox.confirm(`此操作将删除所有选中的记录，是否继续?`, "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
    })
        .then(() => {
            deleteRecord(
                multipleSelection.value.map(item => item.id).join(",")
            ).then(res => {
                if (res.code == 0) {
                    message("删除成功！", { type: "success" });
                } else {
                    message(res.msg, { type: "error" });
                }
            });
            setTimeout(() => {
                getTableData();
            }, 500);
        })
        .catch(() => {});
};

// 页面加载时
onMounted(() => {
    getTableData();
});
</script>

<style scoped lang="scss">
.detect-tobacco-image-record {
    &_container {
        height: 100%;
    }

    &_inner {
        height: 100%;
        border-radius: 10px;
        padding: 15px;
        background: #d3e3f1;
    }

    &_table {
        background: #d3e3f1;
        flex: 1;
        margin: 15px 0;
    }
}
</style>
