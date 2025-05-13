<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :width="modalWidth"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <a-list
      ref="list"
      class="loadmore-list"
      :loading="loading"
      item-layout="horizontal"
      :data-source="data"
    >
      <a-list-item slot="renderItem" slot-scope="item">
        <a-list-item-meta
          :description="item.attName"
        >
          <!--<a slot="title" href="#">#{{ index+1 }}：【{{ item.attId }}】</a>-->
          <a-avatar
            slot="avatar"
            :src="image"
          />
        </a-list-item-meta>
        <div>
          <template>
            <a class="table_blue" @click="handleExport(item.attId,item.attName)">【下载】</a>
            <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(item.attId,item.knowledgeId)">
              <a class="table_orange" >【删除】</a>
            </a-popconfirm>
          </template>
        </div>
      </a-list-item>
    </a-list>
  </a-modal>
</template>

<script>
import image from '@/assets/img/13418.gif'
import { fetchList, download, removeAtt } from '@/api/admin/knowledge/index'
export default {
  data () {
    return {
      modalTitle: '',
      confirmLoading: false,
      visible: false,
      loading: true,
      modalWidth: window.innerWidth * 0.5,
      loadingMore: false,
      image: image,
      data: []
    }
  },
  methods: {
     add (id) {
      this.modalTitle = '下载 - 知识库附件'
      this.visible = true
      this.loading = false
      this.getData(id)
    },
    handleExport (id, name) {
      // const fileName = '知识库附件_' + moment().format('YYYYMMDDhhmmss') + '.xlsx'
      download(id).then((data) => {
        if (!data) {
          this.$message.warning('文件下载失败')
          return
        }
        this.handleDownloadResponse(data, name)
      })
    },
    handleDelete (id, knowledgeId) {
        removeAtt(id).then(res => {
          if (res.code === 0) {
            this.$message.success(res.message)
            this.getData(knowledgeId)
          } else {
            this.$message.error(res.message)
          }
        })
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
    },
    getData (id) {
      fetchList(id).then((res) => {
        if (res.code === 0) {
              this.data = res.data
        } else {
          console.log(res.message)
        }
      })
    },
    handleDownloadResponse (res, name) {
      const content = res
      const blob = new Blob([content])
      const fileName = name
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
    }
  }
}
</script>
