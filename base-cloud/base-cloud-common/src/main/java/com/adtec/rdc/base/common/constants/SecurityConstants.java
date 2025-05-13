package com.adtec.rdc.base.common.constants;

/**
 * @author: JTao
 * @date: 2018/10/9 15:10
 * @description: 安全配置常量
 */
public class SecurityConstants {

	/**
	 * token的header key
	 */
	public static final String TOKEN_HEADER = "Authorization";

	public static final String CLOUD = "cloud";

	public static final String CLOUD_PREFIX = "base_";

	/**
	 * jwt 加密key
	 */
	public static final String SIGN_KEY = "BASE";

	/**
	 * sys_oauth_client_details 字段
	 */
	public static final String CLIENT_FIELDS = "client_id, client_secret, resources_ids, scope, authorized_grant_types,"
			+ "web_server_redirect_uri, authorities, access_token_validity,"
			+ "refresh_token_validity, addition_information, autoapprove";

	/**
	 * jdbcClientDetailsService查询sql
	 */
	public static final String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS + " from sys_oauth_client";

	/**
	 * 默认查询语句
	 */
	public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/**
	 * 根据client_id查询
	 */
	public static final String DEFAULT_FIND_STATEMENT_BY_CLIENT_ID = BASE_FIND_STATEMENT + " where client_id = ?";

	public static final String SPRING_SECURITY_MOBILE_KEY = "mobile";

	public static final String SPRING_SECURITY_CODE_KEY = "code";

	/**
	 * 手机验证码登录的地址
	 */
	public static final String SPRING_SECURITY_MOBILE_TOKEN_URL = "/mobile/token";

	public static final String REDIS_MOBILE_CODE_PREFIX = "base_mobile_code_";

	public static final Integer REDIS_CODE_EXPIRE = 60;

	public static final String REDIS_CAPTCHA_CODE_PREFIX = "base_captcha_code_";
	
	/**
	 * 忘记密码：缓存前缀
	 */
	public static final String REDIS_FORGOT_PWD_CODE_PREFIX = "base_forgot_code_";
	public static final Integer REDIS_FORGOT_PWD_EXPIRE = 300;
		//忘记密码邮件认证码
	public static final String REDIS_FORGOT_PWD_MAIL_CODE_PREFIX = "base_forgot_mail_code_";
	public static final Integer REDIS_FORGOT_PWD_MAIL_CODE_EXPIRE = 8;//8小时
	/**
	 * 数据字典：字典前缀
	 */
	public static final String REDIS_SYS_DICT_PREFIX = "REDIS_SYS_DICT_PREFIX_";

}
