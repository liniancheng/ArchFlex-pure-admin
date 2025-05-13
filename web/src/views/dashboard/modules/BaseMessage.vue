<template>
  <a-card
    class="content_zh_item a_card imoia-message-card"
    :tab-list="messageTabList"
    :activeTabKey="messageTabKey"
    @tabChange="key => onMessageTabChange(key)"
    :bodyStyle="bodyStyle"
    :loading="loading"
  >
    <div slot="title">
      <iconMsg width="20px" height="20px"/>消息公告
    </div>
    <span slot="customRender" slot-scope="item">{{ item.key }}</span>
    <iconMore slot="extra" width="30px" height="30px" @click="gotoPersonCenter(3)"/>

    <!-- <img slot="extra" src="~@/assets/img/11115.svg" @click="gotoPersonCenter(3)" /> -->
    <a-list class="imoia-task-list" size="small" :data-source="currentList" :split="split">
      <a-list-item slot="renderItem" slot-scope="item" @click="messageClick(item)" class="a_card_list">
        <a-list-item-meta>
          <div slot="title" class="a_card_top">
            <i :class="'i' + item.level" />
            <span>{{ item.title }}</span>
          </div>
          <div slot="description" class="a_card_btn">
            <span v-html="item.content" />
          </div>
        </a-list-item-meta>
      </a-list-item>
    </a-list>
    <a-modal
      v-model="messageVisible"
      title="消息公告"
      @ok="handleOk"
      class="messageModal"
      width="800px"
    >
      <template slot="footer">
        <a-button style="margin-left: 8px" type="primary" @click="handleOk">确定</a-button>
      </template>
      <h2>{{ mdl.title }}</h2>
      <hr />
      <a-row>
        <a-col :span="24">
          <p>
            <vue-scroll
              :ops="{
                bar: { size: '6px', background: '#999', keepShow: true },
                scrollPanel: { scrollingX: false, maxHeight: 350 }
              }"
            >
              <span v-html="mdl.content">{{ mdl.content }}</span>
            </vue-scroll>
          </p>
          <br />
          <p class="text_right">时间：{{ mdl.fromTime | dateFrm }}</p>
          <p class="text_right">发布人：{{ mdl.fromUserId }}</p>
          <br />
          <a-row class="enclosure">
            <span>附件：</span>

            <div v-for="(i, index) in mdl.attachList" :key="index">
              <a-col class="ant-col_" :span="5">
                <a-row :gutter="10">
                  <a-col class="col_span" :span="10">
                    <img v-if="i.name.endsWith('.xls') || i.name.endsWith('.xlsx')" src="~@/assets/img/excle_att.png" />
                    <img
                      v-else-if="i.name.endsWith('.doc') || i.name.endsWith('.docx')"
                      src="~@/assets/img/word_att.png"
                    />
                    <img v-else-if="i.name.endsWith('.pdf')" src="~@/assets/img/pdf_att.png" />
                    <img
                      v-else-if="i.name.endsWith('.rar') || i.name.endsWith('.zip') || i.name.endsWith('.jar')"
                      src="~@/assets/img/rar_att.png"
                    />
                    <img v-else-if="i.name.endsWith('.ppt')" src="~@/assets/img/ppt_att.png" />
                    <img
                      v-else-if="
                        i.name.endsWith('.bmp') ||
                          i.name.endsWith('.png') ||
                          i.name.endsWith('.gif') ||
                          i.name.endsWith('.tif') ||
                          i.name.endsWith('.jpg') ||
                          i.name.endsWith('.svg')
                      "
                      src="~@/assets/img/img_att.png"
                    />
                    <img v-else src="~@/assets/img/other_att.png" />

                    <span style="color:#0097f4;margin-top:10px;margin-left: 10px;">
                      <a @click="handleExport(i)">下载</a>
                    </span>
                  </a-col>
                  <a-col class="col_span" :span="14">
                    <a-tooltip>
                      <template slot="title">{{ i.name }}</template>
                      <span style="margin-top:10px">{{ i.name }}</span>
                    </a-tooltip>
                  </a-col>
                </a-row>
              </a-col>
            </div>
            <div v-if="mdl.attachList && mdl.attachList.length === 0">
              <span style="margin-top:1px;margin-left: 10px;">无</span>
            </div>
          </a-row>
        </a-col>
      </a-row>
    </a-modal>
  </a-card>
