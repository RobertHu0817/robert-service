package com.ll.platform.robertService.util;

import com.alibaba.fastjson.JSON;
import com.ll.platform.robertService.common.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title WebUtil.java
 * @Description web工具类
 * @author Robert
 * @date 2019年11月29日
 */
public class WebUtil {
	
	/**
	 * 获取请求的真实ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
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
		// 如果是多级代理，那么取第一个ip为客户端ip
		if (ip != null && ip.contains(",")) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}

		return ip;
	}
	
	public static void responseResult(HttpServletResponse response, Result<?> result) {
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try {
			response.getWriter().write(JSON.toJSONString(result));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}