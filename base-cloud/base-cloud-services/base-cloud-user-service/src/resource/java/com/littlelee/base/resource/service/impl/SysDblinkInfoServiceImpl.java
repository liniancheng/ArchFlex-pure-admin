package com.littlelee.base.resource.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.model.vo.SysDblinkVo;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.resource.model.po.SysDblinkInfo;
import com.littlelee.base.resource.model.query.SysDblinkInfoQuery;
import com.littlelee.base.resource.service.SysDblinkInfoService;
import com.littlelee.base.resource.utils.DatabaseUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlelee.base.resource.mapper.SysDblinkInfoMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzhh
 * @date 2019-11-22 14:07:28
 */
@Service
public class SysDblinkInfoServiceImpl extends BaseServiceImpl<SysDblinkInfoMapper, SysDblinkInfo>
		implements SysDblinkInfoService {
	@Autowired
	private SysDblinkInfoMapper mapper;

	@Override
	public SysDblinkInfoQuery pageByQuery(SysDblinkInfoQuery query) {
		query.addOrder(OrderItem.desc("create_time"))
				.addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public boolean save(SysDblinkInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.DB_RE_NAME.getErrorCode(), ErrorCodeEnum.DB_RE_NAME.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	public boolean updateById(SysDblinkInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.DB_RE_NAME.getErrorCode(), ErrorCodeEnum.DB_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}

	@Override
	public Boolean test(String id) {
		SysDblinkVo dblink = getVoById(id);
		if (dblink == null) {
			throw new ServiceException(ErrorCodeEnum.DB_NULL.getErrorCode(), ErrorCodeEnum.DB_NULL.getMessage());
		}
		return DatabaseUtils.testConnection(dblink);
	}

	@Override
	public Boolean isExistName(SysDblinkInfo info) {
		return mapper.isExistName(info);
	}

	@Override
	public List<TreeNode> listDb() {
		QueryWrapper<SysDblinkInfo> query = new QueryWrapper<SysDblinkInfo>();
		query.lambda().orderByAsc(true,	SysDblinkInfo::getCreateTime);
		List<SysDblinkInfo> list = mapper.selectList(query);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		list.forEach(info -> {
			TreeNode node = new TreeNode();
			node.setKey(info.getDblinkId());
			node.setValue(info.getDblinkId());
			node.setTitle(info.getDblinkName());
			nodes.add(node);
		});
		return nodes;
	}

	@Override
	public SysDblinkVo getVoById(String id) {
		return mapper.getVoById(id);
	}

	@Override
	public List<SysDblinkVo> listDbVo() {
		return mapper.listDbVo();
	}

}
