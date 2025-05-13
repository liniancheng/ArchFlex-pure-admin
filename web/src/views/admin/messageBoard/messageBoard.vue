<template>
  <a-spin :spinning="loading">
    <vue-scroll>
      <div class="description" v-for="(item, index) in comments[objId]" :key="index">
        <a-avatar slot="avatar" :src="image" />
        <a-row>
          <a-col :span="20">
            <span class="comment_ comment">评论内容：{{ item.commentContent }}</span>
          </a-col>
        </a-row>
        <span class="comment">评论人：{{ item.userName }}</span>
        <span class="comment">评论时间：{{ item.createTime|dateFrm }}</span>
        <a-popconfirm v-if="item.myComment" title="确定要删除?" @confirm="() => removeComment(item.commentId)">
          <a class="table_orange">【删除】</a>
        </a-popconfirm>
      </div>
    </vue-scroll>
    <p v-if="showMores[objId]"><a @click="loadMore">加载更多</a></p>
    <a-input-search
      placeholder="请输入评论内容"
      enter-button="发表评论"
      size="large"
      v-model="searchName"
      @search="saveComment"
    />
  </a-spin>
</template>
<script>
import moment from 'moment'
import image from '@/assets/img/pl.png'
import { fetch, save, removeById } from '@/api/admin/user/comment'
export default {
  name: 'MessageBoard',
  props: {
    objId: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      image: image,
      loading: false,
      currentPage: 1,
      comments: {},
      loadedPages: {},
      showMores: {},
      searchName: ''
    }
  },
  filters: {
    dateFrm (e1) {
      return moment(e1).format('YYYY-MM-DD HH:mm:ss')
    }
  },
  methods: {
    // 查询
    loadComment () {
      // if (this.loadedPages[this.objId + '_' + this.currentPage]) {
      //   // this.loadedPages = this.loadedPages[this.objId + '_' + this.currentPage]
      //   return
      // }
      this.loading = true
      fetch({ commentObjid: this.objId, size: 5, current: this.currentPage })
        .then(res => {
          if (!this.comments[this.objId]) {
            this.comments[this.objId] = []
          }
          this.comments[this.objId] = this.comments[this.objId].concat(res.data.records)
         // this.loadedPages[this.objId + '_' + this.currentPage] = true
          if (res.data.total <= res.data.records.length + (this.currentPage - 1) * 5) {
            this.showMores[this.objId] = false
          } else {
            this.showMores[this.objId] = true
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 保存
    saveComment (value) {
      if (value !== '') {
        this.loading = true
        save({ commentObjid: this.objId, commentContent: value }).then(res => {
          if (res.code === 0) {
            this.comments[this.objId].unshift(res.data)
            this.searchName = ''
          } else {
            this.$message.error(res.message)
          }
          this.loading = false
        })
      }
    },
    // 删除
    removeComment (id) {
      removeById(id).then(res => {
        if (res.code === 0) {
          this.$message.success(res.message)
          for (var i = 0; i < this.comments[this.objId].length; i++) {
            if (this.comments[this.objId][i].commentId === id) {
              this.comments[this.objId].splice(i, 1)
              break
            }
          }
          // 删除成功之后重新加载列表并且恢复分页
          this.comments[this.objId] = []
          this.currentPage = 1
          this.loadComment()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 加载更多
    loadMore () {
      this.currentPage += 1
      this.loadComment()
    }
  },
  mounted () {
    this.currentPage = 1
    this.comments[this.objId] = []
    this.loadComment()
  },
  watch: {
    objId: function (val) {
      this.comments[this.objId] = []
      this.currentPage = 1
      this.loadComment()
    }
    // currentPage: function (val) {
    //   this.loadComment()
    // }
  }
}
</script>
<style lang="less">
// .ant-list {
//   max-height: 286px;
// }
.description {
  width: 66%;
  margin-bottom: 10px;
  padding: 10px;
  color: #333;
  font-size: 12px;
  line-height: 22px;
  background: rgba(240, 240, 240, 1);
  border-radius: 4px;
  .ant-avatar-image {
    float: left;
  }
  .comment {
    margin: 0 14px;
  }
  .comment_ {
    display: block;
    margin: 0 14px;
  }
}
.ant-input-group-wrapper {
  width: 66%;
}
</style>
