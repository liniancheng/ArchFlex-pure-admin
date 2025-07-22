package com.littlelee.base.monitor.controller;

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

import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.monitor.agent.bo.Server;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author littlelee
 * @date 2020-06-28 07:42:28
 */
@RestController
@RequestMapping("/monitor")
@Tag(name = "系统监控操作接口", description = "系统监控相关接口")
public class SysMonitorController {

	@Value("${system.monitor.ips}")
	private String ips;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String SYSTEM_MONITOR_MAP = "SYSTEM_MONITOR_MAP";
	private static final String SYSTEM_MONITOR_MAP_SERVER = "SYSTEM_MONITOR_MAP_SERVER";
	private static final String SYSTEM_MONITOR_MAP_SERVICE = "SYSTEM_MONITOR_MAP_SERVICE";

	@PostMapping("/agent")
	@Operation(summary = "保存系统信息", description = "上报并保存节点系统信息")
	public ApiResult<Boolean> save(
			@Parameter(description = "系统信息", required = true)
			@RequestBody Server server) {

		boolean isRightIp = false;
		if (StringUtils.isEmpty(ips)) {
			isRightIp = true;
		} else {
			String[] arrayIps = ips.split(",");
			for (String ip : arrayIps) {
				if (server.getSys().getComputerIp().startsWith(ip)) {
					isRightIp = true;
					break;
				}
			}
		}

		if (isRightIp) {
			Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
			Map<String, Object> serverMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVER);
			if (serverMap == null) {
				serverMap = new HashMap<>();
			}
			Map<String, Object> serviceMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVICE);
			if (serviceMap == null) {
				serviceMap = new HashMap<>();
			}
			serverMap.put(server.getSys().getComputerIp(), server);
			List<Server> services = (List<Server>) serviceMap.get(server.getServiceName());
			if (services == null) {
				services = new ArrayList<>();
				services.add(server);
				serviceMap.put(server.getServiceName(), services);
			} else {
				int i = 0;
				boolean existIp = false;
				for (Server service : services) {
					if (service.getSys().getComputerIp().equals(server.getSys().getComputerIp())) {
						services.set(i, server);
						existIp = true;
						break;
					}
					i++;
				}
				if (!existIp) {
					services.add(server);
				}
			}
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, serverMap);
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, serviceMap);
			redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
		}
		return new ApiResult<>(true);
	}

	@GetMapping("/service")
	@Operation(summary = "查询服务列表", description = "获取所有已上报的服务实例")
	public ApiResult<List<Server>> service() {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		Map<String, Object> serviceMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVICE);
		List<Server> list = new ArrayList<>();
		for (String key : serviceMap.keySet()) {
			List<Server> servers = (List<Server>) serviceMap.get(key);
			for (Server server : servers) {
				if ((System.currentTimeMillis() - server.getCreateTime()) / 1000 / 60 > 5) {
					server.setHealth(false);
				} else {
					server.setHealth(true);
				}
				list.add(server);
			}
		}
		return new ApiResult<>(list);
	}

	@GetMapping("/server")
	@Operation(summary = "查询服务器列表", description = "根据关键字查询服务器信息")
	public ApiResult<List<Server>> server(
			@Parameter(description = "关键字（支持 OS 名称、IP 模糊匹配）")
			String keyword) {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		Map<String, Object> serverMap = monitorMap.get(SYSTEM_MONITOR_MAP_SERVER);
		List<Server> list = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			for (Object object : serverMap.values()) {
				Server server = (Server) object;
				list.add(server);
			}
		} else {
			for (Object object : serverMap.values()) {
				Server server = (Server) object;
				if (server.getSys().getOsName().contains(keyword) || server.getSys().getComputerIp().contains(keyword)) {
					list.add(server);
				}
			}
		}
		return new ApiResult<>(list);
	}

	@GetMapping("/clearServer")
	@Operation(summary = "清空服务器缓存", description = "清空所有已上报的服务器缓存")
	public ApiResult<Boolean> clearServer() {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, new HashMap<>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
		return new ApiResult<>(true);
	}

	@GetMapping("/clearService")
	@Operation(summary = "清空服务缓存", description = "清空所有已上报的服务实例缓存")
	public ApiResult<Boolean> clearService() {
		Map<String, Map<String, Object>> monitorMap = getMapFromRedis();
		monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, new HashMap<>());
		redisTemplate.opsForValue().set(SYSTEM_MONITOR_MAP, monitorMap);
		return new ApiResult<>(true);
	}

	private Map<String, Map<String, Object>> getMapFromRedis() {
		Object redisObject = redisTemplate.opsForValue().get(SYSTEM_MONITOR_MAP);
		Map<String, Map<String, Object>> monitorMap;
		if (redisObject == null) {
			monitorMap = new HashMap<>(2);
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVER, new HashMap<>());
			monitorMap.put(SYSTEM_MONITOR_MAP_SERVICE, new HashMap<>());
		} else {
			monitorMap = (Map<String, Map<String, Object>>) redisObject;
		}
		return monitorMap;
	}
}