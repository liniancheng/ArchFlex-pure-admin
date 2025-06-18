<template>
    <div>
        <div class="header">
            <div class="title-box">烟虫检测</div>
        </div>
        <div class="table-page-search-wrapper">
            <a-form layout="inline">
                <a-row :gutter="48">
                    <a-col :md="3" :sm="24">
                        <a-form-item label="模型">
                            <a-select v-model="queryParam.model" placeholder="请选择模型" default-value="0">
                                <a-select-option value="0">yolo</a-select-option>
                                <a-select-option value="1">rtdetr</a-select-option>
                                <a-select-option value="2">deim</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :md="3" :sm="24">
                        <a-form-item label="权重文件">
                            <a-select v-model="queryParam.weights" placeholder="请选择权重" default-value="0">
                                <a-select-option value="0">best.onnx</a-select-option>
                                <a-select-option value="1">yolov8n.pt</a-select-option>
                                <a-select-option value="2">yolov11n.pt</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :md="3" :sm="24">
                        <a-form-item label="AI助手">
                            <a-select v-model="queryParam.ai" placeholder="请选择AI助手" default-value="0">
                                <a-select-option value="0">DeepSeek</a-select-option>
                                <a-select-option value="1">Qwen</a-select-option>
                                <a-select-option value="2">不使用AI</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :md="8" :sm="24">
                        <a-form-item label="最小置信度阈值">
                            <slider v-model="queryParam.minConfidence" :max="1" :min="0" :step="0.1"></slider>
                        </a-form-item>
                    </a-col>
                    <a-col :md="!advanced && 6 || 24" :sm="24">
                        <span
                            class="table-page-search-submitButtons"
                            :style="advanced && { float: 'right', overflow: 'hidden' } || {} "
                        >
                            <a-button type="primary" @click="$refs.table.refresh(true)">开始预测</a-button>
                            <a-button type="primary" style="margin-left: 8px" @click="$refs.table.refresh(true)">PDF导出</a-button>
                            <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
                        </span>
                    </a-col>
                </a-row>
            </a-form>
        </div>

        <div class="table-operator">
            <a-dropdown v-action:edit v-if="selectedRowKeys.length > 0">
                <a-menu slot="overlay">
                    <a-menu-item key="1">
                        <a-icon type="delete"/>
                        删除
                    </a-menu-item>
                    <!-- lock | unlock -->
                    <a-menu-item key="2">
                        <a-icon type="lock"/>
                        锁定
                    </a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px">
                    批量操作
                    <a-icon type="down"/>
                </a-button>
            </a-dropdown>
        </div>
        <div class="upload-container">
            <a-upload
                v-if="!imageUrl"
                name="file"
                :maxCount="1"
                :showUploadList="false"
                :beforeUpload="beforeUpload"
            >
                <div class="upload-box">
                    <a-icon type="upload" style="font-size: 48px; color: #888;"/>
                    <div style="margin-top: 8px;">点击上传</div>
                </div>
            </a-upload>
            <img
                v-else
                :src="imageUrl"
                :alt="imageFile.name"
                class="tobacco__update-image"
            />
        </div>
        <div class="ai-advice">
            <div class="title-box">AI建议</div>
        </div>
    </div>
</template>

<script>
import { getRoleList, getServiceList } from '@/api/manage'
import { upload } from '@/api/detect/image'
import { Slider } from 'ant-design-vue'
import { STable } from '@/components'
import moment from 'moment'

export default {
    name: 'TableList',
    components: {
        STable,
        Slider
    },
    data() {
        return {
            mdl: {},
            // 高级搜索 展开/关闭
            advanced: false,
            // 查询参数
            queryParam: {
                model: null,
                ai: null,
                minConfidence: 0.5
            },
            // 加载数据方法 必须为 Promise 对象
            loadData: parameter => {
                console.log('loadData.parameter', parameter)
                return getServiceList(Object.assign(parameter, this.queryParam))
                    .then(res => {
                        return res.result
                    })
            },
            selectedRowKeys: [],
            selectedRows: [],
            imageFile: null,
            imageUrl: null,

            // custom table alert & rowSelection
            options: {
                alert: {
                    show: true,
                    clear: () => {
                        this.selectedRowKeys = []
                    }
                },
                rowSelection: {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            },
            optionAlertShow: false
        }
    },
    created() {
        this.tableOption()
        getRoleList({ t: new Date() })
    },
    methods: {
        tableOption() {
            if (!this.optionAlertShow) {
                this.options = {
                    alert: {
                        show: true,
                        clear: () => {
                            this.selectedRowKeys = []
                        }
                    },
                    rowSelection: {
                        selectedRowKeys: this.selectedRowKeys,
                        onChange: this.onSelectChange
                    }
                }
                this.optionAlertShow = true
            } else {
                this.options = {
                    alert: false,
                    rowSelection: null
                }
                this.optionAlertShow = false
            }
        },

        handleEdit(record) {
            this.$emit('onEdit', record)
        },
        handleOk() {

        },

        beforeUpload(file) {
            // 上传图片前处理方法
            this.handleFileSelect(file)

            return false
        },
        handleFileSelect(file) {
            this.imageFile = file
            this.customUpload()
        },
        customUpload() {
            // 自定义上传方法
            const me = this
            upload(me.imageFile).then(res => {
                me.imageUrl = res.data
            })
        },

        onSelectChange(selectedRowKeys, selectedRows) {
            this.selectedRowKeys = selectedRowKeys
            this.selectedRows = selectedRows
        },
        toggleAdvanced() {
            this.advanced = !this.advanced
        },

        resetSearchForm() {
            this.queryParam = {
                date: moment(new Date())
            }
        }
    }
}
</script>

<style scoped>
.header, .ai-advice {
    border-bottom: 2px solid #1890ff; /* 蓝色边框 */
    padding-bottom: 12px;
    margin-bottom: 24px;
    margin-top: 24px;
    text-align: center;
    font-size: 24px; /* 设置字体大小为24像素 */
}

.title-box {
    display: inline-block;
    padding: 8px 16px;
    color: #fff; /* 白色文字 */
    background-color: #1890ff; /* 蓝色背景 */
    border-radius: 4px; /* 圆角边框 */
    font-weight: bold;
}

.upload-container {
    width: 100%;
    height: 500px;
    border: 1px dashed #d9d9d9;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fafafa;
    cursor: pointer;
    margin-top: 20px;
}

.upload-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    color: #888;
}

.tobacco__update-image {
    height: 100%;
}
</style>
