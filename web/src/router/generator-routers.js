// eslint-disable-next-line
import * as loginService from '@/api/login'
// eslint-disable-next-line
import { BasicLayout, BlankLayout, PageView, RouteView } from '@/layouts'

// 前端路由表
const constantRouterComponents = {
  // 基础页面 layout 必须引入
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView
}

// 前端未找到页面路由（固定不用改）
const notFoundRouter = {
  path: '*',
  redirect: '/404',
  hidden: true
}

// 根级菜单
const rootRouter = {
  key: 'index',
  name: 'menu.home',
  routeName: '  ', // 使用空格来替换
  path: '/',
  component: 'BasicLayout',
  redirect: '/dashboard/workplace',
  meta: {
    title: 'menu.home'
  },
  children: []
}

/**
 * 动态生成菜单
 * @param token
 * @returns {Promise<Router>}
 */
export const generatorDynamicRouter = () => {
  return new Promise((resolve, reject) => {
    loginService
      .getCurrentUserNav()
      .then(res => {
        const menuNav = []
        const childrenNav = res.data
        // eslint-disable-next-line camelcase
        rootRouter.children = childrenNav
        menuNav.push(rootRouter)
        const routers = generator(menuNav)
        routers.push(notFoundRouter)
        resolve(routers)
      })
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 格式化树形结构数据 生成 vue-router 层级路由表
 *
 * @param routerMap
 * @param parent
 * @returns {*}
 */
export const generator = (routerMap, parent) => {
  return routerMap.map(item => {
    const currentRouter = {
      // 如果路由设置了 path，则作为默认 path，否则 路由地址 动态拼接生成如 /dashboard/workplace
      path: item.path || `${(parent && parent.path) || ''}/${item.key}`,
      // 路由名称，建议唯一
      name: item.name || item.key || '',
      // 该路由对应页面的 组件 :方案1
      // component: constantRouterComponents[item.component || item.key],
      // 该路由对应页面的 组件 :方案2 (动态加载)
      component: constantRouterComponents[item.component || item.key] || (() => import(`@/${item.component}`)),
      // menu: true 分层菜单(左侧菜单) false 资源树菜单(左侧资源树)
      menu: (item.children && item.children.length > 0) || (!item.children || item.children === null) && (item.component !== 'layouts/PageView' && item.component !== 'layouts/RouteView'),
      // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
      meta: {
        title: item.routeName,
        icon: item.icon || undefined,
        hiddenHeaderContent: false, // hiddenHeaderContent,
        keepAlive: item.aliveFlag === '1'
        // target: target,
        // permission: item.name
      }
    }
    if (item.hideFlag === '1') {
      currentRouter.hidden = true
    }
    // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
    if (!currentRouter.path.startsWith('http')) {
      currentRouter.path = currentRouter.path.replace('//', '/')
    }
    // 重定向
    item.redirect && (currentRouter.redirect = item.redirect)
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      // Recursion
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })
}
