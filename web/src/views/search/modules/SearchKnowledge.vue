<template>
  <div>
    <a-row class="imoia-search-result">
      <a-list
        class="imoia-search"
        item-layout="vertical"
        :data-source="datas"
        :pagination="pagination"
      >
        <a-list-item slot="renderItem" slot-scope="item">
          <template slot="actions">
            <a-row>
              <a-col span="4">
                <span class="imoia-label">类型：</span>知识
              </a-col>
              <a-col span="10">
                <span class="imoia-label">时间：</span>{{ formatDate(new Date(item.createTime)) }}
              </a-col>
              <a-col span="10">
                <span class="imoia-label">操作：</span>
                <span>
                  <a-button size="small" @click="$router.push(`/know/view/${item.knowledgeId}?customTitle=${item.knowledgeTitle}`)">知识</a-button>
                </span>
              </a-col>
            </a-row>
          </template>
          <a-list-item-meta>
            <a slot="title">{{ item.knowledgeTitle }}</a>
            <template slot="description">
              <a-row>
                <a-col span="24">
                  <span class="imoia-label">关键字：</span>
                  <span class="imoia-text">{{ item.keyWords }}</span>
                </a-col>
                <!-- <a-col span="8">
                  <span class="imoia-label">类型：</span>
                  <span class="imoia-text">{{ item.kwType }}</span>
                </a-col> -->
              </a-row>
              <a-row>
                <div class="imoia-ellipsis">
                  <span class="imoia-label">描述：</span>
                  <span class="imoia-text">{{ item.knowledgeRmk }}</span>
                </div>
              </a-row>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </a-row>
  </div>
</template>
<script>
// 元数据查询
// import { fetchKnowledge } from '@/api/dgmp/common/search/index'
import { fetch } from '@/api/admin/knowledge/index'
export default {
  name: 'SearchDstd',
  data () {
    return {
      searchCode: '',
      datas: [],
      pagination: {
        onChange: page => {
          this.current = page
          this.handleSearch()
        },
        pageSize: 5
      },
      total: 0,
      current: 1
    }
  },
  methods: {
    refresh (searchCode) {
      this.searchCode = searchCode
      this.$nextTick(() => {
        this.handleSearch()
      })
    },
    handleSearch () {
      fetch(Object.assign(
          { current: this.current, size: this.pagination.pageSize },
          { knowledgeTitle: this.searchCode }
        )).then(res => {
        if (res.code === 0) {
          console.info(res)
          this.datas = res.data.records
          this.pagination = { ...this.pagination, total: res.data.total, current: res.data.current }
        }
      })
      // fetchKnowledge(
      //   Object.assign(
      //     { current: this.current - 1, size: this.pagination.pageSize },
      //     { code: this.searchCode }
      //   )
      // ).then(res => {
      //   if (res.code === 0) {
      //     this.datas = res.data.records
      //     this.pagination = { ...this.pagination, total: res.data.total, current: res.data.current + 1 }
      //   }
      // })
    }
  }
}
</script>
