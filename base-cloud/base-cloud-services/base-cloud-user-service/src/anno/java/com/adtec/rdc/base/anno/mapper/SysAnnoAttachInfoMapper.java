package com.adtec.rdc.base.anno.mapper;

import java.util.List;

import com.adtec.rdc.base.anno.model.po.SysAnnoAttachInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2019-12-09 16:56:44
 */
public interface SysAnnoAttachInfoMapper extends BaseMapper<SysAnnoAttachInfo> {
	/**
	 * 按公告id查询附件列表
	 * @param annoId
	 * @return
	 */
	List<SysAnnoAttach> getByAnnoId(String annoId);
	/**
	 * 判断同一个公告中附件名称是否重复
	 * @param annoId
	 * @param attName
	 * @return
	 */
	Boolean isExistName(String annoId,String attName);
	

}
