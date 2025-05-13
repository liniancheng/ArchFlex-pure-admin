package com.adtec.rdc.base.workflow.mapper;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author adtec
 * @date 2020-07-02 10:06:37
 */
public interface SysWorkflowInstanceNodeAuthMapper extends BaseMapper<SysWorkflowInstanceNodeAuth> {
	void batchInsert(@Param("instanceNodeAuths")List<SysWorkflowInstanceNodeAuth> instanceNodeAuths);
	void deleteByInstanceId(@Param("instanceId")Serializable id);
}
