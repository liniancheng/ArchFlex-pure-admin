package com.littlelee.base.resource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.resource.mapper.NoticeSmsTempInfoMapper;
import com.littlelee.base.resource.model.po.NoticeSmsSrvInfo;
import com.littlelee.base.resource.model.po.NoticeSmsTempInfo;
import com.littlelee.base.resource.model.query.NoticeSmsTempInfoQuery;
import com.littlelee.base.resource.service.NoticeSmsTempInfoService;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
@Service
public class NoticeSmsTempInfoServiceImpl extends BaseServiceImpl<NoticeSmsTempInfoMapper, NoticeSmsTempInfo> implements NoticeSmsTempInfoService {
	@Autowired
    private NoticeSmsTempInfoMapper mapper;
	
	@Override
	public NoticeSmsTempInfoQuery pageByQuery(NoticeSmsTempInfoQuery query) {
//		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	public boolean save(NoticeSmsTempInfo entity) {
		// 判断模板名称
		if (mapper.isSameTempName(entity.getTempName(),entity.getAppId()) > 0) {
			throw new ServiceException(ErrorCodeEnum.TEMP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.TEMP_RE_NAME.getMessage());
		}
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(NoticeSmsTempInfo entity) {
		// 判断服务器名称
		if (mapper.isExistSmsTitle(entity)) {
			throw new ServiceException(ErrorCodeEnum.TEMP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.TEMP_RE_NAME.getMessage());
		}
		return super.updateById(entity);
	}
}
