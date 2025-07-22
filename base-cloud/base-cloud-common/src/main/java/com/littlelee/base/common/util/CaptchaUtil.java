package com.littlelee.base.common.util;

import com.littlelee.base.common.constants.SecurityConstants;

public class CaptchaUtil {
	public static String getCaptchaRedisKey(String ipaddress) {
		return SecurityConstants.REDIS_CAPTCHA_CODE_PREFIX + ipaddress;
	}
}