</template>
<script>
import { download } from '@/api/admin/home'
import { fetchAttachList } from '@/api/config/anno/index'
import events from '@/utils/events'
import moment from 'moment'
import { iconMsg, iconMore } from '@/core/icons'
export default {
  name: 'BaseMessage',
  components: {
    iconMsg,
    iconMore
  },
  data () {
    return {
      split: false,
      dataSource: this.$store.getters.listMsg,
      bodyStyle: { height: '100%', width: '100%', overflow: 'hidden', padding: '0 15px' },
      currentList: this.$store.getters.listMsg['anno'],
      messageTabList: [
        { key: 'anno', tab: '系统公告', scopedSlots: { tab: 'customRender' } },
        { key: 'mess', tab: '我的消息' }
      ],
      messageTabKey: 'anno',
      messageVisible: false,
      mdl: {},
      loading: true
    }
  },
  filters: {
    dateFrm: function (e1) {
      return moment(e1).format('YYYY年MM月DD日')
    }
  },
  mounted () {
    setTimeout(() => {
      this.loading = false
    }, 200)
  },
  methods: {
    // 点击消息详情
    messageClick (record) {
      this.mdl = record
      fetchAttachList(record.id).then(res => {
        if (res.code === 0) {
          this.mdl.attachList = res.data
        }
      })
      this.$nextTick(() => {
        this.messageVisible = true
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
    handleOk (e) {
      this.messageVisible = false
    },
    onMessageTabChange (key) {
      this.messageTabKey = key
      this.currentList = this.dataSource[key]
    },
    gotoPersonCenter (type) {
      events.$emit('OnPersonTabChange', type)
      this.$store.commit('setRouteParam', type)
      this.$router.push({ name: 'menu.person' })
    }
  }
  // watch: {
  //   dataSource: {
  //     handler (newValue, oldValue) {
  //       this.dataSource = newValue
  //       this.currentList = newValue[this.messageTabKey]
  //     }
  //   }
  // }
}
</script>
<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

.content_zh_item {
  .ant-card-head-title,.ant-card-extra {
    color: @primary-color;
  }
  .ant-tabs-nav .ant-tabs-tab-active {
    background: @primary-color;
  }
}
.imoia-message-card {
  .ant-card-head-title {
    height: 24px;
    line-height: 24px;
  }
  .ant-card-head-wrapper {
    width: 100%;
    line-height: 28px;
  }
  .ant-tabs-nav {
    float: right;
    margin-right: 20px;
    margin-top: 5px;
  }
  .ant-tabs-nav .ant-tabs-tab-active {
    box-shadow: 0px 0px 5px 0px rgba(72, 119, 230, 0.5);
    border-radius: 50px;
    color: #fff !important;
  }
  .ant-tabs-nav .ant-tabs-tab-active:hover {
    color: #fff;
  }
  .ant-tabs-nav .ant-tabs-tab {
    color: #999;
    font-size: 12px;
  }
  .ant-tabs .ant-tabs-large-bar .ant-tabs-tab {
    padding: 0px 8px;
    margin: 0 10px 0 0;
  }
  .ant-tabs-top .ant-tabs-ink-bar-animated,
  .ant-tabs-bottom .ant-tabs-ink-bar-animated {
    display: none !important;
  }
  .ant-card-head {
    height: 48px;
  }
  .ant-card-head-title {
    padding: 0px;
    font-size: 14px;
    font-weight: bold;
  }
  .ant-card-extra {
    padding: 0px;
  }
  .ant-card-body {
    padding: 0;
  }

  .ant-card-head {
    .ant-tabs-large {
      position: absolute;
      top: 0;
      right: 55px;
      .ant-tabs-bar {
        border-bottom: none;
        height: 35px;
      }
      .ant-tabs-nav-scroll {
        height: 35px;
      }
    }
    .ant-card-head-tabs {
      .ant-tabs-ink-bar-animated {
        display: none !important;
      }
    }
  }
}

.imoia-task-list {
  overflow: hidden;
  .ant-list-item {
    padding: 0;
  }
}
.a_card_list {
  overflow: hidden;
  padding: 0;
  margin: 0;
  .a_card_top {
    height: 24px;
    margin-top: 10px;
    .i0 {
      float: left;
      display: block;
      width: 11px;
      height: 6px;
      background: #2d7be0;
      margin: 9px 14px 0 0;
    }
    .i1 {
      float: left;
      display: block;
      width: 11px;
      height: 6px;
      background: #f66c23;
      margin: 9px 14px 0 0;
    }
    span {
      float: left;
      font-size: 14px;
      font-weight: normal;
      color: rgba(51, 51, 51, 1);
      line-height: 24px;
      margin-right: 10px;
    }
    p {
      float: left;
      width: 40px;
      height: 17px;
      background: #f66c23;
      border-radius: 50px;
      color: #fff;
      font-size: 12px;
      text-align: center;
      display: block;
      margin-top: 4px;
    }
  }
  p {
    margin: 0;
  }
  .a_card_btn {
    float: left;
    font-size: 12px;
    font-weight: normal;
    color: #999999;
    line-height: 26px;
    border-bottom: 1px dashed #ebebeb;
    width: 100%;
    height: 60px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  li:nth-child(3) {
    .a_card_btn {
      border-bottom: 0px dashed #ebebeb;
      margin-bottom: 3px;
      padding-bottom: 0px;
    }
  }
}
.messageModal {
  h2 {
    color: #333;
    font-size: 18px;
    font-family: SourceHanSansCN;
    font-weight: bold;
    text-align: center;
  }
  hr {
    border: none;
    height: 2px;
    background-color: #ccc;
    margin: 25px 0 40px 0;
  }
  p {
    font-size: 14px;
    font-family: SourceHanSansCN;
    font-weight: 400;
    color: rgba(51, 51, 51, 1);
    margin: 0;
  }
  .text_right {
    text-align: right;
    color: #666666;
    font-size: 14px;
    font-family: SourceHanSansCN;
    font-weight: 400;
  }
  .enclosure {
    span {
      font-size: 12px;
      display: inline-block;
      white-space: nowrap;
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .ant-col_ {
      background: rgba(251, 251, 251, 1);
      border: 1px solid rgba(230, 235, 245, 1);
      margin-right: 16px;
      padding: 10px;
      margin-bottom: 10px;
    }
    .col_span {
      text-align: center;
    }
  }
}
</style>
