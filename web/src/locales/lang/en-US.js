import antdEnUS from 'ant-design-vue/es/locale-provider/en_US'
import momentEU from 'moment/locale/eu'

const components = {
  antLocale: antdEnUS,
  momentName: 'eu',
  momentLocale: momentEU
}

const locale = {
  message: '-',
  'menu.home': '主页',
  menu: {
    search: '搜索',
    dashboard: '首页',
    console: '控制台',
    admin: {
      self: '平台管理',
      system: {
        self: '系统管理',
        branch: '机构管理',
        menu: '菜单管理',
        role: '角色管理',
        user: '用户管理',
        layout: {
          layout: '布局管理',
          custom: '自定义布局',
          design: '定制布局',
          item: '布局数据项'
        }
      },
      config: {
        self: '运行维护',
        anno: '公告管理',
        annoType: '公告类型',
        online: '在线用户',
        operlog: '操作日志',
        pubcode: '运行参数',
        inout: '导入导出',
        inoutManage: '导入导出配置',
        redis: '缓存管理'
      },
      notice: {
        self: '通知中心',
        mailsrv: '邮件服务器',
        smssrv: '短信服务器',
        mailtmp: '邮件模板',
        smstmp: '短信模板',
        log: '通知日志'
      },
      develop: {
        self: '附助开发',
        code: '代码生成'
      },
      account: {
        self: '个人页',
        center: {
          self: '个人中心'
        },
        settings: {
          self: '个人设置',
          base: '基本设置',
          security: '安全设置',
          custom: '个性化',
          notice: '消息通知'
        }
      }
    },
    framework: {
      self: '架构管理',
      organization: {
        self: '组织架构',
        department: '部门管理',
        deployee: '员工管理'
      },
      itfw: {
        self: 'IT架构',
        system: '系统管理',
        hardware: '硬件资源',
        software: '软件资源',
        datafw: '数据架构'
      }
    },
    task: {
      self: '任务管理',
      task: {
        self: '任务管理 ',
        server: '调度服务器',
        calendar: '执行日历',
        program: '程序注册',
        regedit: '任务管理',
        monitor: '任务监控',
        execlog: '任务日志'
      }
    },
    knowledge: {
      bus: '知识库',
      self: '知识库管理',
      manage: {
        self: '知识库管理',
        know: '知识库管理'
      }
    },
    meta: {
      bus: '元数据',
      self: '元数据管理',
      model: {
        self: '元模型管理',
        type: '元类型管理',
        model: '元模型管理'
      },
      meta: {
        self: '元数据管理',
        meta: '元数据管理',
        view: '元视图管理',
        version: '元数据版本',
        reldstd: '关联数据标准'
      },
      analysis: {
        self: '元数据分析',
        meta: '元数据分析',
        stat: '元数据统计'
      }
    },
    dstd: {
      bus: '数据标准',
      self: '数据标准管理',
      dstd: {
        self: '数据标准管理',
        basis: '数据标准依据',
        dstd: '数据标准管理',
        approve: '数据标准审批',
        floor: '对标任务管理',
        floorrole: '对标审批角色',
        floortaskapprove: '对标任务审批'
      },
      version: {
        self: '数据标准版本',
        version: '数据标准版本',
        approve: '标准版本审批'
      },
      analysis: {
        self: '数据标准分析',
        analysis: '数据标准分析'
      },
      ind: {
        self: '数据指标管理',
        ind: '指标体系管理'
      },
      model: {
        self: '数据模型管理',
        business: '业务术语',
        theme: '主题域',
        model: '数据模型',
        version: '模型版本',
        different: '模型差异'
      }
    },
    dqm: {
      bus: '数据质量',
      self: '数据质量管理',
      check: {
        self: '检核管理',
        rule: '检核规则',
        filetmp: '文件模板'
      },
      ind: {
        self: '指标检核',
        sql: 'SQL查询管理',
        rule: '指标取数规则',
        formula: '指标检核公式'
      },
      dstd: {
        self: '标准检核',
        dstdrel: '检核规则关系'
      },
      result: {
        self: '检核结果',
        detail: '检核记录',
        total: '总体情况',
        task: '任务情况',
        rule: '规则情况',
        report: '检核报告'
      },
      question: {
        self: '质量问题',
        dir: '问题分类',
        manage: '问题管理',
        analysis: '问题分析'
      },
      assess: {
        self: '质量评估',
        dimension: '质量评估维度',
        result: '质量评估结果',
        report: '质量报估报告'
      }
    },
    query: {
      bus: '自助查询',
      self: '数据画像',
      permission: {
        self: '权限控制 ',
        operation: '操作权限',
        tab: '标签页权限',
        orgtable: '数据源机构表',
        tableorgfield: '表机构字段'
      }
    }
  }
}

export default {
  ...components,
  ...locale
}
