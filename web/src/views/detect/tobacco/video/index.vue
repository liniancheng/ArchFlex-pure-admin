<template>
    <div>
        <div class="header">
            <div class="kind">
                <el-select v-model="kind" placeholder="请选择作物种类" size="large" style="width: 180px" @change="getData">
                    <el-option v-for="item in state.kind_items" :key="item.value" :label="item.label"
                               :value="item.value" />
                </el-select>
            </div>
            <div class="weight">
                <el-select v-model="weight" placeholder="请选择模型" size="large" style="margin-left: 20px;width: 180px">
                    <el-option v-for="item in state.weight_items" :key="item.value" :label="item.label"
                               :value="item.value" />
                </el-select>
            </div>
            <div class="conf" style="margin-left: 20px;display: flex; flex-direction: row;">
                <div
                    style="font-size: 14px;margin-right: 20px;display: flex;justify-content: start;align-items: center;color: #909399;">
                    设置最小置信度阈值</div>
                <el-slider v-model="conf" :format-tooltip="formatTooltip" style="width: 280px;" />
            </div>
            <el-upload v-model="state.form.inputVideo" ref="uploadFile" class="avatar-uploader"
                       action="http://localhost:9999/files/upload" :show-file-list="false"
                       :on-success="handleAvatarSuccessOne">
                <div class="button-section" style="margin-left: 20px">
                    <el-button type="info" class="predict-button">上传视频</el-button>
                </div>
            </el-upload>
            <div class="button-section" style="margin-left: 20px">
                <el-button type="primary" @click="upData" class="predict-button">开始处理</el-button>
            </div>
            <div class="demo-progress" v-if="state.isShow">
                <el-progress :text-inside="true" :stroke-width="20" :percentage=state.percentage style="width: 380px;">
                    <span>{{ state.type_text }} {{ state.percentage }}%</span>
                </el-progress>
            </div>
        </div>
        <div class="cards" ref="cardsContainer">
            <img v-if="state.video_path" class="video" :src="state.video_path" alt="结果">
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { message } from "@/utils/message";
import { formatDate } from "@vueuse/core";
import { SocketService } from '@/utils/socket';
import { getWeightList } from "@/api/detect/video";
import { useUserStoreHook } from "@/store/modules/user";
import type { UploadInstance } from 'element-plus';

const uploadFile = ref<UploadInstance>();
const stores = useUserStoreHook();
const conf = ref(0);
const kind = ref('');
const weight = ref('');
const { username } = storeToRefs(stores);

const state = reactive({
    weight_items: [] as any,
    kind_items: [
        {
            value: 'tobacco',
            label: '烟草',
        }
    ],
    data: {} as any,
    video_path: '',
    type_text: "正在保存",
    percentage: 50,
    isShow: false,
    form: {
        username: '',
        inputVideo: null as any,
        weight: '',
        conf: null as any,
        kind: '',
        startTime: ''
    },
});

const socketService = new SocketService();

socketService.on('message', (data: string) => {
    console.log('Received message:', data);
    message(data, { type: "success" });
});

socketService.on('progress', (data: number) => {
    state.percentage = parseInt(data);
    if (parseInt(data) < 100) {
        state.isShow = true;
    } else {
        //两秒后隐藏进度条
        message("保存成功！", { type: "success" });
        setTimeout(() => {
            state.isShow = false;
            state.percentage = 0;
        }, 2000);
    }
    console.log('Received message:', data);
});

function formatTooltip(val: number): number {
    return val / 100
}

function handleAvatarSuccessOne(response: any) {
    message("上传成功！", { type: "success" });
    state.form.inputVideo = response.data;
}

function getData() {
    // TODO: 请求接口获取数据
    getWeightList();
    state.weight_items = [
        {
            value: 'yolo11n.pt',
            label: 'yolo11n.pt',
        }
    ];
}

function upData() {
    state.form.weight = weight.value;
    state.form.conf = (parseFloat(conf.value)/100);
    state.form.username = username.value;
    state.form.kind = kind.value;
    state.form.startTime = formatDate(new Date(), 'YYYY-mm-dd HH:MM:SS');
    console.log(state.form);
    const queryParams = new URLSearchParams(state.form).toString();
    state.video_path = `http://127.0.0.1:5000/predictVideo?${queryParams}`;
    message("正在加载！", { type: "success" });
}

onMounted(() => {
    getData();
});
</script>

<style scoped lang="scss">
.header {
    width: 100%;
    height: 5%;
    display: flex;
    justify-content: start;
    align-items: center;
    font-size: 20px;
}

.cards {
    width: 100%;
    height: 95%;
    min-height: 70vh;
    border-radius: 5px;
    margin-top: 15px;
    padding: 0;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    background: radial-gradient(circle, #d3e3f1 0%, #ffffff 100%);
    /* 防止视频溢出 */
}

.video {
    width: 100%;
    max-height: 100%;
    /* 限制视频最大高度不超过父元素高度 */
    height: auto;
    object-fit: contain;
}

.button-section {
    display: flex;
    justify-content: center;
}

.predict-button {
    width: 100%;
    /* 按钮宽度填满 */
}

.demo-progress .el-progress--line {
    margin-left: 20px;
    width: 600px;
}
</style>
