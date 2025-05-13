package com.adtec.rdc.base.dict.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.constants.SecurityConstants;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.dict.mapper.SysDictItemMapper;
import com.adtec.rdc.base.dict.mapper.SysDictMapper;
import com.adtec.rdc.base.dict.model.po.SysDict;
import com.adtec.rdc.base.dict.model.po.SysDictItem;
import com.adtec.rdc.base.dict.model.query.SysDictItemQuery;
import com.adtec.rdc.base.dict.model.vo.DictModel;
import com.adtec.rdc.base.dict.service.SysDictItemService;
import com.adtec.rdc.base.dict.util.RedisUtil;
import com.alibaba.fastjson.JSON;

/**
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
@Service
@Transactional
public class SysDictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper, SysDictItem>
		implements SysDictItemService {
	@Autowired
	private SysDictItemMapper mapper;
	@Autowired
	private SysDictMapper dictMapper;
	@Autowired
	private RedisUtil<String, String> redisUtil;

	@Override
	public SysDictItemQuery pageByQuery(SysDictItemQuery query) {
		query.setDesc("SORT_ORDER");
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public List<SysDictItem> selectItemsByDictId(String dictId) {
		return mapper.selectItemsByDictId(dictId);
	}

	@Override
	public boolean save(SysDictItem entity) {
		entity.setId(UUID.generate());
		boolean isSucess = doCacheAdd(entity);
		return isSucess;
	}

	@Override
	public boolean updateById(SysDictItem entity) {
		boolean isSucess = super.updateById(entity);
		SysDict dict = dictMapper.selectById(entity.getDictId());
		isSucess = doCacheEdit(dict, isSucess);
		return isSucess;
	}

	/**
	 * @param entity
	 * @param isSucess
	 * @return
	 */
	public boolean doCacheAdd(SysDictItem entity) {
		boolean isSucess = super.save(entity);
		SysDict dict = dictMapper.selectById(entity.getDictId());
		doCacheEdit(dict,isSucess);
		return isSucess;
	}

	/**
	 * @param entity
	 * @param isSucess
	 * @return
	 */
	public boolean doCacheEdit(SysDict dict, boolean isSucess) {
		redisUtil.delete(SecurityConstants.REDIS_SYS_DICT_PREFIX + dict.getDictCode());
		List<DictModel> itemsByCode = mapper.queryDictItemsByCode(dict.getDictCode());
		String json = JSON.toJSONString(itemsByCode); // List转json
		redisUtil.set(SecurityConstants.REDIS_SYS_DICT_PREFIX + dict.getDictCode(),json);
		return isSucess;
	}

	@Override
	public boolean removeById(Serializable id) {
		SysDictItem entity = mapper.selectById(id);
		SysDict dict = dictMapper.selectById(entity.getDictId());
		boolean isSucess = super.removeById(id);
		isSucess = doCacheEdit(dict, isSucess);
		return isSucess;
	}

	@Override
	public List<DictModel> queryDictItemsByCode(String code) {
		String json = redisUtil.get(SecurityConstants.REDIS_SYS_DICT_PREFIX +code);
		List<DictModel> resultList = new ArrayList<>();
		if(!StringUtils.isEmpty(json)) {
			resultList = JSON.parseArray(json,DictModel.class);
		}else {
			resultList = mapper.queryDictItemsByCode(code);
			json = JSON.toJSONString(resultList); // List转json
			redisUtil.set(SecurityConstants.REDIS_SYS_DICT_PREFIX + code,json);
		}
		return resultList;
	}

	/**
	 * 通过查询指定table的 text code 获取字典
	 * @param table
	 * @param text
	 * @param code
	 * @return
	 */
	@Override
	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
		return mapper.queryTableDictItemsByCode(table,text,code);
	}

	@Override
	public List<SysDictItem> getByDictCode(String dictCode) {
		QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SysDict::getDictCode,dictCode);
		SysDict sysDict = dictMapper.selectOne(queryWrapper);
		QueryWrapper<SysDictItem> itemQueryWrapper = new QueryWrapper<>();
		itemQueryWrapper.lambda().eq(SysDictItem::getDictId,sysDict.getId());
		List<SysDictItem> sysDictItemList = mapper.selectList(itemQueryWrapper);
		return sysDictItemList;
	}
}
