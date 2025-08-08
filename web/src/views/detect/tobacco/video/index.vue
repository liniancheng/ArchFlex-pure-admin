<template>
    <div>
        <el-form ref="detectionFormRef" :model="state.form" size="large">
            <el-row :gutter="24">
                <el-col :span="3" :xs="24" :sm="6" :md="6" :lg="5" :xl="3">
                    <el-form-item prop="kind" required>
                        <el-select v-model="state.form.kind" placeholder="请选择检测种类" @change="getData">
                            <el-option
                                v-for="item in state.kind_items"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="3" :xs="24" :sm="6" :md="6" :lg="5" :xl="3">
                    <el-form-item prop="weight" required>
                        <el-select v-model="state.form.weight" placeholder="请选择模型">
                            <el-option
                                v-for="item in state.weight_items"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8" :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
                    <el-form-item label="设置最小置信度阈值" prop="conf">
                        <el-slider v-model="state.form.conf" :step="0.01" :min="0" :max="1"/>
                    </el-form-item>
                </el-col>
                <el-col :span="4" :xs="24" :sm="8" :md="8" :lg="6" :xl="4">
                    <el-row :gutter="12">
                        <el-col :span="12" :xs="24">
                            <el-form-item prop="inputVideo" required>
                                <el-upload
                                    :maxCount="1"
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccessOne"
                                    :beforeUpload="beforeUpload"
                                >
                                    <el-button type="info">上传视频</el-button>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12" :xs="24">
                            <el-button type="primary" :loading="detecting" @click="upData">开始处理</el-button>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="6" :xs="24" :sm="16" :md="16" :lg="24" :xl="6">
                    <div v-if="state.isShow">
                        <el-progress :text-inside="true" :stroke-width="20" :percentage=state.percentage>
                            <span>{{ state.type_text }} {{ state.percentage }}%</span>
                        </el-progress>
                    </div>
                </el-col>
            </el-row>
        </el-form>
        <div class="detect-tobacco-video-index_result">
            <img v-if="state.video_path" class="detect-tobacco-video-index_result-video" :src="state.video_path" alt="结果">
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { message } from "@/utils/message";
import { upload } from "@/api/detect/video";
import { getWeightList } from "@/api/detect";
import { SocketService } from "@/utils/socket";
import { useUserStoreHook } from "@/store/modules/user";

const stores = useUserStoreHook();
const { username } = storeToRefs(stores);
const detectionFormRef = ref();
const detecting = ref(false);

const state = reactive({
    weight_items: [] as any,
    kind_items: [
        {
            value: "tobacco",
            label: "烟草",
        },
        {
            value: "coco",
            label: "coco",
        }
    ],
    data: {} as any,
    video_path: "",
    type_text: "正在保存",
    percentage: 50,
    isShow: false,
    form: {
        username: "",
        inputVideo: null as any,
        weight: "",
        conf: 0.5 as any,
        kind: "",
        startTime: ""
    },
});

const socketService = new SocketService();

socketService.on("message", (data: string) => {
    console.log("Received message:", data);
    message(data, { type: "success" });
});

socketService.on("progress", (data: number) => {
    state.percentage = parseInt(data);
    if (parseInt(data) < 100) {
        state.isShow = true;
    } else {
        //两秒后隐藏进度条
        message("保存成功！", { type: "success" });
        setTimeout(() => {
            state.isShow = false;
            state.percentage = 0;
            detecting.value = false;
        }, 2000);
    }
    console.log("Received message:", data);
});

function handleAvatarSuccessOne(response: any) {
    message("上传成功！", { type: "success" });
    state.form.inputVideo = response.data;
}

function beforeUpload(file: File) {
    // 上传图片前处理方法
    upload(file).then(res => {
        state.form.inputVideo = res.data;
    });
    return false;
}

function getData() {
    // TODO: 请求接口获取数据
    getWeightList();
    state.weight_items = [
        {
            value: "yolo11n.pt",
            label: "yolo11n.pt",
        }
    ];
}

function upData() {
    detectionFormRef.value.validate((valid: boolean) => {
        if (!valid) return;
        detecting.value = true;
        state.form.username = username.value;
        const now = new Date();
        const offset = now.getTimezoneOffset() * 60000; // 转换为毫秒
        const localTime = new Date(now.getTime() - offset);
        state.form.startTime = localTime.toISOString();
        console.log(state.form);
        const queryParams = new URLSearchParams(state.form).toString();
        state.video_path = `http://127.0.0.1:5000/predictVideo?${queryParams}`;
        message("正在加载！", { type: "success" });
    })
}

onMounted(() => {
    getData();
});
</script>

<style scoped lang="scss">
.detect-tobacco-video-index {
    &_result {
        width: 100%;
        height: 70vh;
        border-radius: 5px;
        margin-top: 15px;
        padding: 0;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;
        background: #d3e3f1;
        /* 防止视频溢出 */

        &-video {
            width: 100%;
            max-height: 100%;
            /* 限制视频最大高度不超过父元素高度 */
            height: auto;
            object-fit: contain;
        }
    }
}
</style>
