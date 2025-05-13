package com.adtec.rdc.base.resource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.resource.mapper.NoticeSmsSrvInfoMapper;
import com.adtec.rdc.base.resource.model.po.NoticeSmsSrvInfo;
import com.adtec.rdc.base.resource.model.query.NoticeSmsSrvInfoQuery;
import com.adtec.rdc.base.resource.service.NoticeSmsSrvInfoService;

/**
 * @author adtec
 * @date 2020-06-16 13:00:26
 */
@Service
public class NoticeSmsSrvInfoServiceImpl extends BaseServiceImpl<NoticeSmsSrvInfoMapper, NoticeSmsSrvInfo> implements NoticeSmsSrvInfoService {
	@Autowired
    private NoticeSmsSrvInfoMapper mapper;
	
	@Override
	public NoticeSmsSrvInfoQuery pageByQuery(NoticeSmsSrvInfoQuery query) {
		mapper.pageByQuery(query);
        return query;
	}
	@Override
	public boolean save(NoticeSmsSrvInfo entity) {
		// 判断服务器名称
		if (mapper.isSameSrvName(entity.getSrvName(),entity.getAppId()) > 0) {
			throw new ServiceException(ErrorCodeEnum.SRV_RE_NAME.getErrorCode(),
					ErrorCodeEnum.SRV_RE_NAME.getMessage());
		}
		return super.save(entity);
	}
	@Override
	public boolean updateById(NoticeSmsSrvInfo entity) {
		// 判断服务器名称
		if (mapper.isExistSmsTitle(entity)) {
			throw new ServiceException(ErrorCodeEnum.SRV_RE_NAME.getErrorCode(),
					ErrorCodeEnum.SRV_RE_NAME.getMessage());
		}
		return super.updateById(entity);
	}
}
