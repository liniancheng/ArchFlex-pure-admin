package com.littlelee.base.user.service.fallback;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.littlelee.base.common.model.vo.DgmpFwEmployeeInfoVo;
import com.littlelee.base.user.service.feign.SysEmployeeService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SysEmployeeServiceFallBack implements SysEmployeeService{
	@Override
	public List<DgmpFwEmployeeInfoVo> listEmployees(List<String> ids) {
		log.error("调用【base-cloud-user-service】服务接口【/employInfo/listEmployees{}】异常", ids);
		return Collections.emptyList();
	}
}
