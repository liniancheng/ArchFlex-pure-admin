package com.adtec.rdc.base.common.constants;

/**
 * @author: JTao
 * @date: 2018/10/17 15:18
 * @description:
 */
public class CommonConstants {

    /**
     * 树的根节点值
     */
	public static final String TREE_ROOT = "-1";

	public static final String BASE_REDIS_LIST_LEY = "base_client_id_to_access:cloud";

	public static final String BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY = "base_redis_list:statistical_strategy";
	/**
     *  1 与登录名相同 
     *  2 登录名+固定密码 
     *  3 固定密码
     */
	
	public static final String IS_SAME_LOGIN_NAME = "1";
	public static final String LOGIN_NAME_AND_FIXD = "2";
	public static final String ONLY_FIXD = "3";
	public static final String INIT_STAT = "0";
	
	public static final String EXIT = "exit"; //中断
	public static final String SKIP = "skip"; //跳过
	public static final String UPDATE = "update";//覆盖
	public static final String CLEARTHENINSERT = "clearThenInsert";//清除
	
	public static final String SYSTEM_PUBLIC_CODE = "system_public_code";

}
