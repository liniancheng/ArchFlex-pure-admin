<template>
  <a-layout class="imoia-layout-top">
    <a-layout-header class="header">
      <custom-header
        :menus="menus"
        :settings="settings"
        :theme="theme"
        @handleLoadMenu="handleLoadMenu">
        <template v-slot:middleMenu>
          <a-menu
            :theme="theme"
            mode="horizontal"
            @select="onSelect"
            :style="{ lineHeight: '64px' }">
            <a-menu-item
              v-for="(item,index) in topMenus"
              :key="index">
              <a-icon :type="item.meta.icon" />{{ $t(item.name) }}
            </a-menu-item>
          </a-menu>
        </template>
        <template v-slot:rightContent>
          <right-content
            @modifyPassword="modifyPassword"
            :top-menu="settings.layout === 'topmenu'"
            :theme="theme" />
        </template>
      </custom-header>
    </a-layout-header>

    <a-layout
      :theme="theme"
      class="box"
      ref="box">
      <side-menu
        v-if="sideShowType === 'menu' && sideMenus !== []"
        :style="{display:sideMenus.length == 0 ? 'none':'block'}"
        mode="inline"
        :menus="sideMenus"
        :theme="theme"
        :collapsed="collapsed"
        :collapsible="true"
        :width="leftWidth"
        class="left" />
      <side-tree
        v-if="sideShowType === 'tree'"
        mode="inline"
        :theme="theme"
        :treeUrl="treeUrl"
        :treeData="treeData"
        :collapsed="false"
        :collapsible="false"
        :width="leftWidth"
        class="left" />

      <side-blank
        v-if="sideShowType === 'blank'"
        mode="inline"
        :theme="theme"
        :collapsed="false"
        :collapsible="false"
        :width="leftWidth"
        class="left" />
      <div
        class="resize"
        title="拖动调整宽度"
        v-show="isResize">⋮</div>
      <a-layout
        style="padding: 0 0 10px"
        class="mid">
        <multi-tab
          :keep-alive-component-instance="keepAliveComponentInstance"
          class="imoia-multi-tab" />
        <a-layout-content>
          <transition name="page-transition">
            <vue-scroll>
              <div ref="keepAliveContainer">
                <keep-alive>
                  <route-view :key="$route.fullPath" />
                </keep-alive>
              </div>
              <div id="container"></div>
            </vue-scroll>
          </transition>
        </a-layout-content>
        <a-layout-footer>
          <slot name="footerRender">默认 footer</slot>
        </a-layout-footer>
      </a-layout>
    </a-layout>
    <footer-tool-bar>
      <div
        slot="extra"
        class="imoia-sider-button"
        v-if="sideShowType === 'menu'"
        @click="handleSider">
        <a-icon :type="collapsed?'menu-fold':'menu-unfold'" />
      </div>
    </footer-tool-bar>
    <pswd-form ref="pswdForm" />
  </a-layout>
</template>

<script>
import FooterToolBar from '@/components/FooterToolbar'
import RightContent from '@/components/GlobalHeader/RightContent'
import PswdForm from '@/components/GlobalHeader/ModifyPassword'
import CustomHeader from './CustomHeader'
import RouteView from './RouteView'
import SideMenu from '@/components/Menu/SideMenu'
import SideTree from '@/components/Menu/SideTree'
import SideBlank from '@/components/Menu/SideBlank'
import SMenu from '@/components/Menu/index'
import { tree } from '../_util/pub'

