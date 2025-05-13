<template>
  <div class="knowledgeDrowsing">
    <a-spin :spinning="confirmLoading">
      <div v-if="this.knowInfo.knowledgeId !== null">
        <a-card :bordered="false">
          <h2>{{ knowInfo.knowledgeTitle }}</h2>
          <a-divider />
          <div class="toolItem">
            <p>
              发布时间：<span>{{ knowInfo.createTime | dateFrm }}</span>
            </p>
            <p>
              发布人：<span>{{ knowInfo.createUser }}</span>
            </p>
          </div>
          <a-divider />
          <div class="content" v-html="knowInfo.knowledgeContent"></div>
          <a-row class="enclosure">
            <span>附件：</span>
            <div class="ant-col_" v-for="item in knowInfo.attachs" :key="item.id" @click="handleExport(item)">
              <a-row>
                <a-col class="col_span" :span="10">
                  <!-- <img src="~@/assets/img/excle.png  showImage" /> -->
                  <!-- <div v-if="showImg(item.name)"> -->
                  <img :src="item.image" />
                  <!-- </div> -->
                  <span class="download">
                    <a>下载</a>
                  </span>
                </a-col>
                <a-col class="col_span" :span="14">
                  <a-tooltip>
                    <template slot="title">
                      {{ item.name }}
                    </template>
                    <span style="margin-top: 10px;" class="title">{{ item.name }}</span>
                  </a-tooltip>
                </a-col>
              </a-row>
            </div>
            <div v-if="knowInfo.attachs !== undefined && knowInfo.attachs.length === 0">
              <span style="margin-top: 1px; margin-left: 10px; color: #333;">无</span>
            </div>
          </a-row>
          <a-divider />
          <div class="messageBoard_">
            <messageBoard :objId="knowId" ref="messageBoard"></messageBoard>
          </div>
        </a-card>
      </div>
      <div v-if="this.knowInfo.knowledgeId === null">
        <div class="knowledgeBase">
          <div class="knowledgeBaseSearch" v-if="typePage == 0">
            <a-input-search
              v-model="queryParam.knowledgeContentEditType"
              placeholder="请输入知识标题或内容进行查询"
              size="large"
              @search="onSearch"
            >
              <a-button slot="enterButton" type="primary" icon="search">搜索</a-button>
            </a-input-search>
            <a-button class="know_button" @click="share">知识分享</a-button>
            <a-list
              class="custom-ant-list"
              item-layout="vertical"
              size="large"
              :pagination="pagination"
              @change="handleTableChange"
              :data-source="listData"
              :split="false"
            >
              <a-list-item slot="renderItem" key="item.title" slot-scope="item">
                <template slot="actions">
                  <span>
                    <a class="ant-list-item-text" href="#">发布人：</a>
                    {{ item.createUser }}
                  </span>
                  <span>
                    <a class="ant-list-item-text" href="#">发布时间：</a>
                    {{ item.createTime | dateFrm }}
                  </span>
                </template>
                <a-list-item-meta>
                  <a
                    slot="title"
                    target="_blank"
                    @click="$router.push(`/know/view/${item.knowledgeId}?customTitle=${item.knowledgeTitle}`)"
                  >{{ item.knowledgeTitle }}
                  </a>
                </a-list-item-meta>
                <!-- <a slot="title" target="_blank" @click="jump(item.knowledgeId)">{{ item.knowledgeTitle }}</a> -->
                <div class="item-meta-content">
                  <span v-html="item.knowledgeContent">{{ item.knowledgeContent }}</span>
                </div>
              </a-list-item>
            </a-list>
            <a-spin :spinning="confirmLoading"> </a-spin>
          </div>
          <template v-if="typePage == 1">
            <a-steps :current="currentStep" labelPlacement="vertical">
              <a-step title="基本信息" />
              <a-step title="知识内容" />
              <a-step title="上传附件" />
            </a-steps>
            <a-form :form="form" ref="formName" v-if="isShow == 0">
              <a-form-item label="知识分类" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-tree-select
                  placeholder="请输入知识分类"
                  tree-default-expand-all
                  style="width: 100%;"
                  v-decorator="[
                    'dirId',
                    {
                      initialValue: mdl.dirId,
                      rules: [
                        {
                          required: true,
                          message: '请选择知识分类'
                        }
                      ]
                    }
                  ]"
                  :tree-data="options"
                  :checkStrictly="checkStrictly"
                  :show-checked-strategy="SHOW_PARENT"
                />
              </a-form-item>
              <a-form-item label="知识标题" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-input
                  style="width: 100%;"
                  placeholder="请输入知识标题"
                  v-decorator="[
                    'knowledgeTitle',
                    {
                      initialValue: mdl.knowledgeTitle,
                      rules: [
                        {
                          required: true,
                          message: '请输入知识标题'
                        },
                        {
                          pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9-]{1,50}$/,
                          message: i18nRenderByParams('prompt.nameAndLength', '50')
                        }
                      ]
                    }
                  ]"
                />
              </a-form-item>
              <a-form-item label="知识描述" :label-col="labelCol" :wrapper-col="{ span: 12 }">
                <a-textarea
                  class="textarea_input"
                  placeholder="请输入知识描述"
                  v-decorator="['knowledgeRmk', { initialValue: mdl.knowledgeRmk }]"
                />
              </a-form-item>
            </a-form>
            <editor-bar ref="editor" :isClear="isClear" @change="changeVal" v-if="isShow == 1" v-model="data" />
            <div class="upload" v-if="isShow == 2">
              <a-upload
                name="file"
                :multiple="true"
                :remove="onRemove"
                :before-upload="beforeUpload"
                :fileList="fileList"
                :customRequest="customRequest"
              >
                <a-button> <a-icon type="upload" />点击此处上传附件</a-button>
              </a-upload>
            </div>
            <div class="steps-action">
              <a-button v-if="currentStep > 0" @click="prev" type="primary">上一步</a-button>
              <a-button v-if="currentStep < 2" style="margin-left: 8px;" @click="next">下一步</a-button>
              <a-button v-if="currentStep === 2" style="margin-left: 8px;" @click="handleSubmit">完成</a-button>
              <a-button type="primary" style="margin-left: 8px;" @click="returns">返回</a-button>
            </div>
          </template>
        </div>
      </div>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment'
