package com.adtec.rdc.base.knowledge.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeDirInfoTree;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeDirInfoQuery;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
public interface KnowKnowledgeDirInfoService extends BaseService<KnowKnowledgeDirInfo> {
	KnowKnowledgeDirInfoQuery pageByQuery(KnowKnowledgeDirInfoQuery query);
	/**
     * 查询所有的类型
     * @param appId 
     * @return
     */
    List<KnowKnowledgeDirInfoTree> getAllKnowledgeTree();
    
    /**
     * 查询所有的类型
     * @param appId 
     * @return
     */
    List<TreeNode> getAllKnowledgeNode();
}
