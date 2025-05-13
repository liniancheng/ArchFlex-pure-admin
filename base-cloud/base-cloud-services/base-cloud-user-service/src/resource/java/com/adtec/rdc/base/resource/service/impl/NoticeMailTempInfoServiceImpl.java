package com.adtec.rdc.base.resource.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.resource.mapper.NoticeMailTempInfoMapper;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;
import com.adtec.rdc.base.resource.model.query.NoticeMailTempInfoQuery;
import com.adtec.rdc.base.resource.service.NoticeMailTempInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
@Service
public class NoticeMailTempInfoServiceImpl extends BaseServiceImpl<NoticeMailTempInfoMapper, NoticeMailTempInfo> implements NoticeMailTempInfoService {
	@Autowired
    private NoticeMailTempInfoMapper mapper;
	
	@Override
	public NoticeMailTempInfoQuery pageByQuery(NoticeMailTempInfoQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
	@Override
	public boolean save(NoticeMailTempInfo entity) {
		// 判断模板名称
		if (mapper.isSameTempName(entity.getTempName(),entity.getAppId()) > 0) {
			throw new ServiceException(ErrorCodeEnum.TEMP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.TEMP_RE_NAME.getMessage());
		}
		return super.save(entity);
	}
	@Override
	public boolean updateById(NoticeMailTempInfo entity) {
		// 判断模板名称
		if (mapper.isExistSrvTitle(entity)) {
			throw new ServiceException(ErrorCodeEnum.TEMP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.TEMP_RE_NAME.getMessage());
		}
		return super.updateById(entity);
	}
	
	@Override
	public List<NoticeMailTempInfo> listTemp(String appId) {
		QueryWrapper<NoticeMailTempInfo> query = new QueryWrapper<NoticeMailTempInfo>();
		query.lambda().eq(NoticeMailTempInfo::getAppId, appId);
		return mapper.selectList(query);
	}
}
