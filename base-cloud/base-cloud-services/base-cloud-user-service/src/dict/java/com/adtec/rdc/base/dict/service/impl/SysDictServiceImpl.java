package com.adtec.rdc.base.dict.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.dict.mapper.SysDictMapper;
import com.adtec.rdc.base.dict.model.po.SysDict;
import com.adtec.rdc.base.dict.model.query.SysDictQuery;
import com.adtec.rdc.base.dict.service.SysDictService;
import com.adtec.rdc.base.enums.ErrorCodeEnum;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
@Service
@Transactional
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {
	@Autowired
    private SysDictMapper mapper;
	
	@Override
	public SysDictQuery pageByQuery(SysDictQuery query) {
		query.setDesc("CREATE_TIME");
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
