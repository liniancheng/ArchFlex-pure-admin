package com.littlelee.base.knowledge.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.constants.CommonConstants;
import com.littlelee.base.common.enums.DataStatusEnum;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.knowledge.mapper.KnowKnowledgeDirInfoMapper;
import com.littlelee.base.knowledge.mapper.KnowKnowledgeInfoMapper;
import com.littlelee.base.knowledge.model.bo.KnowKnowledgeDirInfoTree;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeDirInfoQuery;
import com.littlelee.base.knowledge.service.KnowKnowledgeDirInfoService;
import com.littlelee.base.knowledge.util.TreeUtil;
import com.littlelee.base.user.model.po.SysMenuInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@Service
public class KnowKnowledgeDirInfoServiceImpl extends BaseServiceImpl<KnowKnowledgeDirInfoMapper, KnowKnowledgeDirInfo> implements KnowKnowledgeDirInfoService {
	@Autowired
    private KnowKnowledgeDirInfoMapper mapper;
	@Autowired
	private KnowKnowledgeInfoMapper infoMapper;
	@Override
	public KnowKnowledgeDirInfoQuery pageByQuery(KnowKnowledgeDirInfoQuery query) {
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
    public List<KnowKnowledgeDirInfoTree> getAllKnowledgeTree() {
		QueryWrapper<KnowKnowledgeDirInfo> query  = new QueryWrapper<KnowKnowledgeDirInfo>();
    	List<KnowKnowledgeDirInfo> list = mapper.selectList(query);
    	return TreeUtil.list2Tree(list, CommonConstants.TREE_ROOT);
    }
	
	@Override
    public List<TreeNode> getAllKnowledgeNode(){
		QueryWrapper<KnowKnowledgeDirInfo> query  = new QueryWrapper<KnowKnowledgeDirInfo>();
        List<KnowKnowledgeDirInfo> list = mapper.selectList(query);
        return TreeUtil.list2TreeNode(list, CommonConstants.TREE_ROOT);
	}
	
	@Override
	public boolean save(KnowKnowledgeDirInfo entity) {
		// 判断名称
		if (mapper.isSameTitle(entity.getDirName()) > 0) {
			throw new ServiceException(ErrorCodeEnum.KN_TP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.KN_TP_RE_NAME.getMessage());
		}
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(KnowKnowledgeDirInfo entity) {
		// 判断名称
		if (mapper.isExistKnowTitle(entity)) {
			throw new ServiceException(ErrorCodeEnum.KN_TP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.KN_TP_RE_NAME.getMessage());
		}
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		if(mapper.isExsitByParentId(id)) {
			throw new ServiceException("该类型已有子类型，不能删除！");
		}
		if(infoMapper.isExsitByParentId(id)) {
			throw new ServiceException("该类型已有知识库关联，不能删除！");
		}
		super.removeById(id);
		return Boolean.TRUE;
	}

}
