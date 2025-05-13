package com.adtec.rdc.base.knowledge.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeInfoQuery;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
public interface KnowKnowledgeInfoService extends BaseService<KnowKnowledgeInfo> {
	KnowKnowledgeInfoQuery pageByQuery(KnowKnowledgeInfoQuery query);
	public String onlySave(KnowKnowledgeInfo entity) ;
	public String onlyUpdate(KnowKnowledgeInfo entity) ;
	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	KnowKnowledgeInfo fineById(String id);
	/**
	 * 按id删除知识库（含附件）
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);
}
