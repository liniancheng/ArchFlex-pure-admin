<script>
import events from './events'

export default {
  name: 'MultiTab',
  props: {
    keepAliveComponentInstance: { type: Object, default: null }
  },
  data () {
    return {
      fullPathList: [],
      pages: [],
      activeKey: '',
      newTabIndex: 0
    }
  },
  created () {
    // bind event
    events
      .$on('open', val => {
        if (!val) {
          throw new Error(`multi-tab: open tab ${val} err`)
        }
        this.activeKey = val
      })
      .$on('close', val => {
        if (!val) {
          this.closeThat(this.activeKey)
          return
        }
        this.closeThat(val)
      })
      .$on('rename', ({ key, name }) => {
        try {
          const item = this.pages.find(item => item.path === key)
          item.meta.customTitle = name
          this.$forceUpdate()
        } catch (e) {}
      })

    this.pages.push(this.$route)
    this.fullPathList.push(this.$route.fullPath)
    this.selectedLastPath()
    // 判断打开的是否是工作台
    if (this.pages[0].path !== '/dashboard/workplace') {
      this.pages.unshift({
        fullPath: '/dashboard/workplace',
        path: '/dashboard/workplace',
        hash: '',
        name: 'menu.dashboard',
        meta: {
          title: 'menu.dashboard'
        }
      })
      this.fullPathList.unshift('/dashboard/workplace')
    }
  },
  methods: {
    onEdit (targetKey, action) {
      this[action](targetKey)
    },
    remove (targetKey) {
      if (targetKey === '/dashboard/workplace') {
        this.selectedLastPath()
        return false
      } else {
        this.pages = this.pages.filter(page => page.fullPath !== targetKey)
        this.fullPathList = this.fullPathList.filter(path => path !== targetKey)
         const cache = this.keepAliveComponentInstance.cache
          const keys = this.keepAliveComponentInstance.keys
          for (let i = 0; i < keys.length; i++) {
            if (keys[i] === targetKey) {
              keys.splice(i, 1)
              if (cache[targetKey] != null) {
                delete cache[targetKey]
              }
              break
            }
          }
        // 判断当前标签是否关闭，若关闭则跳转到最后一个还存在的标签页
        if (!this.fullPathList.includes(this.activeKey)) {
          this.selectedLastPath()
        }
      }
    },
    selectedLastPath () {
      this.activeKey = this.fullPathList[this.fullPathList.length - 1]
    },

    // content menu
    closeThat (e) {
      if (e === '/dashboard/workplace') {
      } else {
        // 判断是否为最后一个标签页，如果是最后一个，则无法被关闭
        if (this.fullPathList.length > 1) {
          this.remove(e)
        } else {
          this.$message.info('这是最后一个标签了, 无法被关闭')
        }
      }
    },
    closeLeft (e) {
      const currentIndex = this.fullPathList.indexOf(e)
      if (currentIndex > 0) {
        this.fullPathList.forEach((item, index) => {
          if (index < currentIndex) {
            this.remove(item)
          }
        })
      } else {
        this.$message.info('左侧没有标签')
      }
    },
    closeRight (e) {
      const currentIndex = this.fullPathList.indexOf(e)
      if (currentIndex < this.fullPathList.length - 1) {
        this.fullPathList.forEach((item, index) => {
          if (index > currentIndex) {
            this.remove(item)
          }
        })
      } else {
        this.$message.info('右侧没有标签')
      }
    },
    closeAll (e) {
      const currentIndex = this.fullPathList.indexOf(e)
      this.fullPathList.forEach((item, index) => {
        if (index !== currentIndex) {
          this.remove(item)
        }
      })
    },
    closeMenuClick (key, route) {
      this[key](route)
    },
    renderTabPaneMenu (e) {
      return (
        <a-menu
          {...{
            on: {
              click: ({ key, item, domEvent }) => {
                this.closeMenuClick(key, e)
              }
            }
          }}
        >
          <a-menu-item key="closeThat">关闭当前标签</a-menu-item>
          <a-menu-item key="closeRight">关闭右侧</a-menu-item>
          <a-menu-item key="closeLeft">关闭左侧</a-menu-item>
          <a-menu-item key="closeAll">关闭全部</a-menu-item>
        </a-menu>
      )
    },
    // render
    renderTabPane (title, keyPath) {
      const menu = this.renderTabPaneMenu(keyPath)
      return (
        <a-dropdown overlay={menu} trigger={['contextmenu']}>
          <span style={{ userSelect: 'none' }}>{this.$t(title)}</span>
        </a-dropdown>
      )
    }
  },
  watch: {
    $route: function (newVal) {
      this.activeKey = newVal.fullPath
      if (this.fullPathList.indexOf(newVal.fullPath) < 0) {
        this.fullPathList.push(newVal.fullPath)
        this.pages.push(newVal)
      }
    },
    activeKey: function (newPathKey) {
      this.$router.push({ path: newPathKey })
    }
  },
  render () {
    const {
      onEdit,
      $data: { pages }
    } = this
    const panes = pages.map(page => {
      return (
        <a-tab-pane
          style={{ height: 0 }}
          tab={this.renderTabPane((page.query && page.query.customTitle) || page.meta.title, page.fullPath)}
          key={page.fullPath}
          closable={pages.length > 1 && page.path !== '/dashboard/workplace'}
        ></a-tab-pane>
      )
    })

    return (
      <div class="ant-pro-multi-tab ant-pro-multi-tab-individualization">
        <div class="ant-pro-multi-tab-wrapper">
          <a-tabs
            hideAdd
            type={'editable-card'}
            v-model={this.activeKey}
            tabBarStyle={{ margin: 0, paddingLeft: '16px', paddingTop: '1px' }}
            {...{ on: { edit: onEdit } }}
          >
            {panes}
          </a-tabs>
        </div>
      </div>
    )
  }
}
</script>
<style lang="less">
@import '../theme.less';
.ant-pro-multi-tab-individualization {
  .ant-tabs-bar {
    background: #fff !important;
    border-bottom: 0px solid #e8e8e8;
    box-shadow: 0px 0px 6px 0px @ashColor;
  }
  .ant-tabs-nav {
    margin-top: 5px;
  }
  .ant-tabs.ant-tabs-card .ant-tabs-card-bar  {
    .ant-tabs-tab-active {
      color: @whiteColor !important;
      background: @primary-color !important;
      box-shadow: 0px 0px 3px 0px @ashColor;
      border-color: transparent;
      border: 0px solid #fff;
      svg {
        fill: @primary-color !important;
      }
      height: 28px;
    }
    .ant-tabs-tab-active.ant-tabs-tab .ant-tabs-close-x {
      background: @whiteColor;
    }
    .ant-tabs-tab {
      background: @ashColor;
      border: 0px solid #e8e8e8;
      color: @fontColoe;
      height: 28px;
      margin-right: 6px;
      padding: 0 10px;
      line-height: 28px;
      border-radius: 50px;
      svg {
        fill: @whiteColor;
      }
      .ant-tabs-close-x {
        background: @fontColoe;
        color: @ashColor;
        width: 16px;
        border-radius: 50px;
        height: 16px;
        line-height: 16px;
        overflow: hidden;
        font-size: 10px;
        vertical-align: middle;
        -webkit-transition: all 0.3s;
        transition: all 0.3s;
        font-weight: bold;
        margin: 0 0 4px 10px;
      }
    }
  }
}
</style>
