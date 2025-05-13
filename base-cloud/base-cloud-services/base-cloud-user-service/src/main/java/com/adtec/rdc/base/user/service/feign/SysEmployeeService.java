package com.adtec.rdc.base.user.service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.adtec.rdc.base.common.model.vo.DgmpFwEmployeeInfoVo;
import com.adtec.rdc.base.user.service.fallback.SysEmployeeServiceFallBack;

/**
 * <p>
 * feign 调用服务
 * </p>
 *
 * @author: adtec
 * @since 2020-07-23
 */
@FeignClient(name = "dgmp-common-service", fallback = SysEmployeeServiceFallBack.class)
public interface SysEmployeeService {

    /**
     * 通过员工id查询员工列表
     * @param username
     * @return
     */
	@PostMapping("/employInfo/employees")
    public  List<DgmpFwEmployeeInfoVo>  listEmployees(@RequestBody List<String> ids );

}
