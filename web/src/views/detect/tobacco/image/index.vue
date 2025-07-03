<template>
    <div>
        <div class="detect-tobacco-image-index_header">
            <div class="detect-tobacco-image-index_header_title-box">烟虫检测</div>
        </div>
        <div class="detect-tobacco-image-index_form-wrapper">
            <el-form layout="inline">
                <el-row :gutter="48">
                    <el-col :span="4" :xs="24" :sm="8" :md="8" :lg="4" :xl="4">
                        <el-form-item label="模型">
                            <el-select v-model="detectionParam.model" placeholder="请选择模型" default-value="0">
                                <el-option value="0">yolo</el-option>
                                <el-option value="1">rtdetr</el-option>
                                <el-option value="2">deim</el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" :xs="24" :sm="8" :md="8" :lg="4" :xl="4">
                        <el-form-item label="权重文件">
                            <el-select v-model="detectionParam.weights" placeholder="请选择权重" default-value="0">
                                <el-option value="0">best.onnx</el-option>
                                <el-option value="1">yolov8n.pt</el-option>
                                <el-option value="2">yolov11n.pt</el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" :xs="24" :sm="8" :md="8" :lg="4" :xl="4">
                        <el-form-item label="AI助手">
                            <el-select v-model="detectionParam.ai" placeholder="请选择AI助手" default-value="0">
                                <el-option value="0">DeepSeek</el-option>
                                <el-option value="1">Qwen</el-option>
                                <el-option value="2">不使用AI</el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7" :xs="24" :sm="24" :md="14" :lg="12" :xl="7">
                        <el-form-item label="最小置信度阈值">
                            <el-slider v-model="detectionParam.minConfidence" :max="1" :min="0" :step="0.1"></el-slider>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5" :xs="24" :sm="24" :md="10" :lg="24" :xl="5">
                        <span class="detect-tobacco-image-index_buttons">
                            <el-button type="primary" @click="handleDetection">开始预测</el-button>
                            <el-button type="primary" @click="handleExport">PDF导出</el-button>
                            <el-button @click="handleReset">重置</el-button>
                        </span>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div
            class="detect-tobacco-image-index_upload-wrapper"
            :class="{
                'detect-tobacco-image-index_upload-wrapper--selectedImage': imageUrl
            }"
        >
            <el-upload
                class="detect-tobacco-image-index_upload-container"
                v-if="!imageUrl"
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
                :src="imageUrl"
                :alt="imageFile.name"
                class="detect-tobacco-image-index_upload-tobacco-image"
            />
        </div>
        <div class="detect-tobacco-image-index_header">
            <div class="detect-tobacco-image-index_header_title-box">AI建议</div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { upload } from "@/api/detect/image";
import { ref } from "vue";
import UploadIcon from "~icons/ri/upload-2-line?width=26&height=26";


const imageUrl = ref(""); // 图片地址
const imageFile = ref(); // 图片文件
// 检测参数
const detectionParam = ref({
    model: null,
    weights: null,
    ai: null,
    minConfidence: 0.5
});


function beforeUpload(file) {
    // 上传图片前处理方法
    handleFileSelect(file);
    return false;
}

function handleFileSelect(file: File) {
    imageFile.value = file;
    customUpload();
}

function customUpload() {
    // 自定义上传方法
    upload(imageFile.value).then(res => {
        imageUrl.value = res.data;
    });
}

function handleDetection() {
    // TODO
}

function handleExport() {
    // TODO
}

function handleReset() {
    // TODO
    detectionParam.value = {
        model: null,
        weights: null,
        ai: null,
        minConfidence: 0.5
    };
    imageUrl.value = "";
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
