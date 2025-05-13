// with polyfills
import 'core-js/stable'
import 'regenerator-runtime/runtime'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/'
import i18n from './locales'
import moment from 'moment'
import { VueAxios } from './utils/request'
import ProLayout, { PageHeaderWrapper } from '@ant-design-vue/pro-layout'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import themePluginConfig from '../config/themePluginConfig'
import storage from 'store'
import { PREMISSIONS } from '@/store/mutation-types'
import echarts from 'echarts'

import bootstrap from './core/bootstrap'
import './core/lazy_use'
import './permission' // permission control
import './utils/filter' // global filter
import './global.less'
import JDictSelectTag from './components/Dict/index.js'
import AModal from '@/components/CustomModal/AModal'
import Storage from 'vue-ls'

const options = {
  namespace: 'vuejs__', // key键前缀
  name: 'ls', // 命名Vue变量.[ls]或this.[$ls],
  storage: 'local' // 存储名称: session, local, memory
}
Vue.use(ElementUI)
Vue.use(Storage, options)
Vue.use(JDictSelectTag)
Vue.config.productionTip = false

Vue.prototype.$echarts = echarts
Vue.use(VueAxios)

Vue.component('a-modal', AModal)
Vue.component('pro-layout', ProLayout)
Vue.component('page-header-wrapper', PageHeaderWrapper)
// 格式化时间全局通用方法
Vue.prototype.formatDate = function (date, fmt = 'YYYY-MM-DD HH:mm:ss') {
  return moment(date).format(fmt)
}
Vue.prototype.checkButtonAuth = function (key) {
  const premissions = storage.get(PREMISSIONS)
  return premissions.indexOf(key) > -1
}

Vue.prototype.tableScrollY = function () {
  let headY = 0
  const head = document.getElementsByClassName('table-page-search-wrapper')
  if (head.length > 0) {
    headY = head[0].clientHeight
  }
  return document.body.clientHeight - 280 - headY
}

window.umi_plugin_ant_themeVar = themePluginConfig.theme
new Vue({
  router,
  store,
  i18n,
  data: function () {
    return {
      theme: 'blue'

    }
  },
  created: bootstrap,
  render: h => h(App)
}).$mount('#app')

// 加载初始化资源（公共代码）
store.dispatch('PublicCode')
