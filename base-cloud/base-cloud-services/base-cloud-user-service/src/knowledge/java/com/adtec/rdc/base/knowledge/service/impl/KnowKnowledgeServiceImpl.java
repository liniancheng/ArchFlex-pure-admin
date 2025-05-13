package com.adtec.rdc.base.knowledge.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.knowledge.mapper.KnowKnowledgeAttachInfoMapper;
import com.adtec.rdc.base.knowledge.mapper.KnowKnowledgeDirInfoMapper;
import com.adtec.rdc.base.knowledge.mapper.KnowKnowledgeInfoMapper;
import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeVo;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeService;
import com.adtec.rdc.base.knowledge.util.KnowKnowledgeTreeUtils;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.web.antd.bo.VueTreeNode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class KnowKnowledgeServiceImpl extends BaseServiceImpl<KnowKnowledgeInfoMapper, KnowKnowledgeInfo> implements KnowKnowledgeService {
	@Autowired
	private KnowKnowledgeInfoMapper mapper;
	@Autowired
	private SysUserInfoMapper userMapper;
	@Autowired
	private KnowKnowledgeDirInfoMapper dirMapper;
	@Autowired
	private KnowKnowledgeAttachInfoMapper attachMapper;
	
	@Override
	public List<VueTreeNode> tree(String dirId, String appId, List<String> roleCodes) {
		List<KnowKnowledgeDirInfo> dirs = null;
		List<KnowKnowledgeInfo> knows = null;
		boolean queryAll = false;//是否查询全部
		if("-1".equals(dirId)) {
			QueryWrapper<KnowKnowledgeInfo> wraper = new QueryWrapper<>();
			wraper.lambda().eq(KnowKnowledgeInfo::getAppId, appId);
			int count =  mapper.selectCount(wraper);
			//数量超过1000时异步加载
			if(count<1000) {
				queryAll = true;
			}
		}
		if(queryAll) {
			QueryWrapper<KnowKnowledgeDirInfo> dirWraper = new QueryWrapper<>();
			dirWraper.lambda().eq(KnowKnowledgeDirInfo::getAppId, appId).orderByAsc(KnowKnowledgeDirInfo::getDirOrder);
			dirs = dirMapper.selectList(dirWraper);
			QueryWrapper<KnowKnowledgeInfo> knowWraper = new QueryWrapper<>();
			knowWraper.lambda().eq(KnowKnowledgeInfo::getAppId, appId).orderByDesc(KnowKnowledgeInfo::getCreateTime);
			knows = mapper.selectList(knowWraper);
		}else {
			QueryWrapper<KnowKnowledgeDirInfo> dirWraper = new QueryWrapper<>();
			dirWraper.lambda().eq(KnowKnowledgeDirInfo::getAppId, appId).eq(KnowKnowledgeDirInfo::getParentId, dirId).orderByAsc(KnowKnowledgeDirInfo::getDirOrder);
			dirs = dirMapper.selectList(dirWraper);
			QueryWrapper<KnowKnowledgeInfo> knowWraper = new QueryWrapper<>();
			knowWraper.lambda().eq(KnowKnowledgeInfo::getAppId, appId).eq(KnowKnowledgeInfo::getDirId, dirId).orderByDesc(KnowKnowledgeInfo::getCreateTime);
			knows = mapper.selectList(knowWraper);
		}
		return KnowKnowledgeTreeUtils.tree(dirs, knows, dirId);
	}

	@Override
	public KnowKnowledgeVo getKnowKnowledgeById(String knowId) {
		KnowKnowledgeInfo know = mapper.selectById(knowId);
		KnowKnowledgeVo knowVo = new KnowKnowledgeVo();
		if(know != null) {
			//当前标准
			BeanUtils.copyProperties(know, knowVo);
			knowVo.setAttachs(attachMapper.getByKnowId(knowId));
			knowVo.setUserName(userMapper.loadUserById(knowVo.getCreateUser()).getUserName());
		}
		return knowVo;
	}

	@Override
	public List<KnowKnowledgeInfo> getInfoByType(String typeId,String appId) {
		return mapper.getInfoByType(typeId,appId);
	}

}
