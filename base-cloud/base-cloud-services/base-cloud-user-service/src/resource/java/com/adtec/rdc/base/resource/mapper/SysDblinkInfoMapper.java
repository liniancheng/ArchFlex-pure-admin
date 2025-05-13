package com.adtec.rdc.base.resource.mapper;

import com.adtec.rdc.base.resource.model.po.SysDblinkInfo;

import java.util.List;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.common.model.vo.SysDblinkVo;

/**
 * @author xuzhh
 * @date 2019-11-22 14:07:28
 */
public interface SysDblinkInfoMapper extends BaseMapper<SysDblinkInfo> {
	/**
	 * 判断是否重名
	 *
	 * @author dengchf
	 */
	Boolean isExistName(SysDblinkInfo info);

	/**
	 * 按照id查询数据源信息
	 *
	 * @author dengchf
	 */
	SysDblinkVo getVoById(String id);

	/**
	 * 查询所有可用数据源
	 *
	 * @author dengchf
	 */
	List<SysDblinkVo> listDbVo();

}
