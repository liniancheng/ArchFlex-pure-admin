package com.adtec.rdc.base.monitor.agent.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.adtec.rdc.base.monitor.agent.bo.Server;

public class AgentUtils {
	public static final String SYSTEM_MONITOR_MAP = "SYSTEM_MONITOR_MAP";
	public static final String SYSTEM_MONITOR_MAP_SERVER = "SYSTEM_MONITOR_MAP_SERVER";
	public static final String SYSTEM_MONITOR_MAP_SERVICE = "SYSTEM_MONITOR_MAP_SERVICE";
	
	public static Map<String, Map<String, Object>> getMapFromRedis(RedisTemplate<String, Object> redisTemplate) {
		Object redisObject = redisTemplate.opsForValue().get(SYSTEM_MONITOR_MAP);
		Map<String, Map<String, Object>> monitorMap = null;
		if(redisObject == null) {
			monitorMap = new HashMap<String, Map<String, Object>>(2);
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, new HashMap<String, Object>());
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, new HashMap<String, Object>());
		}else {
			monitorMap = (Map<String, Map<String, Object>>) redisObject;
		}
		return monitorMap;
	}
	
	public static Map<String, Object> getServerMap(Map<String, Map<String, Object>> monitorMap) {
		Map<String, Object> serverMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVER);
		if(serverMap==null) {
			serverMap = new HashMap<String, Object>();
		}
		return serverMap;
	}
	
	public static Map<String, Object> getServiceMap(Map<String, Map<String, Object>> monitorMap) {
		Map<String, Object> serviceMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVICE);
		if(serviceMap==null) {
			serviceMap = new HashMap<String, Object>();
		}
		return serviceMap;
	}
	
	/**
	 * 保存到redis
	 * @param redisTemplate
	 * @param server
	 */
	public static void saveToRedis(RedisTemplate<String, Object> redisTemplate, Server server) {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis(redisTemplate);
		Map<String, Object> serverMap = getServerMap(monitorMap); 
		serverMap.put(server.getSys().getComputerIp(), server);
		
		Map<String, Object> serviceMap = getServiceMap(monitorMap); 
		List<Server> services = (List<Server>) serviceMap.get(server.getServiceName());
		if(services == null) {
			services = new ArrayList<Server>();
			services.add(server);
			serviceMap.put(server.getServiceName(), services);
		}else {
			int i = 0;
			boolean existIp = false;
			for(Server service : services) {
				if(service.getSys().getComputerIp().equals(server.getSys().getComputerIp())) {
					services.set(i, server);
					existIp = true;
					break;
				}
				i++;
			}
			if(!existIp) {
				services.add(server);
			}
			serviceMap.put(server.getServiceName(), services);
		}
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, serverMap);
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, serviceMap);
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
	}
	
	public static void clearServer(RedisTemplate<String, Object> redisTemplate) {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis(redisTemplate);
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, new HashMap<String, Object>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
	}
	
	public static void clearService(RedisTemplate<String, Object> redisTemplate) {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis(redisTemplate);
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, new HashMap<String, Object>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
	}
	
	/**
	 * 判断服务器IP是否为要监控的IP范围
	 * @param serverIp
	 * @param ips
	 * @return
	 */
	public static boolean isRightIp(String serverIp, String ips) {
		boolean isRightIp = false;
    	if(null==ips || "".equals(ips)) {
    		isRightIp = true;
    	}else {
    		String[] arrayIps = ips.split(",");
    		for(String ip : arrayIps) {
    			if(serverIp.startsWith(ip)) {
    				isRightIp = true;
    				break;
    			}
    		}
    	}
    	return isRightIp;
	}
}
