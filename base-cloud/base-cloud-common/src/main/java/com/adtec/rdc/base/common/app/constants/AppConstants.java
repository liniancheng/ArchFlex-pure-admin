package com.adtec.rdc.base.common.app.constants;

/**
 * 
 * @author dengchf
 * @description: 多租户工具类
 */
public class AppConstants {
	/**	租户id */
	public static  final String TOKEN_TENANT = "Tenant";
	
	/** 管理端登录标识 */
	public static final String LOGIN_TYPE_EXP = "Tenant_Exp";
	/** 业务端登录标识 */
	public static final String LOGIN_TYPE_RUN = "Tenant_Run";
	/** 超级管理系统标识（内置租户） */
	public static final String DEF_MAIN_APP_ID = "manage_tenant";
	// redis缓存相关
	/** 当前登录的租户id（持久生效，登陆时刷新） */
	public static final String REDIS_APP_ID_PREFIX = "base_login_appId_";
	/**
	 * 租户状态 
	 * 	0 : 启用
	 *  1 : 禁用
	 *  2 : 删除
	 */
	public static final String APP_STATE_ON = "0";
	public static final String APP_STATE_OFF = "1";
	public static final String APP_STATE_DEL = "2";
}
