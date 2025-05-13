package com.adtec.rdc.base.knowledge.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
public interface KnowKnowledgeInfoMapper extends BaseMapper<KnowKnowledgeInfo> {
	
	public Integer isSameTitle(@Param("title") String title,@Param("dirId") String dirId,@Param("appId") String appId);
	boolean isExistKnowTitle(KnowKnowledgeInfo entity);
	List<KnowKnowledgeInfo> getInfoByType(@Param("dirId") String dirId,@Param("appId") String appId);
	public boolean isExsitByParentId(@Param("dirId") Serializable  dirId);

}
