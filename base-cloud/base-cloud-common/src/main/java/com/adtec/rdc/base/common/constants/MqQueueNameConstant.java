package com.adtec.rdc.base.common.constants;

/**
 * @author: JTao
 * @date: 2018/11/14 15:42
 */
public class MqQueueNameConstant {

    /**
     * 系统日志队列
     */
	public static final String SYS_LOG_QUEUE= "sys_log_queue";
    /**
     * 短信验证码队列
     */
	public static final String MOBILE_CODE_QUEUE= "mobile_code_queue";
    /**
     * 邮件队列
     */
	public static final String MAIL_CODE_QUEUE= "mail_code_queue";

    /**
     * 访问统计队列
     */
    public static final String REQUEST_STATISTICS_QUEUE= "request_statistics_queue";
    /**
     * 报表权限同步队列
     */
    public static final String REPORT_AUTH_QUEUE = "report_auth_queue";
    /**
     * 报表用户同步队列
     */
    public static final String REPORT_USER_QUEUE = "report_user_queue";
    /**
     * 报表角色同步队列
     */
    public static final String REPORT_ROLE_QUEUE = "report_role_queue";
    /**
     * 报表机构同步队列
     */
    public static final String REPORT_BRANCH_QUEUE = "report_branch_queue";
    /**
     * 报表机构批量同步队列
     */
    public static final String REPORT_BRANCH_BATCH_QUEUE = "report_branch_batch_queue";
    
    /**
     * 报表访问统计队列
     */
    public static final String REPORT_REQUEST_QUEUE = "report_request_queue";
    /**
     * 搜索删除队列
     */
    public static final String ELASTICSEARCH_QUEUE = "elasticsearch_queue";
    
}
