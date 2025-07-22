package com.littlelee.base.knowledge.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
public interface KnowKnowledgeDirInfoMapper extends BaseMapper<KnowKnowledgeDirInfo> {
	public Integer isSameTitle(@Param("dirName") String dirName);
	boolean isExistKnowTitle(KnowKnowledgeDirInfo entity);
	public boolean isExsitByParentId(@Param("parentId") Serializable  dirId);

}
