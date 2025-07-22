package com.littlelee.base.anno.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.UUID;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlelee.base.anno.mapper.SysAnnoAttachInfoMapper;
import com.littlelee.base.anno.mapper.SysAnnoInfoMapper;
import com.littlelee.base.anno.mapper.SysAnnoUserOperMapper;
import com.littlelee.base.anno.model.po.SysAnnoUserOper;
import com.littlelee.base.anno.model.po.SysAnnoInfo;
import com.littlelee.base.anno.model.query.SysAnnoAttach;
import com.littlelee.base.anno.model.query.SysAnnoInfoQuery;
import com.littlelee.base.anno.service.SysAnnoInfoService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author littlelee
 * @date 2019-12-01 20:25:26
 */
@Service
public class SysAnnoInfoServiceImpl extends BaseServiceImpl<SysAnnoInfoMapper, SysAnnoInfo>
		implements SysAnnoInfoService {
	@Autowired
	private SysAnnoInfoMapper mapper;
	@Autowired
	private SysAnnoAttachInfoMapper attachMapper;
	@Autowired
	private SysAnnoUserOperMapper annoOperMapper;

	@Override
	public SysAnnoInfoQuery pageByQuery(SysAnnoInfoQuery query) {
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public String onlySave(SysAnnoInfo anno) throws ServiceException {
		if (anno == null) {
			throw new ServiceException(ErrorCodeEnum.ANNO_NULL.getErrorCode(),ErrorCodeEnum.ANNO_NULL.getMessage());
		}
		if (isExistAnnoTitle(anno)) {
			throw new ServiceException(ErrorCodeEnum.ANNO_RE_NAME.getErrorCode(),ErrorCodeEnum.ANNO_RE_NAME.getMessage());
		}
		anno.setCreateTime(LocalDateTime.now());
		anno.setAnnoId(UUID.generate());
		super.save(anno);

		return anno.getAnnoId();
	}

	@Override
	@Transactional
	public String onlyUpdate(SysAnnoInfo anno) throws ServiceException {
		if (anno == null) {
			throw new ServiceException(ErrorCodeEnum.ANNO_NULL.getErrorCode(),ErrorCodeEnum.ANNO_NULL.getMessage());
		}
		if (isExistAnnoTitle(anno)) {
			throw new ServiceException(ErrorCodeEnum.ANNO_RE_NAME.getErrorCode(),ErrorCodeEnum.ANNO_RE_NAME.getMessage());
		}
		anno.setModifyTime(LocalDateTime.now());
		super.updateById(anno);
		// 公告修改之后 ===> 将该公告设置为未读
		QueryWrapper<SysAnnoUserOper> query = new QueryWrapper<SysAnnoUserOper>();
		query.lambda().eq(SysAnnoUserOper::getAnnoId, anno.getAnnoId());
		annoOperMapper.delete(query);

		return anno.getAnnoId();
	}

	@Override
	public boolean isExistAnnoTitle(SysAnnoInfo anno) {
		return mapper.isExistAnnoTitle(anno);
	}

	@Override
	public SysAnnoInfo fineById(String id) {
		SysAnnoInfo anno = mapper.selectById(id);
		if (anno == null) {
			return null;
		}
		List<SysAnnoAttach> attachList = attachMapper.getByAnnoId(id);
		anno.setFileList(attachList);
		return anno;
	}

	@Override
	@Transactional
	public boolean deleteById(String id) {
		super.removeById(id);
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("anno_id", id);
		attachMapper.deleteByMap(columnMap);

//		公告删除，关系表也删除
		QueryWrapper<SysAnnoUserOper> query = new QueryWrapper<SysAnnoUserOper>();
		query.lambda().eq(SysAnnoUserOper::getAnnoId, id);
		annoOperMapper.delete(query);
		return Boolean.TRUE;
	}

	/**
	 * 用户公告查询
	 */
	@Override
	public SysAnnoInfoQuery appPageByQuery(SysAnnoInfoQuery query) {
		query.setIsValid(1);
		query.setEndTime(new Date());
		mapper.appPageByQuery(query);
		return query;
	}

	@Override
	public List<SysAnnoAttach> getAttachById(String annoId) {
		List<SysAnnoAttach> attachList = attachMapper.getByAnnoId(annoId);
		return attachList;
	}
}
