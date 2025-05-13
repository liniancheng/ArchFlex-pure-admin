package com.adtec.rdc.base.monitor.agent.task;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adtec.rdc.base.monitor.agent.bo.Server;
import com.adtec.rdc.base.monitor.agent.utils.AgentUtils;
import com.adtec.rdc.base.monitor.agent.utils.ServerUtils;

@Component
public class AgentTask {
	@Value("${spring.application.name}")
    private String serviceName;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 资源监控
	 */
	@Scheduled(cron="0 */1 * * * ?")
    public void monitor(){
		try {
			Server server = ServerUtils.monitor(serviceName);
			AgentUtils.saveToRedis(redisTemplate, server);
		} catch (Exception e) {
			System.out.println("提交失败-"+e.getMessage());
		}
    }
}