export default {
  name: 'BasicLayout',
  components: {
    FooterToolBar,
    CustomHeader,
    SideMenu,
    SideTree,
    SideBlank, // 切换树时使用
    RouteView,
    SMenu,
    RightContent,
    PswdForm
  },
  props: {
    settings: { type: Object, required: true },
    menus: { type: Array, required: true },
    theme: { type: String, default: 'light' }
  },
  data () {
    return {
      collapsed: false,
      // 媒体查询
      query: {},
      // 头部菜单
      topMenus: [],
      // 侧边显示类型
      sideShowType: 'menu',
      // 侧边菜单
      sideMenus: [],
      // 侧边树数据获取url
      treeUrl: '',
      // 侧边树数据
      treeData: [],
      leftWidth: 200,
      keepAliveComponentInstance: null,
      notification: null,
      isResize: false
    }
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    }
  },
  mounted () {
    if (this.$refs.keepAliveContainer) {
      this.keepAliveComponentInstance = this.$refs.keepAliveContainer.childNodes[0].__vue__
    }
    this.$nextTick(function () {
      this.topMenus = this.menus.filter(item => !item.hidden).map(item => item)
      // 处理二级菜单
      if (this.topMenus && this.topMenus.length > 0) {
        const currentMenu = this.topMenus[0]
        if (currentMenu.children) {
          this.sideMenus = currentMenu.children
        }
      }
    })

    const cycle = this.codeMap['system.pwd.cycle.edit']
    const tips = this.codeMap['system.pwd.cycle.tips']
    if (cycle && tips) {
      const last = this.$store.getters.userInfo.lastModifyTime || this.$store.getters.userInfo.createTime
      const diff = new Date() - new Date(last)
      if (Math.floor(diff / (24 * 3600 * 1000) + parseInt(tips)) >= parseInt(cycle)) {
        // const _this = this
        this.setInterval = setInterval(() => {
          this.$notification.warning({
            message: '密码过期提醒',
            description: this.codeMap['system.pwd.cycle.message'] || '当前密码即将过期，请及时修改密码！'
          })
        }, 600000)// 10分钟提醒一次
      }
    }
    this.dragControllerDiv()
  },
  methods: {
    handleSider () {
      this.collapsed = !this.collapsed
    },
    onSelect ({ item, key, selectedKeys }) {
      this.isResize = true
      const currentMenu = this.topMenus[key]
      if (currentMenu.menu) {
        this.sideShowType = 'menu'
        if (currentMenu.children) {
          this.sideMenus = currentMenu.children
        } else {
          // 判断是否是页面
          this.$router.push({ path: currentMenu.path })
        }
      } else {
        this.sideShowType = 'blank'
        this.treeUrl = currentMenu.path
        tree(this.treeUrl, '-1', '').then(res => {
          if (res.data === '') {
            this.treeData = []
          } else {
            this.treeData = res.data
          }
          this.sideShowType = 'tree'
        })
      }
    },
    handleLoadMenu (index) {
      const current = this.topMenus[index]
      // 判断是资源页面还在资源菜单
      if (current.children) {
        this.sideMenus = current.children
      } else {
        this.sideMenus = [current]
      }
    },
    modifyPassword () {
      this.$refs.pswdForm.show()
    },
    dragControllerDiv: function () {
      var resize = document.getElementsByClassName('resize')
      var left = document.getElementsByClassName('left')
      var box = document.getElementsByClassName('box')
      const _this = this
      for (let i = 0; i < resize.length; i++) {
        // 鼠标按下事件
        resize[i].onmousedown = function (e) {
          // 颜色改变提醒
          resize[i].style.background = '#818181'
          var startX = e.clientX
          resize[i].left = resize[i].offsetLeft

          // 鼠标拖动事件
          document.onmousemove = function (e) {
            var endX = e.clientX
            var moveLen = resize[i].left + (endX - startX) // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
            var maxT = box[i].clientWidth - resize[i].offsetWidth // 容器宽度 - 左边区域的宽度 = 右边区域的宽度

            if (moveLen < 32) moveLen = 32 // 左边区域的最小宽度为32px
            if (moveLen > maxT - 150) moveLen = maxT - 150 // 右边区域最小宽度为150px

            // resize[i].style.left = moveLen // 设置左侧区域的宽度
            for (let j = 0; j < left.length; j++) {
              // left[j].style.width = moveLen + 'px'
              _this.leftWidth = moveLen
            }
          }
          // 鼠标松开事件
          document.onmouseup = function (evt) {
            // 颜色恢复
            resize[i].style.background = '#d6d6d6'
            document.onmousemove = null
            document.onmouseup = null
            resize[i].releaseCapture && resize[i].releaseCapture() // 当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
          }
          resize[i].setCapture && resize[i].setCapture() // 该函数在属于当前线程的指定窗口里设置鼠标捕获
          return false
        }
      }
    }
  }
}
</script>
<style lang="less">
.imoia-layout-top {
  height: 100%;
  .ant-layout-header {
    padding: 0;
  }
  .imoia-multi-tab {
    margin: 0px -10px 10px -10px;
  }
  .imoia-sider-button {
    color: white;
    padding: 0 5px;
  }
  .ant-layout-sider {
    height: calc(~'100vh - 64px');
  }
  .ant-layout-content {
    height: calc(~'100vh - 124px');
  }
  .ant-layout {
    flex: initial;
    width: 100%;
  }
  .left {
    flex: none !important;
    max-width: none !important;
  }
  /*拖拽区div样式*/
  .resize {
    cursor: w-resize;
    float: left;
    position: relative;
    top: 45%;
    background-color: #d6d6d6;
    border-radius: 5px;
    margin-top: -10px;
    width: 8px;
    height: 42px;
    background-size: cover;
    background-position: center;
    /* z-index: 99999; */
    font-size: 24px;
    color: white;
  }
  /*拖拽区鼠标悬停样式*/
  .resize:hover {
    color: #444444;
  }
}
</style>
