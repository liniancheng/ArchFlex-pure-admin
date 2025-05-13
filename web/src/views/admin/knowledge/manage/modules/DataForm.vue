<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="60%"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col span="12">
            <a-form-item label="知识标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="知识标题"
                v-decorator="['knowledgeTitle', { initialValue: mdl.knowledgeTitle, rules: [
                  {
                    required: true,
                    message: '请填写知识标题！'
                  },
                  {
                    pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                    message:i18nRenderByParams('prompt.nameAndLength', '50')
                  }] }]" />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="知识描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="知识描述"
                v-decorator="['knowledgeRmk', { initialValue: mdl.knowledgeRmk,
                }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <a-form-item label="关键词" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="关键词"
                v-decorator="['keyWords', { initialValue: mdl.keyWords,
                }]"
              />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="有效状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-model="mdl.knowledgeStatus" >
                <a-radio-button value="1">有效</a-radio-button>
                <a-radio-button value="0">无效</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <a-form-item label="知识类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-tree-select
                tree-default-expand-all
                v-decorator="['dirId', { initialValue: mdl.dirId, rules: [
                  {
                    required: true,
                    message: '请选择知识类型'
                  } ] }]"
                :tree-data="options"
                :checkStrictly="checkStrictly"
                :show-checked-strategy="SHOW_PARENT"
                search-placeholder="Please select"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item
          label="知识内容"
          :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
          :wrapperCol="{ xs: { span: 24 }, sm: { span: 20 } }">
          <editor-bar
            ref="editor"
            :isClear="isClear"
            @change="onChange"
            v-decorator="['knowledgeContent', { initialValue: mdl.knowledgeContent, rules: [
              { required: true, message: '请输入知识内容' }
            ]
            }]"/>

        </a-form-item>
        <a-row>
          <a-col span="12">
            <a-form-item label="附件上传" :labelCol="labelCol" :wrapperCol="wrapperCol">
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
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import EditorBar from '@/components/Editor/WangEditor'
import { i18nRenderByParams } from '@/locales'
import { TreeSelect } from 'ant-design-vue'
import { getTreeNodes } from '@/api/admin/dir/index'
import { save, updateById, uploadAttach, removeAtt } from '@/api/admin/knowledge/index'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
export default {
  components: { EditorBar },
  data () {
    return {
      labelCol: {
        xs: { span: 24 }, sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 }, sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      checkStrictly: false,
      mdl: {},
      options: [],
      knowledgeContentEditType: null,
      addKnowledgeId: null,
      modalTitle: '',
      modalWidth: window.innerWidth * 0.5,
      type: 1,
      SHOW_PARENT,
      formData: null,
      isUpload: false,
      fileList: [],
      isClear: false,
      form: this.$form.createForm(this)
    }
  },
  mounted () {
    this.loadTreeList()
  },
  methods: {
    i18nRenderByParams,
    add (record) {
      this.modalTitle = '新增 - 知识库'
      this.fileList = []
      this.isUpload = false
      this.visible = true
      this.mdl = Object.assign({}, record)
      this.type = 1
      this.form.resetFields()
    },
    edit (record) {
      this.form.resetFields()
      this.fileList = []
      this.isUpload = false
      this.modalTitle = '修改 - 知识库'
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.fileList = record.fileList
      this.type = 2
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.type === 1) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
                if (res.code === 0) {
                    this.uploadAttach(res.data)
                    this.handleResponse(res)
                } else {
                  this.$message.error(res.message)
                }
            }).finally(() => {
                this.confirmLoading = false
              })
          } else {
            updateById(Object.assign({ ...this.mdl, ...values })).then(res => {
               if (res.code === 0) {
                    this.uploadAttach(res.data)
                    this.handleResponse(res)
                } else {
                  this.$message.error(res.message)
                }
            }).finally(() => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    loadTreeList () {
      getTreeNodes().then((res) => {
        if (res.code === 0) {
              this.options = res.data
        } else {
          console.log(res.message)
        }
      })
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.code === 0) {
        this.$message.success('操作成功！')
        this.visible = false
        this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
      this.confirmLoading = false
    },
    // 附件相关操作
    uploadAttach (data) {
        if (this.isUpload) {
                this.formData.append('knowledgeId', data)
                uploadAttach(this.formData).then((res) => {
                  if (res.code !== 0) {
                      this.$message.error(res.message)
                    }
                }).catch(function (error) {
                  this.$message.error(error)
                })
          }
    },
     // 附件相关操作
    onRemove (file) {
      const is = (file.uid.constructor === Object)
      // this.$message.success('==is==' + is)
      if (!is) {
          removeAtt(file.uid).then(res => {
            if (res.code === 0) {
              this.fileList = this.fileList.filter(item => item.uid !== file.uid).map(item => item)
              this.$message.success('移除附件成功!')
            }
          }).catch(() => {
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
    customRequest (data) { // 上传提交
      this.isUpload = true
      this.fileList = [...this.fileList, { uid: data.file.uid, name: data.file.name, status: 'done' }]
    },
    onChange (v) {
       this.form.setFieldsValue({ 'knowledgeContent': v })
    }
  }
}
</script>
