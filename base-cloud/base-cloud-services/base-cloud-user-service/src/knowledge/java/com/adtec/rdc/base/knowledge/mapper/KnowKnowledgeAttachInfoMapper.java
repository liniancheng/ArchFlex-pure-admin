package com.adtec.rdc.base.knowledge.mapper;

import java.util.List;

import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
public interface KnowKnowledgeAttachInfoMapper extends BaseMapper<KnowKnowledgeAttachInfo> {
	
	/**
	 * 按知识id查询附件列表
	 * @param annoId
	 * @return
	 */
	List<KnowKnowledgeAttachInfo> getByKnowledgeId(String knowledgeId);
	/**
	 * 判断同一个公告中附件名称是否重复
	 * @param annoId
	 * @param attName
	 * @return
	 */
	Boolean isExistName(String attName);
	
	
	/**
	 * 按知识库id查询附件列表
	 * @param annoId
	 * @return
	 */
	List<KnowKnowledgeAttachInfo> getByKnowId(String knowledgeId);

}
