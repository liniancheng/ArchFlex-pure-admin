package com.adtec.rdc.base.anno.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.adtec.rdc.base.anno.mapper.SysAnnoTypeInfoMapper;
import com.adtec.rdc.base.anno.model.po.SysAnnoTypeInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoTypeInfoQuery;
import com.adtec.rdc.base.anno.service.SysAnnoTypeInfoService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
@Service
public class SysAnnoTypeInfoServiceImpl extends BaseServiceImpl<SysAnnoTypeInfoMapper, SysAnnoTypeInfo> implements SysAnnoTypeInfoService {
	@Autowired
    private SysAnnoTypeInfoMapper mapper;
	
	@Override
	public SysAnnoTypeInfoQuery pageByQuery(SysAnnoTypeInfoQuery query) {
		query.setDesc("CREATE_TIME", "MODIFY_TIME");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public boolean isExistTypeName(SysAnnoTypeInfo query) {
		return mapper.isExistTypeName(query);
	}
	
	@Override
	public boolean onlySave(SysAnnoTypeInfo annoType) throws ServiceException {
		if(isExistTypeName(annoType)) {
			throw new ServiceException(ErrorCodeEnum.ANNO_TYPE_RE_NAME.getErrorCode(), ErrorCodeEnum.ANNO_TYPE_RE_NAME.getMessage());

		}
		annoType.setCreateTime(LocalDateTime.now());
		return super.save(annoType);
	}

	@Override
	public boolean onlyUpdate(SysAnnoTypeInfo annoType) throws ServiceException {
		if(isExistTypeName(annoType)) {
			throw new ServiceException(ErrorCodeEnum.ANNO_TYPE_RE_NAME.getErrorCode(), ErrorCodeEnum.ANNO_TYPE_RE_NAME.getMessage());

		}
		annoType.setModifyTime(LocalDateTime.now());
		return super.updateById(annoType);
	}

	@Override
	public List<SysAnnoTypeInfo> findAll(String appId) {
		QueryWrapper<SysAnnoTypeInfo> query = new QueryWrapper<SysAnnoTypeInfo>();
		query.lambda().eq(SysAnnoTypeInfo::getAppId, appId);
		return mapper.selectList(query);
	}
}
