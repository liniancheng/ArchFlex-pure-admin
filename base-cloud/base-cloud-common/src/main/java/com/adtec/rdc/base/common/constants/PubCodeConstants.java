package com.adtec.rdc.base.common.constants;

/**
 * @author: dengchf
 * @date: 2020年4月1日
 * @Description:参数管理枚举类</br>
 * @version V1.0
 * @Copyright: adtec
 */
public enum PubCodeConstants {
	;

	private interface PubCodeEnums {
		public String getCode();

		public String getValue();
	}
	
	public enum ASYNC implements PubCodeEnums {
		/** smartbi 报表权限同步标识-->0：同步；1：不同步 */
		ASYNC_FLAG("smartbi.async.flag", "1");
		private String code;
		private String value;

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getValue() {
			return value;
		}

		private ASYNC(String code, String value) {
			this.code = code;
			this.value = value;
		}
	}

	/**
	 * 密码相关枚举
	 * @author dengchf
	 *
	 */
	public enum PASSWORD implements PubCodeEnums {
		/** 为第三方集成提供密码 */
		THIRD_PWD("system.pwd.third","true"),
		/** 密码修改周期(单位：天) */
		CYCLE_EDIT("system.pwd.cycle.edit", "10000"),
		/** 密码提前提醒天数 */
		CYCLE_TIPS("system.pwd.cycle.tips", "3"),
		/** 密码校验规则 */
		RULE_PATTERN("system.pwd.rule.pattern", "[/[0-9]/,/[a-zA-Z]/,/[^0-9a-zA-Z_]/]"),
		/** 密码校验提示语 */
		RULE_MSG("system.pwd.rule.msg", "密码强度不够"),
		/** 首次登录修改密码, 默认不强制修改 */
		EDIT_FIRST("system.pwd.edit.first", "true"),
		/** 密码N次修改之内不允许重复， 默认最近1次不允许重复 */
		LOCK_NUMB("system.pwd.lock.numb", "1"),;
		private String code;
		private String value;

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getValue() {
			return value;
		}

		private PASSWORD(String code, String value) {
			this.code = code;
			this.value = value;
		}
	}

}
