package com.adtec.rdc.base.param.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.adtec.rdc.base.param.service.SysParamInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PubCodeInitRunner implements ApplicationRunner {

	@Autowired
	private SysParamInfoService paramInfoService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		log.debug("******************初始化系统配置项 statrt***********************");
		// 获取公共代码中相关配置
		List<SysParamInfo> list = paramInfoService.list();
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.stream()
					.collect(Collectors.toMap(SysParamInfo::getParamName, b -> b.getParamValue(), (k1, k2) -> k2));
			stringRedisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).putAll(map);
		}
		log.debug("******************初始化系统配置项 end***********************");

	}
	
}
