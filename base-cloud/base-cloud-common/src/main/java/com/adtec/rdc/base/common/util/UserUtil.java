package com.adtec.rdc.base.common.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.adtec.rdc.base.common.app.constants.AppConstants;
import com.adtec.rdc.base.common.constants.SecurityConstants;
import com.adtec.rdc.base.common.constants.UserConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: JTao
 * @date: 2018/10/17 08:56
 * @description:
 */
@Slf4j
public class UserUtil {

	/**
	 * 获取管理端id
	 * @param request
	 * @return
	 */
	public static String getParentAppId(HttpServletRequest request) {
		String parentAppIdExp = request.getHeader(AppConstants.LOGIN_TYPE_EXP);
		if(parentAppIdExp == null) {
			return null;
		}
		log.info("获取管理端id成功，值为{}", parentAppIdExp);
		return parentAppIdExp;
	}
	
	/**
	 * 获取请求中的appId
	 * @param request
	 * @return appId
	 * @description
	 * 	当前用户所在的租户的id
	 */
	public static String getAppId(HttpServletRequest request){
		String tenant = request.getHeader(AppConstants.TOKEN_TENANT);
		if(tenant == null){
			return null;
		}
		String appId = tenant.split(" ")[1];
		log.info("获取appId成功，值为{}", appId);
		return appId;
	}
    /**
     * 获取请求中的token
     * @param request
     * @return token
     */
    public static String getToken(HttpServletRequest request){
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if(authorization == null){
            return null;
        }
        String token = authorization.split(" ")[1];
        log.info("获取token成功，值为{}", token);
        return token;
    }

    /**
     * 获取jwt中的claims信息
     * @param token
     * @return claim
     */
    public static Claims getClaims(String token) {
        String key = Base64.getEncoder().encodeToString(SecurityConstants.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 获取请求中的userId
     * @param request
     * @return userId
     */
    public static String getUserId(HttpServletRequest request){
        String token = getToken(request);
        if(token == null){
            return null;
        }
        Claims claims = getClaims(token);
        String userId = (String) claims.get(UserConstants.USER_ID);
        log.info("获取userId成功，值为{}", userId);
        return userId;
    }

    /**
     * 获取请求中的loginName
     * @param request
     * @return userId
     */
    public static String getLoginName(HttpServletRequest request){
        String token = getToken(request);
        if(token == null){
            return null;
        }
        Claims claims = getClaims(token);
        String loginname = (String) claims.get(UserConstants.LOGIN_NAME);
        log.info("获取loginname成功，值为{}", loginname);
        return loginname;
    }
   
    /**
     * 获取请求中的roles集合
     * @param request
     * @return roles
     */
    public static List<String> getRoleCodes(HttpServletRequest request) {
        String token = getToken(request);
        if(token == null){
            return null;
        }
        Claims claims = getClaims(token);
        List<String> roles = (List<String>) claims.get(UserConstants.AUTHORITIES);
        List<String> roleCodes = new ArrayList<String>(roles.size());
        for(String role : roles) {
        	roleCodes.add(role.substring(0, role.indexOf("=")));
        }
        return roleCodes;
    }
    /**
     * 获取请求中的roles集合
     * @param request
     * @return roles
     */
    public static List<String> getRoleIds(HttpServletRequest request) {
        String token = getToken(request);
        if(token == null){
            return null;
        }
        Claims claims = getClaims(token);
        List<String> roles = (List<String>) claims.get(UserConstants.AUTHORITIES);
        List<String> roleIds = new ArrayList<String>(roles.size());
        for(String role : roles) {
        	roleIds.add(role.substring(role.indexOf("=")+1));
        }
        return roleIds;
    }
}
