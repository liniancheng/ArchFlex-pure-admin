package com.adtec.rdc.base.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.monitor.agent.bo.Server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
@RestController
@RequestMapping("/monitor")
@Api(value = "系统监控controller", tags = {"系统监控操作接口"})
public class SysMonitorController {
	@Value("${system.monitor.ips}")
	private String ips;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String SYSTEM_MONITOR_MAP = "SYSTEM_MONITOR_MAP";
	private static final String SYSTEM_MONITOR_MAP_SERVER = "SYSTEM_MONITOR_MAP_SERVER";
	private static final String SYSTEM_MONITOR_MAP_SERVICE = "SYSTEM_MONITOR_MAP_SERVICE";

    @ApiImplicitParam(name = "server", value = "系统信息", required = true, dataType = "Server")
    @PostMapping(value = "/agent")
    public ApiResult<Boolean> save(@RequestBody Server server){
    	boolean isRightIp = false;
    	if(StringUtils.isEmpty(ips)) {
    		isRightIp = true;
    	}else {
    		String[] arrayIps = ips.split(",");
    		for(String ip : arrayIps) {
    			if(server.getSys().getComputerIp().startsWith(ip)) {
    				isRightIp = true;
    				break;
    			}
    		}
    	}
    	
		if(isRightIp) {
			Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
			Map<String, Object> serverMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVER);
			if(serverMap==null) {
				serverMap = new HashMap<String, Object>();
			}
			Map<String, Object> serviceMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVICE);
			if(serviceMap==null) {
				serviceMap = new HashMap<String, Object>();
			}
			serverMap.put(server.getSys().getComputerIp(), server);
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
			}
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, serverMap);
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, serviceMap);
			redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
		}
        return new ApiResult<>(true);
    }
    
	@GetMapping(value = "/service")
    public ApiResult<List<Server>> service() {
    	Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		Map<String, Object> serviceMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVICE);
		List<Server> list = new ArrayList<Server>();
		for(String key : serviceMap.keySet()) {
			List<Server> servers = (List<Server>) serviceMap.get(key);
			for(Server server : servers) {
				if(((System.currentTimeMillis()-server.getCreateTime())/1000/60)>5) {
					//5分钟以上的认为不健康
					server.setHealth(false);			
				}else {
					server.setHealth(true);
				}
				list.add(server);
			}
		}
		return new ApiResult<>(list);
    }
	
	@GetMapping(value = "/server")
    public ApiResult<List<Server>> server(String keyword) {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		Map<String, Object> serverMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVER);
		List<Server> list = new ArrayList<Server>();
		if(StringUtils.isEmpty(keyword)) {
			for(Object object : serverMap.values()) {
				Server server = (Server) object;
				list.add(server);
			}
		}else {
			for(Object object : serverMap.values()) {
				Server server = (Server) object;
				if(server.getSys().getOsName().indexOf(keyword)>-1 || server.getSys().getComputerIp().indexOf(keyword)>-1) {
					list.add(server);
				}
			}
		}
        return new ApiResult<>(list);
    }
	
	@GetMapping(value = "/clearServer")
    public ApiResult<Boolean> clearServer() {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, new HashMap<String, Object>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
        return new ApiResult<>(true);
    }
	
	@GetMapping(value = "/clearService")
    public ApiResult<Boolean> clearService() {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, new HashMap<String, Object>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
        return new ApiResult<>(true);
    }
	
	private Map<String, Map<String, Object>> getMapFromRedis() {
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
	
}
