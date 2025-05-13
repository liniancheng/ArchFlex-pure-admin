package com.adtec.rdc.base.migrate.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.migrate.mapper.SysMigrateInfoMapper;
import com.adtec.rdc.base.migrate.model.bo.SysMigrateTree;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;
import com.adtec.rdc.base.migrate.model.query.SysMigrateInfoQuery;
import com.adtec.rdc.base.migrate.service.SysMigrateInfoService;
import com.adtec.rdc.base.migrate.utils.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@Service
public class SysMigrateInfoServiceImpl extends BaseServiceImpl<SysMigrateInfoMapper, SysMigrateInfo> implements SysMigrateInfoService {
	@Autowired
    private SysMigrateInfoMapper mapper;
	
	@Override
	public SysMigrateInfoQuery pageByQuery(SysMigrateInfoQuery query) {
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public List<SysMigrateTree> getTreeNodes(String parentId) {
		List<SysMigrateTree> treeNodes = new ArrayList<SysMigrateTree>();
		if(StringUtils.isEmpty(parentId)) {
			parentId = CommonConstants.TREE_ROOT;
		}
		QueryWrapper<SysMigrateInfo> query = new QueryWrapper<SysMigrateInfo>();
		query.lambda().eq(SysMigrateInfo::getParentMigId, parentId);
		List<SysMigrateInfo> list = mapper.selectList(query);
		list.forEach(info ->{
			treeNodes.add(new SysMigrateTree(info));
		});
		return treeNodes;
	}

	@Override
	public List<SysMigrateTree> getAllTreeNodes() {
		List<SysMigrateInfo> list = mapper.findAll();
		return TreeUtil.listToTree(list, CommonConstants.TREE_ROOT);
	}

	@Override
	public Boolean onlySave(SysMigrateInfo migrate) {
		if(StringUtils.isEmpty(migrate.getParentMigId())) {
			migrate.setParentMigId(CommonConstants.TREE_ROOT);
		}
		
		return super.save(migrate);
	}

	@Override
	public Boolean onlyUpdateById(SysMigrateInfo migrate) {
		return super.updateById(migrate);
	}
}
