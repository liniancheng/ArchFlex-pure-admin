package com.littlelee.base.anno.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.anno.model.po.SysAnnoInfo;
import com.littlelee.base.anno.model.query.SysAnnoInfoQuery;
import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2019-12-01 20:25:26
 */
public interface SysAnnoInfoMapper extends BaseMapper<SysAnnoInfo> {
	boolean isExistAnnoTitle(SysAnnoInfo anno);
	SysAnnoInfoQuery appPageByQuery(SysAnnoInfoQuery query);
	List<SysAnnoInfo> queryAnnoListUserOper(String userId);
	void updateAnnoUserOper(@Param("updates")List<SysAnnoInfo> updates);
	void insertAnnoUserOper(@Param("inserts")List<SysAnnoInfo> inserts);
}
