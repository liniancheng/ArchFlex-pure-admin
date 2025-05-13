# 基础框架

## 系统参数配置
  this.$store.getters.config['`key`']
## 全局方法
* 按钮权限
  * this.checkButtonAuth('`标识`')
* 时间格式化
  * this.formatDate('`时间`','`格式`')  `可忽略第二个参数，方便全局控制日期格式，格式默认YYYY-MM-DD HH:mm:ss`

## 全局过滤器
* 数值过滤器 
  * {{ `数值` | NumberFormat }}
* 日期格式化
  * {{ `日期` | moment }}