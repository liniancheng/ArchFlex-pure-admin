package com.littlelee.base.dict.service.impl;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.UUID;
import com.littlelee.base.dict.mapper.SysDictMapper;
import com.littlelee.base.dict.model.po.SysDict;
import com.littlelee.base.dict.model.query.SysDictQuery;
import com.littlelee.base.dict.service.SysDictService;
import com.littlelee.base.enums.ErrorCodeEnum;

/**
 * @author littlelee
 * @date 2020-06-28 07:42:28
 */
@Service
@Transactional
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {
	@Autowired
    private SysDictMapper mapper;
	
	@Override
	public SysDictQuery pageByQuery(SysDictQuery query) {
		query.addOrder(OrderItem.desc("CREATE_TIME"));
		mapper.pageByQuery(query);
        return query;
	}
	@Override
	public boolean save(SysDict entity) {
		if (isSameDictCode(entity.getDictCode()) > 0) {
			throw new ServiceException(ErrorCodeEnum.DICT_RE_NAME.getErrorCode(),
					ErrorCodeEnum.DICT_RE_NAME.getMessage());
		}
		entity.setId(UUID.generate());
		return super.save(entity);
	}
	
	public Integer isSameDictCode(String dictCode) {
		return mapper.isSameDictCode(dictCode);
	} 
	@Override
	public boolean updateById(SysDict entity) {
		return super.updateById(entity);
	}
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
}
