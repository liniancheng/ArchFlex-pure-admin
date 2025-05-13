<template>
  <a-card>
    <a-row class="imoia-search-params">
      <a-col span="6"></a-col>
      <a-col span="12">
        <a-form-item>
          <a-input-search v-model="searchKey" placeholder="搜索" @search="handleSearch">
            <a-button slot="enterButton" type="primary" icon="search">搜索</a-button>
          </a-input-search>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-tabs :activeKey="searchType" @change="handleChange">
        <a-tab-pane key="base_kw" tab="知识库">
          <search-knowledge ref="searchKw"/>
        </a-tab-pane>
        <a-tab-pane key="others" tab="其它">
          do yourself
        </a-tab-pane>
      </a-tabs>
    </a-row>
  </a-card>
</template>
<script>
import events from '@/utils/events'
import SearchKnowledge from './modules/SearchKnowledge'
export default {
  components: {
    SearchKnowledge
  },
  data () {
    return {
      searchType: 'base_kw', // 判断显示哪个页签
      searchKey: '' // 搜索的关键字
    }
  },
  mounted () {
    const code = this.$route.params.searchKey
    if (code) {
      this.doSearch(code)
    }
    const _this = this
    events.$on('doSearchEvent', function (data) {
      _this.doSearch(data)
    })
  },
  methods: {
    doSearch (code) {
      this.searchKey = code
      this.handleChange('base_kw')
      this.handleSearch()
    },
    handleSearch () {
      if (this.searchType === 'base_kw') {
        this.$refs.searchKw.refresh(this.searchKey)
      }
    },
    // 全局范围
    handleChange (index) {
      this.searchType = index
      this.$nextTick(() => {
        this.handleSearch()
      })
    }
  },
  watch: {
    $route (to, from) {
      if (to.fullPath === '/search') {
        if (to.params.searchKey && to.params.searchKey !== this.searchKey) {
          this.searchKey = to.params.searchKey
          this.handleChange('base_kw')
          this.handleSearch()
        }
      }
    }
  }
}
</script>
<style lang="less">
  .imoia-search-params{
    .ant-row{
      margin-bottom: 10px;
    }
    .ant-input-search {
      max-width: 532px;
      margin: 4px 0 0 0;
    }
    .ant-form-item{
      margin: 0 auto;
    }
  }
  .imoia-search-result{
    .ant-list-item:nth-child(odd) {
      background: #f9f8f8;
    }
    .ant-list-items {
      border-top: 1px solid #e8e8e8;
    }
    .ant-list-item-meta,.ant-list-item-action {
      margin: 0 50px;
      font-size: 12px;
      .ant-list-item-meta-content {
        width: 100%;
      }
    }
    .ant-list-item-action {
      li {
        width: 100%;
      }
      .ant-row {
        text-align: left;
        font-size: 12px;
      }
    }
    .ant-list-item-meta-description {
      .ant-row {
        margin: 5px 0;
      }
    }
    .imoia-label {
      color: #aaaaaa;
    }
    .imoia-text {
      color: #666666;
    }
    .imoia-ellipsis {
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .ant-pagination,.page-align {
      text-align: right;
    }
  }

</style>
