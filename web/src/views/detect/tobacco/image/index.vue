<template>
    <div>
        <div class="detect-tobacco-image-index_header">
            <div class="detect-tobacco-image-index_header_title-box">烟虫检测</div>
        </div>
        <el-form
            ref="detectionFormRef"
            :model="detectionParam"
        >
            <div class="detect-tobacco-image-index_form-wrapper">
                <el-row :gutter="48">
                    <el-col
                        v-for="select in selectList"
                        :key="select.name"
                        :span="4" :xs="24" :sm="8" :md="8" :lg="4" :xl="4"
                    >
                        <el-form-item :label="select.label" :prop="select.name" required>
                            <el-select
                                v-model="detectionParam[select.name]"
                                :placeholder="select.placeholder"
                                @change="handleSelectChange(select.name)"
                            >
                                <el-option
                                    v-for="item in select.data"
                                    :key="item.value"
                                    :value="item.value"
                                >
                                    {{ item.label }}
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7" :xs="24" :sm="24" :md="14" :lg="12" :xl="7">
                        <el-form-item label="最小置信度阈值" prop="minThreshold">
                            <el-slider v-model="detectionParam.minThreshold" :max="1" :min="0" :step="0.1"></el-slider>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5" :xs="24" :sm="24" :md="10" :lg="24" :xl="5">
                        <span class="detect-tobacco-image-index_buttons">
                            <el-button type="primary" :loading="detecting" @click="handleDetection">开始预测</el-button>
                            <el-button type="primary" @click="handleExport">PDF导出</el-button>
                            <el-button @click="handleReset">重置</el-button>
                        </span>
                    </el-col>
                </el-row>
            </div>
            <el-form-item prop="originalImage" required>
                <div
                    class="detect-tobacco-image-index_upload-wrapper"
                    :class="{
                        'detect-tobacco-image-index_upload-wrapper--selectedImage': selectedImage
                    }"
                >
                    <el-upload
                        class="detect-tobacco-image-index_upload-container"
                        v-if="!selectedImage"
                        drag
                        name="file"
                        :maxCount="1"
                        :showUploadList="false"
                        :beforeUpload="beforeUpload"
                    >
                        <div class="detect-tobacco-image-index_upload-box">
                            <el-icon color="#888" :size="48">
                                <UploadIcon/>
                            </el-icon>
                            <div class="detect-tobacco-image-index_upload-box_text">点击上传</div>
                        </div>
                    </el-upload>
                    <img
                        v-else
                        :src="detectionParam?.originalImage"
                        :alt="imageFile.name"
                        class="detect-tobacco-image-index_upload-tobacco-image"
                    />
                </div>
            </el-form-item>
        </el-form>
        <div class="detect-tobacco-image-index_header">
            <div class="detect-tobacco-image-index_header_title-box">AI建议</div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import { message } from "@/utils/message";
import { DetectImage, detect, upload } from "@/api/detect/image";
import UploadIcon from "~icons/ri/upload-2-line?width=26&height=26";
import type { FormInstance } from "element-plus";


const detecting = ref(false);
const detectionFormRef = ref<FormInstance>(); // 表单ref
const imageFile = ref(); // 图片文件
// 检测参数
const detectionParam = ref<DetectImage>({
    originalImage: null,
    model: null,
    recognitionWeight: null,
    aiAssistant: null,
    minThreshold: 0.5
});

const selectList = ref({
    model: {
        label: "模型",
        name: "model",
        placeholder: "请选择模型",
        data: [
            { label: "yolo", value: "yolo" },
            { label: "rtdetr", value: "rtdetr" },
            { label: "deim", value: "deim" }
        ]
    },
    recognitionWeight: {
        label: "权重文件",
        name: "recognitionWeight",
        placeholder: "请选择权重文件",
        data: [
            { label: "best.onnx", value: "best.onnx" },
            { label: "yolov8n.pt", value: "yolov8n.pt" },
            { label: "yolov11n.pt", value: "yolov11n.pt" }
        ]
    },
    aiAssistant: {
        label: "AI助手",
        name: "aiAssistant",
        placeholder: "请选择AI助手",
        data: [
            { label: "DeepSeek", value: "DeepSeek" },
            { label: "Qwen", value: "Qwen" },
            { label: "不使用AI", value: "不使用AI" }
        ]
    }
})

const selectedImage = computed(()=>{
    return !!detectionParam.value?.originalImage;
})


function beforeUpload(file) {
    // 上传图片前处理方法
    handleFileSelect(file);
    return false;
}

function handleFileSelect(file: File) {
    imageFile.value = file;
    detectionFormRef.value.clearValidate(['originalImage']);
    customUpload();
}

function customUpload() {
    // 自定义上传方法
    upload(imageFile.value).then(res => {
        detectionParam.value.originalImage = res.data;
    });
}

function handleSelectChange (name: string) {
    detectionFormRef.value.clearValidate([name]);
}

function handleDetection() {
    detectionFormRef.value.validate(valid => {
        if (!valid) return;
        // 检测图片方法
        detecting.value = true;
        detect(detectionParam.value).then((res)=>{
            // TODO
            message(JSON.stringify(res), { type: "success" });
        }).catch((err)=>{
            message(err?.message, { type: "error" });
        }).finally(()=>{
            detecting.value = false;
        })
    })
}

function handleExport() {
    // TODO
}

function handleReset() {
    // 重置参数
    detectionParam.value = {
        originalImage: null,
        model: null,
        recognitionWeight: null,
        aiAssistant: null,
        minThreshold: 0.5
    };
    imageFile.value = null;
}

</script>

<style lang="scss" scoped>
.detect-tobacco-image-index {
    &_header {
        margin: 24px 0;
        text-align: center;
        font-size: 24px; /* 设置字体大小为24像素 */
        position: relative;
        display: flex;
        align-items: center;
        justify-content: center;

        &::before {
            content: "";
            width: 100%;
            height: 2px;
            background-color: var(--pure-theme-menu-active-before);
            position: absolute;
            z-index: 1;
        }

        &_title-box {
            padding: 8px 24px;
            color: #fff;
            background-color: var(--pure-theme-menu-active-before);
            border-radius: 4px; /* 圆角边框 */
            font-weight: bold;
            z-index: 2;
        }
    }

    &_upload {
        &-wrapper {
            width: 100%;
            height: 500px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            margin-top: 20px;

            &.detect-tobacco-image-index_upload-wrapper--selectedImage {
                background-color: #fafafa;
                border: 1px dashed #d9d9d9;
                border-radius: 4px;
            }
        }

        &-container {
            width: 100%;
            height: 100%;
        }

        &-box {
            width: 100%;
            height: calc(500px - (var(--el-upload-dragger-padding-horizontal) * 2));
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: #888;

            &-text {
                margin-top: 8px;
            }
        }

        &-tobacco-image {
            height: 100%;
        }

    }
}
</style>
