<template>
    <div>
        <div class="table-page-search-wrapper">
            <a-form layout="inline">
                <a-row :gutter="48">
                    <a-col :md="8" :sm="24">
                        <a-form-item label="识别时间">
                            <a-input v-model="queryParam.id" placeholder="请输入识别时间"/>
                        </a-form-item>
                    </a-col>
                    <a-col :md="8" :sm="24">
                        <a-form-item label="识别结果">
                            <a-select v-model="queryParam.status" placeholder="请输入识别结果" default-value="0">
                                <a-select-option value="0">全部</a-select-option>
                                <a-select-option value="1">关闭</a-select-option>
                                <a-select-option value="2">运行中</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
                    </a-col>
                </a-row>
            </a-form>
        </div>

        <div class="table-operator">
            <a-dropdown v-action:edit v-if="selectedRowKeys.length > 0">
                <a-menu slot="overlay">
                    <a-menu-item key="1"><a-icon type="delete" />删除</a-menu-item>
                    <!-- lock | unlock -->
                    <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px">
                    批量操作 <a-icon type="down" />
                </a-button>
            </a-dropdown>
        </div>

        <s-table
            ref="table"
            size="default"
            rowKey="key"
            :columns="columns"
            :data="loadData"
            :alert="options.alert"
            :rowSelection="options.rowSelection"
        >
            <span slot="serial" slot-scope="text, record, index">
                {{ index + 1 }}
            </span>
            <span slot="originalImage" slot-scope="text, record">
                <img :src="record.originalImage" style="max-width: 300px; max-height: 200px;"/>
            </span>
            <span slot="predictedImage" slot-scope="text, record">
                <img :src="record.predictedImage" alt="预测图片" style="max-width: 300px; max-height: 200px;"/>
            </span>
            <span slot="operation" slot-scope="text, record">
              <template>
                <a @click="handleEdit(record)">编辑</a>
                <a-divider type="vertical" />
                <a @click="handleSub(record)">删除</a>
              </template>
            </span>
        </s-table>
    </div>
</template>

<script>
import moment from 'moment'
import { STable } from '@/components'
import { getRoleList } from '@/api/manage'
import { fetch } from '@/api/detect/imageRecord/index'

export default {
    name: 'TableList',
    components: {
        STable
    },
    data () {
        return {
            mdl: {},
            // 高级搜索 展开/关闭
            advanced: false,
            // 查询参数
            queryParam: {},
            // 表头
            columns: [
                {
                    title: '序号',
                    scopedSlots: { customRender: 'serial' }
                },
                {
                    title: '原始图片',
                    dataIndex: 'originalImage',
                    scopedSlots: { customRender: 'originalImage' }
                },
                {
                    title: '预测图片',
                    dataIndex: 'predictedImage',
                    scopedSlots: { customRender: 'predictedImage' }
                },
                {
                    title: '识别权重',
                    dataIndex: 'recognitionWeight',
                    needTotal: true
                },
                {
                    title: '最小阈值',
                    dataIndex: 'minThreshold',
                    sorter: true
                },
                {
                    title: 'AI助手',
                    dataIndex: 'aiAssistant',
                    width: '150px'
                },
                {
                    title: 'AI建议',
                    dataIndex: 'aiSuggestion'
                },
                {
                    title: '识别时间',
                    dataIndex: 'recognitionTime',
                    customRender: (text, record) => {
                        if (text) {
                            return moment(text).format('YYYY-MM-DD HH:mm:ss')
                        }
                        return '-'
                    }
                },
                {
                    title: '识别用户',
                    dataIndex: 'recognitionUser'
                },
                {
                    title: '操作',
                    dataIndex: 'operation',
                    scopedSlots: { customRender: 'operation' }
                }
            ],
            // 加载数据方法 必须为 Promise 对象
            loadData: parameter => {
                console.log('loadData.parameter', parameter)
                return fetch(Object.assign(parameter, this.queryParam))
                    .then(res => {
                        if (res.code === 0) {
                            return res.data
                        } else {
                            return {}
                        }
                    })
            },
            selectedRowKeys: [],
            selectedRows: [],

            // custom table alert & rowSelection
            options: {
                alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
                rowSelection: {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            },
            optionAlertShow: false
        }
    },
    created () {
        this.tableOption()
        getRoleList({ t: new Date() })
    },
    methods: {
        tableOption () {
            if (!this.optionAlertShow) {
                this.options = {
                    alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
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

        handleEdit (record) {
            this.$emit('onEdit', record)
        },
        handleOk () {

        },

        onSelectChange (selectedRowKeys, selectedRows) {
            this.selectedRowKeys = selectedRowKeys
            this.selectedRows = selectedRows
        },
        toggleAdvanced () {
            this.advanced = !this.advanced
        },

        resetSearchForm () {
            this.queryParam = {
                date: moment(new Date())
            }
        }
    }
}
</script>
