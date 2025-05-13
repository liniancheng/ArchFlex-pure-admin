package com.adtec.rdc.base.auth.service.feign;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.adtec.rdc.base.auth.service.fallback.SysUserServiceFallback;
import com.adtec.rdc.base.common.model.vo.SysUserVo;

/**
 * <p>
 * feign 调用服务
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
@FeignClient(name = "base-cloud-user-service", fallback = SysUserServiceFallback.class)
public interface SysUserService {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    @GetMapping("/user/loadUserByUsername/{username}")
    SysUserVo loadUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 通过mobile查找用户
     * @param mobile
     * @return
     */
    @GetMapping("/user/loadUserByMobile/{mobile}")
    SysUserVo loadUserByMobile(@PathVariable(value = "mobile") String mobile);
    /**
     * 判断密码是否修改过
     */
	@GetMapping("/user/lastModifyTime/{loginName}")
	LocalDateTime lastModifyTime(@PathVariable("loginName") String loginName);

}
