package com.adtec.rdc.base.auth.captcha;

import com.adtec.rdc.base.common.util.CaptchaUtil;
import com.adtec.rdc.base.common.util.NetworkUtil;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类名：CaptchaFilter</br>
 * 模块：图片验证码filter</br>
 * 说明：</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author littlelee
 * @version V1.0
 * @copyright littlelee
 * @since 2025/06/20
 */
public class CaptchaFilter extends OncePerRequestFilter {

    @Setter
    @Getter
    private AuthenticationFailureHandler failureHandler;

    @Setter
    @Getter
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/oauth/token", request.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", request.getMethod())) {
            try {
                validate(request);
            } catch (BadCredentialsException captchaException) {
                //失败调用我们的自定义失败处理器
                failureHandler.onAuthenticationFailure(request, response, captchaException);
                //后续流程终止
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
    	String codeId = NetworkUtil.getIpAddress(request);//使用客户端IP作用KEY
    	String redisKey = CaptchaUtil.getCaptchaRedisKey(codeId);
    	String captchaInRedis = redisTemplate.opsForValue().get(redisKey);
    	if(StringUtils.isEmpty(captchaInRedis)) {
    		//没有验证码时(第一次进入登录页不显示验证码,登录失败后显示)
    		return;
    	}
        //移除缓存
        redisTemplate.delete(redisKey);
    	//验证
    	String captchaInRequest = ServletRequestUtils.getStringParameter(request, "captcha");
        if (StringUtils.isBlank(captchaInRequest)) {
            throw new BadCredentialsException("验证码不能为空");
        }
        String[] captchas = captchaInRequest.split("@");
        if ( captchas.length < 2 || StringUtils.isBlank(captchas[0])) {
            throw new BadCredentialsException("验证码不能为空");
        }
        if (!StringUtils.equalsIgnoreCase(captchaInRedis, captchas[0]) 
        		|| !StringUtils.equalsIgnoreCase(codeId, captchas[1]) ) {
            throw new BadCredentialsException("验证码不正确");
        }
    }
}
