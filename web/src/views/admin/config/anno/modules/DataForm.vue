<template>
  <a-modal
    ref="modal"
    :title="modalTitle"
    :visible="visible"
    width="60%"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    class="notice"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item label="公告标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入公告标题"
                v-decorator="[
                  'annoTitle',
                  {
                    initialValue: mdl.annoTitle,
                    rules: [
                      { required: true, message: '请输入公告标题' },
                      {
                        pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-.]{1,50}$/,
                        message: i18nRenderByParams('prompt.nameAndLength', '50')
                      }
                    ]
                  }
                ]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="公告类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                placeholder="请选择公告类型"
                optionFilterProp="children"
                v-decorator="[
                  'typeId',
                  { initialValue: mdl.typeId, rules: [{ required: true, message: '请选择公告类型' }] }
                ]"
              >
                <a-select-option v-for="(tp, tpindex) in typeList" :key="tpindex" :value="tp.typeId">
                  {{ tp.typeName }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item label="公告等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                v-decorator="[
                  'annoLevel',
                  { initialValue: mdl.annoLevel, rules: [{ required: true, message: '请选择公告等级' }] }
                ]"
              >
                <a-radio value="1">重要</a-radio>
                <a-radio value="0">普通</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="是否有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                v-decorator="[
                  'isValid',
                  { initialValue: mdl.isValid, rules: [{ required: true, message: '请选择是否有效' }] }
                ]"
              >
                <a-radio value="1">是</a-radio>
                <a-radio value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item label="生效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                show-time
                placeholder="生效时间"
                @openChange="handleStartOpenChange"
                v-decorator="[
                  'startTime',
                  { initialValue: formatDate(mdl.startTime), rules: [{ required: true, message: '请选择生效时间' }] }
                ]"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="失效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                show-time
                placeholder="失效时间"
                :open="endOpen"
                @openChange="handleEndOpenChange"
                v-decorator="[ 'endTime', { initialValue: formatDate(mdl.endTime), rules: [
                  { required: true, message: '请选择失效时间' }, { validator: validateEnd }]
                } ]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="24" :sm="32">
            <a-form-item
              label="公告内容"
              :labelCol="{ xs: { span: 24 }, sm: { span: 2 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 20 } }"
            >
              <editor-bar
                ref="editor"
                :isClear="isClear"
                @change="onChange"
                v-decorator="[
                  'annoContent',
                  { initialValue: mdl.annoContent, rules: [{ required: true, message: '请输入公告内容' }] }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-form-item
            label="附件上传"
            :labelCol="{ xs: { span: 24 }, sm: { span: 2 } }"
            :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
          >
            <a-upload
              name="file"
              :multiple="true"
              :remove="onRemove"
              :before-upload="beforeUpload"
              :fileList="fileList"
              :customRequest="customRequest"
            >
              <a-button type="primary"> <a-icon type="upload" />附件</a-button>
            </a-upload>
          </a-form-item>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import { save, updateById, uploadAttach, deleteAttach } from '@/api/config/anno/index'
import { fetAll } from '@/api/config/annoType/index'
import EditorBar from '@/components/Editor/WangEditor'
import { i18nRenderByParams } from '@/locales'
export default {
  components: { EditorBar },
  data () {
    return {
      labelCol: { xs: { span: 24 }, sm: { span: 5 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 } },
      format: 'YYYY-MM-DD HH:mm:ss',
      visible: false,
      confirmLoading: false,
      showUploadList: true,
      mdl: {},
      modalTitle: '',
      typeList: [],
      endOpen: false,
      type: 1,
      formData: null,
      isUpload: false,
      isClear: false,
      form: this.$form.createForm(this),
      fileList: []
    }
  },
  methods: {
    i18nRenderByParams,
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    },
    initialTypeList () {
      fetAll().then(res => {
        if (res.code === 0) {
          this.typeList = res.data
        }
      })
    },
    add (record) {
      this.form.resetFields()
      this.modalTitle = '新增 - 公告'
      this.mdl = Object.assign({}, record)
      this.isUpload = false
      this.fileList = []
      this.initialTypeList()
      this.type = 1
    },
    edit (record) {
      this.form.resetFields()
      this.modalTitle = '修改 - 公告'
      this.mdl = Object.assign({}, record)
      this.isUpload = false
      this.fileList = []
      this.initialTypeList()
      this.mdl.isValid = this.mdl.isValid + ''
      this.fileList = record.fileList
      this.visible = true
      this.type = 2
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    validateEnd (rule, value, callback) {
      const form = this.form
      const startTime = form.getFieldValue('startTime')
      if (value !== undefined && value !== undefined) {
        if (startTime > value) {
          callback(new Error('失效时间不能小于生效时间，请检查!'))
        } else {
          callback()
        }
      }
    },
    formatDate (d) {
      if (d === undefined) {
        return moment(new Date(), this.format)
      }
      return moment(d, this.format)
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.type === 1) {
            save(Object.assign({ ...this.mdl, ...values }))
              .then(res => {
                if (res.code === 0) {
                  this.uploadAttach(res.data)
                  this.handleResponse(res)
                } else {
                  this.$message.error(res.message)
                }
              })
              .finally(() => {
                this.confirmLoading = false
              })
          } else {
            updateById(Object.assign({ ...this.mdl, ...values }))
              .then(res => {
                if (res.code === 0) {
                  this.uploadAttach(res.data)
                  this.handleResponse(res)
                } else {
                  this.$message.error(res.message)
                }
              })
              .finally(() => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    uploadAttach (data) {
      if (this.isUpload) {
        this.formData.append('annoId', data)
        uploadAttach(this.formData)
          .then(res => {
            if (res.code !== 0) {
              this.$message.error(res.message)
            }
          })
          .catch(function (error) {
            this.$message.error(error)
          })
      }
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.code === 0) {
        this.$message.success(res.message)
        this.visible = false
        this.form.resetFields()
        this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
      this.form.resetFields()
    },
    // 附件相关操作
    onRemove (file) {
      const is = file.uid.constructor === Object
      // this.$message.success('==is==' + is)
      if (!is) {
        deleteAttach(file.uid)
          .then(res => {
            if (res.code === 0) {
              this.fileList = this.fileList.filter(item => item.uid !== file.uid).map(item => item)
              this.$message.success('移除附件成功!')
            }
          })
          .catch(() => {
            this.$message.error('移除附件异常，请检查！')
          })
      } else {
        this.fileList = this.fileList.filter(item => item.uid !== file.uid).map(item => item)
        this.$message.success('移除附件成功!')
      }
    },
    beforeUpload (file, fileList) {
      const that = this
      that.formData = new FormData()
      return new Promise((resolve, reject) => {
        fileList.forEach(function (file, index, sysRoles) {
          if (file.size / 1024 / 1024 > 10) {
            that.$message.error(file.name + '大于10M，请检查！')
            // eslint-disable-next-line prefer-promise-reject-errors
            reject()
          } else {
            that.formData.append('file', file)
            resolve()
          }
        })
      })
    },
    customRequest (data) {
      // 上传提交
      this.isUpload = true
      this.fileList = [...this.fileList, { uid: data.file.uid, name: data.file.name, status: 'done' }]
    },
    onChange (v) {
      this.form.setFieldsValue({ annoContent: v })
    }
  }
}
</script>
<style lang="less">
.notice {
  .ant-col-sm-32 {
    > .ant-row {
      > .ant-col-sm-2 {
        width: 10.333333%;
      }
      > .ant-col-sm-20 {
        width: 87.5%;
      }
    }
  }
  .ant-upload {
    margin-left: 3%;
  }
}
</style>
