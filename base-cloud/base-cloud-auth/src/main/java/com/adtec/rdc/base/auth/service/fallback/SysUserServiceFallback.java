package com.adtec.rdc.base.auth.service.fallback;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.adtec.rdc.base.auth.service.feign.SysUserService;
import com.adtec.rdc.base.common.model.vo.SysUserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
@Slf4j
@Service
public class SysUserServiceFallback implements SysUserService {

    @Override
    public SysUserVo loadUserByUsername(String username) {
        log.error("调用loadUserByUsername方法异常，参数：{}", username);
       return null;
    }

    @Override
    public SysUserVo loadUserByMobile(String mobile) {
        log.error("调用loadUserByMobile方法异常，参数：{}", mobile);
        return null;
    }

	@Override
	public LocalDateTime lastModifyTime(String loginName) {
		log.error("调用lastModifyTime方法异常，参数：{}", loginName);
		return null;
	}
}