import { i18nRenderByParams } from '@/locales'
import word from '@/assets/img/word_att.png'
import pdf from '@/assets/img/pdf_att.png'
import other from '@/assets/img/other_att.png'
import img from '@/assets/img/img_att.png'
import rar from '@/assets/img/rar_att.png'
import excle from '@/assets/img/excle_att.png'
import ppt from '@/assets/img/ppt_att.png'
import { TreeSelect } from 'ant-design-vue'
import { getByKnowId } from '@/api/admin/knowledge/bus'
import { getTreeNodes } from '@/api/admin/dir/index'
import { save, download, fetch, uploadAttach, removeAtt } from '@/api/admin/knowledge/index'
// import { save, uploadAttach, removeAtt } from '@/api/admin/knowledge/index'
import messageBoard from '../../messageBoard/messageBoard'
import EditorBar from '@/components/Editor/WangEditor'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
const listData = []
export default {
  components: { messageBoard, EditorBar },
  data () {
    return {
      knowId: '',
      knowInfo: {},
      knowName: '',
      listData,
      fileList: [],
      formData: null,
      isUpload: false,
      confirmLoading: false,
      checkStrictly: false,
      SHOW_PARENT,
      options: [],
      titleList: [],
      typeId: null,
      mdl: {},
      image: null,
      pagination: {
        onChange: page => {},
        total: 0,
        showSizeChanger: true,
        pageSizeOptions: ['10', '20', '30', '40'], // 每页中显示的数据
        showTotal: total => `共有 ${total} 条数据` // 分页中显示总的数据
      },
      // 查询参数
      queryParam: {
        current: 1, // 第几页
        size: 10 // 每页中显示数据的条数
      },
      // 分享
      typePage: 0,
      currentStep: 0,
      steps: [
        {
          title: 'First',
          content: 'First-content'
        },
        {
          title: 'Second',
          content: 'Second-content'
        },
        {
          title: 'Last',
          content: 'Last-content'
        }
      ],
      // 第一步
      labelCol: { span: 2 },
      wrapperCol: { span: 7 },
      form: this.$form.createForm(this, {
        knowledgeTitle: '',
        knowledgeRmk: '',
        knowledgeContent: ''
      }),
      isShow: 0,
      headers: {
        authorization: 'authorization-text'
      },
      // 知识数据
      knowList: {},
      data: '',
      isClear: false
    }
  },
  mounted () {
    this.knowId = this.$route.params.id
    this.queryParam.dirId = null
    this.knowInfo.knowledgeId = null
    // 加载数据标准
    this.handleSelect()
    if (this.knowInfo.knowledgeId === null) {
      this.queryParam.dirId = this.$route.params.id
      this.loadTreeList()
      this.getTableList()
    } else {
      this.$refs.messageBoard.loadComment()
    }
  },
  watch: {
    $route (to, from) {
      this.queryParam.dirId = null
      this.knowInfo.knowledgeId = null
      if (to.path.indexOf('/know/view/') > -1) {
        this.knowId = to.params.id
        this.typePage = 0
        this.currentStep = 0
        this.isShow = 0
        this.mdl = {}
        this.handleSelect()
        if (this.knowInfo.knowledgeId === null) {
          this.queryParam.dirId = to.params.id
          this.loadTreeList()
          this.getTableList()
        }
      }
    }
  },
  filters: {
    dateFrm: function (e1) {
      return moment(e1).format('YYYY-MM-DD HH:mm:ss')
    }
  },
  methods: {
    i18nRenderByParams,
    getFileType (fileName) {
      // 后缀获取
      let suffix = ''
      // 获取类型结果
      let result = ''
      try {
        const flieArr = fileName.split('.')
        suffix = flieArr[flieArr.length - 1]
      } catch (err) {
        suffix = ''
      }
      // fileName无后缀返回 false
      if (!suffix) {
        return other
      }
      suffix = suffix.toLocaleLowerCase()
      // 图片格式
      const imglist = ['png', 'jpg', 'jpeg', 'bmp', 'gif']
      // 进行图片匹配
      result = imglist.find(item => item === suffix)
      if (result) {
        return img
      }

      // 匹配 excel
      const excelist = ['xls', 'xlsx']
      result = excelist.find(item => item === suffix)
      if (result) {
        return excle
      }
      // 匹配 word
      const wordlist = ['doc', 'docx']
      result = wordlist.find(item => item === suffix)
      if (result) {
        return word
      }
      // 匹配 pdf
      const pdflist = ['pdf']
      result = pdflist.find(item => item === suffix)
      if (result) {
        return pdf
      }
      // 匹配 ppt
      const pptlist = ['ppt', 'pptx']
      result = pptlist.find(item => item === suffix)
      if (result) {
        return ppt
      }
      // }
      // 匹配 压缩
      const radiolist = ['rar', 'zip', 'wmv']
      result = radiolist.find(item => item === suffix)
      if (result) {
        return rar
      }
      // 其他 文件类型
      return other
    },
    handleSelect () {
      getByKnowId(this.knowId).then(res => {
        const arr = []
        if (res.code === 0) {
          this.knowInfo = res.data
          if (res.data.attachs !== null) {
            res.data.attachs.forEach(item => {
              item.image = this.getFileType(item.name)
              arr.push(item)
            })
            this.knowInfo.attachs = null
            this.knowInfo.attachs = arr
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    getTableList () {
      this.confirmLoading = true
      fetch(this.queryParam).then(res => {
        const pagination = { ...this.pagination }
        pagination.total = res.data.total
        this.listData = res.data.records
        this.pagination = pagination
        this.confirmLoading = false
      })
    },
    handleTableChange (pagination) {
      this.pagination.current = pagination.current
      this.pagination.pageSize = pagination.pageSize
      this.queryParam.current = pagination.current
      this.queryParam.size = pagination.pageSize
      this.getTableList()
    }, // 调用查询接口为dataSource 赋值
    share () {
      this.form.resetFields()
      this.fileList = []
      this.isUpload = false
      this.data = null
      this.typePage = 1
      this.currentStep = 0
      this.isShow = 0
      this.mdl = {}
    },
    returns () {
      // this.form.resetFields()
      this.fileList = []
      this.isUpload = false
      this.data = null
      this.typePage = 0
      this.currentStep = 0
      this.isShow = 0
      this.mdl = {}
    },
    loadTreeList () {
      getTreeNodes().then(res => {
        if (res.code === 0) {
          this.options = res.data
        } else {
        }
      })
    },
    handleExport (record) {
      download(record.uid).then(res => {
        this.handleDownloadResponse(record.name, res)
      })
    },
    handleDownloadResponse (acceName, res) {
      const content = res
      const blob = new Blob([content])
      const fileName = acceName
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL 对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, fileName)
      }
      this.confirmLoading = false
    },
    onSearch (value) {
      // this.queryParam.knowledgeContentEditType = value
      this.getTableList()
    },
    change (val) {
      // this.data = val
      console.log(val, this.data)
    },
    next () {
      this.currentStep++
      if (this.currentStep === 0) {
        this.isShow = 0
      } else if (this.currentStep === 1) {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.isShow = 1
            // 保存知识数据
            this.mdl = values
          } else {
            this.currentStep--
          }
        })
      } else if (this.currentStep === 2) {
        if (this.data !== null && this.data !== undefined) {
          this.mdl.knowledgeContent = this.data
          this.isShow = 2
        } else {
          this.$message.error('请输入知识内容!')
          this.currentStep--
        }
      }
    },
    prev () {
      this.currentStep--
      if (this.currentStep === 0) {
        this.isShow = 0
      } else if (this.currentStep === 1) {
        this.isShow = 1
      } else if (this.currentStep === 2) {
        this.isShow = 2
      }
    },
    // 附件相关操作
    uploadAttach (data) {
      if (this.isUpload) {
        this.formData.append('knowledgeId', data)
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
    onRemove (file) {
      const is = file.uid.constructor === Object
      if (!is) {
        removeAtt(file.uid)
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
        fileList.forEach(function (file, index, fileList) {
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
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.mdl.knowledgeStatus = '1'
          save(this.mdl)
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
          this.confirmLoading = false
        }
      })
    },
    handleResponse (res) {
      if (res.code === 0) {
        this.$message.success('操作成功！')
        // this.visible = false
        // this.$emit('ok')
      } else {
        this.$message.error(res.message)
      }
    },
    changeVal (v) {
      this.data = v
    }
  }
}
</script>
<style lang="less">
@import '../../../../components/theme.less';
.knowledgeDrowsing {
  .knowledgeBase {
    .know_button.ant-btn {
      border: 1px solid @primary-color;
      color: @primary-color;
    }
  }
}

.knowledgeDrowsing {
  h2 {
    text-align: center;
  }
  .toolItem {
    height: 22px;
    > p {
      padding: 0;
      margin: 0;
      float: left;
      margin-right: 30px;
      font-size: 14px;
      color: #999;
    }
    span {
      color: #333;
    }
  }
  .ant-divider-horizontal {
    margin: 2px 0;
  }
  .content {
    padding: 20px 30px 60px 30px;
  }
  .enclosure {
    span {
      float: left;
      font-size: 14px;
      color: #999;
    }
    .ant-col_ {
      background: rgba(251, 251, 251, 1);
      border: 1px solid rgba(230, 235, 245, 1);
      margin-right: 16px;
      padding: 10px;
      margin-bottom: 10px;
      width: 190px;
      float: left;
    }
    .download {
      color: #0097f4;
      margin-top: 5px;
      margin-left: 20px;
    }
    .title {
      width: 100%;
      display: inline-block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .col_span {
      text-align: center;
    }
  }
  .knowledgeBase {
    background: rgba(255, 255, 255, 1);
    padding-bottom: 60px;
    .knowledgeBaseSearch {
      padding: 30px;
    }
    .ant-input-search {
      width: 532px;
      height: 32px;
      margin-bottom: 25px;
      .ant-input-lg {
        height: 32px;
      }
      .ant-btn-lg {
        height: 32px;
        padding: 0 28px;
      }
      // .ant-btn-primary {
      //   background-color: #46a9e6;
      //   border-color: #46a9e6;
      // }
    }
    .know_button.ant-btn {
      border-radius: 14px;
      float: right;
    }
    .ant-steps {
      padding: 45px 130px 10px 130px;
      background: #fcfcfc;
    }
    .ant-steps-label-vertical .ant-steps-item-icon {
      margin-left: 34px;
    }
    .ant-form {
      margin: 15px 0 0 0;
    }
    .textarea_input {
      width: 100%; /*自己主动适应父布局宽度*/
      overflow: auto;
      word-break: break-all;
      min-height: 160px;
    }
    #editor {
      margin: 30px;
    }
    .upload {
      width: 50%;
      margin: 30px;
      .ant-upload {
        width: 100%;
        .ant-btn {
          width: 100%;
          text-align: left;
          background: rgba(219, 225, 239, 1);
          border: 1px solid rgba(197, 203, 222, 1);
          color: #666;
          height: 30px;
          padding: 0 20px;
        }
      }
      .ant-upload-list {
        border-left: 1px solid rgba(197, 203, 222, 1);
        border-right: 1px solid rgba(197, 203, 222, 1);
        border-bottom: 1px solid rgba(197, 203, 222, 1);
        background: rgba(246, 248, 252, 1);
        min-height: 100px;
      }
    }

    .ant-list {
      max-height: inherit;
    }
    .ant-list-items {
      .ant-list-item {
        padding: 12px 20px;
      }
      .ant-list-item:nth-child(odd) {
        background: #f7f7f7;
      }
    }
    .steps-action {
      position: absolute;
      left: 30px;
    }
  }
  .messageBoard_ {
    margin-top: 10px;
    padding: 0 0 10px 10px;
    border: 1px solid #e8e8e8;
  }
  .description {
    width: 95%;
    margin-left: 40px;
    margin-top: 10px;
    .ant-avatar-image {
      position: absolute;
      left: 0;
    }
    > .ant-row {
      float: left;
      width: 100%;
    }
    .table_orange_ {
      color: #fc5959;
    }
  }
  .ant-editor-wang {
    padding: 30px;
  }
}
</style>
