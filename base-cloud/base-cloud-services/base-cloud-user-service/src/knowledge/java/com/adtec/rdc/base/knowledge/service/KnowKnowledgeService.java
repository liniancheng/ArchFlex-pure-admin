package com.adtec.rdc.base.knowledge.service;

import java.util.List;

import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeVo;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.adtec.rdc.web.antd.bo.VueTreeNode;

public interface KnowKnowledgeService {
	List<VueTreeNode> tree(String id, String appId, List<String> roleCodes);
	KnowKnowledgeVo getKnowKnowledgeById(String knowId);
	List<KnowKnowledgeInfo> getInfoByType(String typeId,String appId);
}
