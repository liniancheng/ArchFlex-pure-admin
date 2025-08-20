<template>
    <div class="detect-tobacco-video-record_container">
        <div class="detect-tobacco-video-record_inner">
            <div class="detect-tobacco-video-record_search">
                <el-input
                    v-model="state.tableData.param.search1"
                    size="default"
                    placeholder="请输入农作物类型"
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
                    @click="getTableData()"
                >
                    <el-icon>
                        <Search />
                    </el-icon>
                    查询
                </el-button>
            </div>
            <el-table
                class="detect-tobacco-video-record_table"
                :data="state.tableData.data"
                v-loading="state.tableData.loading"
                style="width: 100%"
            >
                <el-table-column
                    column-key="num"
                    prop="num"
                    label="序号"
                    width="100"
                    align="center"
                />
                <el-table-column
                    column-key="inputVideo"
                    prop="inputVideo"
                    label="原视频"
                    width="200"
                    align="center"
                >
                    <template #default="scope">
                        <video
                            class="detect-tobacco-video-record_video"
                            controls
                            :key="scope.row.inputVideo + uniqueKey"
                        >
                            <source
                                :src="scope.row.inputVideo"
                                type="video/mp4"
                            />
                        </video>
                    </template>
                </el-table-column>
                <el-table-column
                    column-key="outVideo"
                    prop="outVideo"
                    label="处理结果"
                    width="200"
                    align="center"
                >
                    <template #default="scope">
                        <video
                            class="detect-tobacco-video-record_video"
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
                    column-key="kind"
                    prop="kind"
                    label="农作物种类"
                    align="center"
                />
                <el-table-column
                    column-key="weight"
                    prop="weight"
                    label="识别权重"
                    align="center"
                />
                <el-table-column
                    column-key="conf"
                    prop="conf"
                    label="最小阈值"
                    show-overflow-tooltip
                    width="100"
                    align="center"
                ></el-table-column>
                <el-table-column
                    column-key="username"
                    prop="username"
                    label="识别用户"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column
                    column-key="startTime"
                    prop="startTime"
                    label="识别时间"
                    show-overflow-tooltip
                    align="center"
                ></el-table-column>
                <el-table-column column-key="operation" label="操作" width="240" align="center">
                    <template #default="scope">
                        <el-button
                            size="small"
                            text
                            type="primary"
                            @click="onRowDel(scope.row)"
                        >
                            删除
                        </el-button>
                        <el-button
                            size="small"
                            text
                            type="primary"
                            @click="show(scope.row)"
                        >
                            查看详情
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                v-model:current-page="state.tableData.param.pageNum"
                v-model:page-size="state.tableData.param.pageSize"
                background
                class="detect-tobacco-video-record_pagination"
                layout="total, sizes, prev, pager, next, jumper"
                :total="state.tableData.total"
                :pager-count="5"
                :page-sizes="[10, 20, 30]"
                @current-change="onHandleCurrentChange"
                @size-change="onHandleSizeChange"
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
import { deleteRecord, getRecords } from "@/api/detect/video";
import Search from "~icons/ep/search";

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
            search2: "",
            search3: "",
            pageNum: 1,
            pageSize: 10
        }
    }
});

// 唯一标识符，动态刷新
const uniqueKey = ref(0);

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

const show = (row: any) => {
    window.open("http://localhost:8000/#/videoShow?id=" + row.id);
};

const onRowDel = (row: any) => {
    ElMessageBox.confirm(`此操作将永久删除该信息，是否继续?`, "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
    })
        .then(() => {
            deleteRecord(row.id).then(res => {
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

const onHandleSizeChange = (val: number) => {
    state.tableData.param.pageSize = val;
    getTableData();
};

const onHandleCurrentChange = (val: number) => {
    state.tableData.param.pageNum = val;
    getTableData();
};

onMounted(() => {
    getTableData();
});
</script>

<style scoped lang="scss">
.detect-tobacco-video-record {
    &_container {
        height: 100%;

        .detect-tobacco-video-record {
            &_inner {
                height: 100%;
                border-radius: 10px;
                padding: 15px;
                background: #d3e3f1;

                .detect-tobacco-video-record {

                    &_table {
                        background: #d3e3f1;
                        flex: 1;
                        margin: 15px 0;
                    }
                }
            }
        }
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
