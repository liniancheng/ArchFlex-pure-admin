package com.adtec.rdc.base.gateway.service.feign;

import com.adtec.rdc.base.common.model.vo.SysMenuVo;
import com.adtec.rdc.base.gateway.feign.fallback.SysResourceFallback;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author: JTao
 * @date: 2018/11/15 16:36
 */
@FeignClient(value = "base-cloud-user-service", fallback = SysResourceFallback.class)
public interface SysResourceService {

    /**
     * 根据角色查询资源信息
     * @param roleCode
     * @return
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<SysMenuVo> listResourceByRole(@PathVariable("roleCode") String roleCode);

}
