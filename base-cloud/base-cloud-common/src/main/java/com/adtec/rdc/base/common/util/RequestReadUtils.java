package com.adtec.rdc.base.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;

import com.google.gson.Gson;

public class RequestReadUtils {
	public static String getRequestParams(HttpServletRequest request, ProceedingJoinPoint pjp) {
		String method = request.getMethod().toUpperCase();
        String type = request.getContentType();
        Gson gson = new Gson();
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
