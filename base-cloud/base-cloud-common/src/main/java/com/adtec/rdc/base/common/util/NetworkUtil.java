package com.adtec.rdc.base.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class NetworkUtil {
	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("HTTP_CLIENT_IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
			} else if (ip.length() > 15) {
				String[] ips = ip.split(",");
				for (int index = 0; index < ips.length; index++) {
					String strIp = (String) ips[index];
					if (!("unknown".equalsIgnoreCase(strIp))) {
						ip = strIp;
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("获取客户端IP异常");
			e.printStackTrace();
		}
		if (ip == null || ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ip = inet.getHostAddress();
		}
		return ip;
	}
}
