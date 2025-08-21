<template>
    <div class="detect-tobacco-camera-record_container">
        <div class="detect-tobacco-camera-record_inner">
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
                        v-model="state.tableData.param.search3"
                        size="default"
                        placeholder="请输入最低阈值"
                        style="max-width: 180px; margin: 0 15px"
                    ></el-input>
                    <el-button
                        size="default"
                        type="primary"
                        @click="getTableData"
                    >
                        <el-icon>
                            <Search />
                        </el-icon>
                        查询
                    </el-button>
                </el-col>
                <el-col
                    :span="8"
                    style="display: flex; justify-content: flex-end"
                >
                    <el-button
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
                class="detect-tobacco-camera-record_table"
                :data="state.tableData.data"
                v-loading="state.tableData.loading"
                style="width: 100%"
                @selection-change="onHandleSelectionChange"
            >
                <el-table-column
                    type="selection"
                    width="40"
                    align="center"
                    fixed
                />
                <el-table-column
                    prop="num"
                    label="序号"
                    width="100"
                    align="center"
                />
                <el-table-column
                    prop="outVideo"
                    label="处理结果"
                    width="200"
                    align="center"
                >
                    <template #default="scope">
                        <video
                            class="video"
                            preload="auto"
                            controls
                            :key="scope.row.outVideo + uniqueKey"
                        >
                            <source
                                :src="scope.row.outVideo"
                                type="video/mp4"
                            />
                        </video>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="kind"
                    label="检测种类"
                    align="center"
                />
                <el-table-column
                    prop="weight"
                    label="识别权重"
                    align="center"
                />
                <el-table-column
                    prop="conf"
                    label="最小阈值"
                    show-overflow-tooltip
                    width="100"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="username"
                    label="识别用户"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="startTime"
                    label="识别时间"
                    show-overflow-tooltip
                    align="center"
                    :formatter="formatStartTime"
                ></el-table-column>
                <el-table-column label="操作" width="240" align="center">
                    <template #default="scope">
                        <el-button
                            size="small"
                            text
                            type="primary"
                            @click="onRowDel(scope.row)"
                        >
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                v-model:page-size="state.tableData.param.pageSize"
                v-model:current-page="state.tableData.param.pageNum"
                background
                class="detect-tobacco-camera-record_pagination"
                layout="total, sizes, prev, pager, next, jumper"
                :pager-count="5"
                :page-sizes="[10, 20, 30]"
                :total="state.tableData.total"
                @size-change="onHandleSizeChange"
                @current-change="onHandleCurrentChange"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, onMounted, ref } from "vue";
import { ElMessageBox } from "element-plus";
import { message } from "@/utils/message";
import { storeToRefs } from "pinia";
import { useUserStoreHook } from "@/store/modules/user";
import { dayjs } from "@/views/detect/tobacco/index/utils";
import { deleteRecord, getRecords } from "@/api/detect/camera";
import Search from "~icons/ep/search";
import Delete from "~icons/ep/delete";

const stores = useUserStoreHook();
const { username } = storeToRefs(stores);

const state = reactive<SysRoleState>({
    tableData: {
        data: [] as any,
        total: 0,
        loading: false,
        param: {
            search: "",
            search1: "",
            search3: "",
            search2: "",
            pageNum: 1,
            pageSize: 10
        }
    }
});

// 唯一标识符，动态刷新
const uniqueKey = ref(0);
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
                state.tableData.data[i] = res.data.records[i];
                state.tableData.data[i]["num"] = i + 1;
            }
            state.tableData.total = res.data.total;

            // 更新唯一标识符
            uniqueKey.value++;
        } else {
            message(res.msg, { type: "error" });
        }
    });
};

const formatStartTime = (row: any, column: any, cellValue: string) => {
    return dayjs(cellValue).format("YYYY-MM-DD HH:mm:ss");
}

const onRowDel = (row: any) => {
    ElMessageBox.confirm(`此操作将删除该记录，是否继续?`, "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
    })
        .then(() => {
            deleteRecord(row.id).then(res => {
                if (res.code == 0) {
                    message("删除成功！", { type: "error" });
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

const onHandleSizeChange = (val: number) => {
    state.tableData.param.pageSize = val;
    getTableData();
};

const onHandleCurrentChange = (val: number) => {
    state.tableData.param.pageNum = val;
    getTableData();
};

const onHandleSelectionChange = (val: AnyArray) => {
    multipleSelection.value = val;
};

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

onMounted(() => {
    getTableData();
});
</script>

<style scoped lang="scss">
.detect-tobacco-camera-record {
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

    &_video {
        width: 100%;
        max-height: 100%;
        /* 限制视频最大高度不超过父元素高度 */
        height: auto;
        object-fit: contain;
    }
}
</style>
