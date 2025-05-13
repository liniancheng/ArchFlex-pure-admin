<template>
  <!-- 消息详细 -->
  <div class="messageDetails">
    <h2>{{ messageInfo.title }}</h2>
    <hr />
    <a-row>
      <a-col :span="5"></a-col>
      <a-col :span="14">
        <span v-html="messageInfo.content">{{ messageInfo.content }}</span>
        <br />
        <br />
        <br />
        <p class="text_right">时间：{{ messageInfo.time | dateFrm }}</p>
        <p class="text_right">发布人：{{ messageInfo.user }}</p>
        <br />
        <br />
        <a-row class="enclosure">
          <span>附件：</span>
          <div v-for="(i, index) in attrList" :key="index">
            <a-col class="ant-col_" :span="5">
              <a-row :gutter="[10,10]">
                <a-col class="col_span" :span="10">
                  <img :src="i.image" />
                  <span style="color:#0097f4;margin-top:10px;margin-left: 10px;">
                    <a @click="handleExport(i)">下载</a>
                  </span>
                </a-col>
                <a-col class="col_span" :span="14">
                  <a-tooltip>
                    <template slot="title">
                      {{ i.name }}
                    </template>
                    <span style="margin-top:10px">{{ i.name }}</span>
                  </a-tooltip>

                </a-col>
              </a-row>
            </a-col>
          </div>
          <div v-if="attrList !== undefined && attrList.length === 0">
            <span style="margin-top:1px;margin-left: 10px;">无</span>
          </div>
        </a-row>
        <br />
        <br />
        <a-button shape="round" type="primary" @click="returnClick()" style="float:right">返回</a-button>
      </a-col>
      <a-col :span="5"></a-col>
    </a-row>
  </div>
</template>
<script>
import word from '@/assets/img/word_att.png'
import pdf from '@/assets/img/pdf_att.png'
import other from '@/assets/img/other_att.png'
import img from '@/assets/img/img_att.png'
import rar from '@/assets/img/rar_att.png'
import excle from '@/assets/img/excle_att.png'
import ppt from '@/assets/img/ppt_att.png'
import { fetchAttachList } from '@/api/config/anno/index'
import moment from 'moment'
export default {
  props: {
    messageInfo: { type: Object, required: true }
  },
  data () {
    return {
      attrList: []
    }
  },
   // 处理公告相关 begin
  filters: {
    dateFrm: function (e1) {
      return moment(e1).format('YYYY年MM月DD日')
    }
  },
  mounted () {
    if (this.messageInfo) {
      if (this.messageInfo.type !== 'mess') {
        this.getAttract(this.messageInfo.id)
      }
    }
  },
  methods: {
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
    getAttract (id) {
      fetchAttachList(id).then(res => {
         const arr = []
         if (res.code === 0) {
          res.data.forEach(item => {
            item.image = this.getFileType(item.name)
            arr.push(item)
            // this.mdl.attachList = arr
            this.attrList = arr
          })
        }
         /// ////////////////////////////////////////////////////////
        if (res.code === 0) {
          this.attrList = res.data
        }
      })
    },
    returnClick () {
      this.$emit('showMessage', this.messageInfo.type)
    }
  }
}
</script>
