package com.littlelee.base.common.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.littlelee.base.common.config.LocalDateTimeTypeAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;

import com.google.gson.Gson;

public class RequestReadUtils {
	public static String getRequestParams(HttpServletRequest request, ProceedingJoinPoint pjp) {
		String method = request.getMethod().toUpperCase();
        String type = request.getContentType();
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
				.create();
        if (!"GET".equals(method) && !"DELETE".equals(method) && type != null && type.indexOf("application/json")>-1) {
        	if(pjp.getArgs()==null) {
        		return "";
        	}else {
        		Object[] args = pjp.getArgs();
        		List<Object> list = new ArrayList<Object>();
        		for(int i=0; i<args.length; i++) {
        			if(args[i] instanceof HttpServletRequest) {
        				continue;
        			}else if(args[i] instanceof HttpServletResponse) {
        				continue;
        			}else if(args[i] instanceof HttpSession) {
        				continue;
        			}else {
        				list.add(args[i]);
        			}
        		}
        		return gson.toJson(list);
        	}
        }else {
        	return gson.toJson(request.getParameterMap());
        }
	}
}
