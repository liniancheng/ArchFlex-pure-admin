package com.adtec.rdc.base.gateway.feign.fallback;

import com.adtec.rdc.base.common.model.vo.SysMenuVo;
import com.adtec.rdc.base.gateway.service.feign.SysResourceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * @author: JTao
 * @date: 2018/11/15 16:37
 */
@Slf4j
@Service
public class SysResourceFallback implements SysResourceService {
    @Override
    public Set<SysMenuVo> listResourceByRole(String roleCode) {
        log.error("调用【base-cloud-user-service】服务接口【/resource/role/{}】异常", roleCode);
        return Collections.emptySet();
    }
}
