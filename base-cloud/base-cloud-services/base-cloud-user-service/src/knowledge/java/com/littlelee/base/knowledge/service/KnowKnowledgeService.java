package com.littlelee.base.knowledge.service;

import java.util.List;

import com.littlelee.base.knowledge.model.bo.KnowKnowledgeVo;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;
import com.littlelee.web.antd.bo.VueTreeNode;

public interface KnowKnowledgeService {
	List<VueTreeNode> tree(String id, String appId, List<String> roleCodes);
	KnowKnowledgeVo getKnowKnowledgeById(String knowId);
	List<KnowKnowledgeInfo> getInfoByType(String typeId,String appId);
}
