package com.adtec.rdc.base.auth.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.auth.service.feign.SysUserService;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.PubCodeConstants;
import com.adtec.rdc.base.common.model.vo.SysUserVo;

/**
 * @author: JTao
 * @date: 2018/10/9 10:05
 * @description: security UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private static final List<Object> KEYS = new ArrayList<Object>();
	static {
		KEYS.add(PubCodeConstants.PASSWORD.EDIT_FIRST.getCode());
		KEYS.add(PubCodeConstants.PASSWORD.CYCLE_EDIT.getCode());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUserVo userVo = sysUserService.loadUserByUsername(username);
		List<Object> list = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).multiGet(KEYS);
		Object eidtFirst = list.get(0);
		LocalDateTime last = sysUserService.lastModifyTime(username);
		// 判断初次登录是否强制修改密码
		if (eidtFirst != null && PubCodeConstants.PASSWORD.EDIT_FIRST.getValue().equals(String.valueOf(eidtFirst))) {
			if (last == null) {
				throw new BadCredentialsException("FIRST");
			}
		}
		if (last == null) {
			last = userVo.getCreateTime();
		}
		// 判断周期修改密码(单位：天),判断密码是否过期
		Duration duration = Duration.between(last, LocalDateTime.now());
		if (list.get(1) != null) {
			Integer cycle = Integer.valueOf(String.valueOf(list.get(1)));
			if (duration.toDays() > cycle) {// 未修改密码，被锁定
				throw new BadCredentialsException("LOCKED");
			}
		}	
		return new UserDetailsImpl(userVo);
	}
}
