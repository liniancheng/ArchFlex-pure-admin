package com.littlelee.base.knowledge.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeInfoQuery;

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
