package com.adtec.rdc.base.user.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.user.mapper.SysUserCommentInfoMapper;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.user.model.po.SysUserCommentInfo;
import com.adtec.rdc.base.user.model.query.SysUserCommentInfoQuery;
import com.adtec.rdc.base.user.service.SysUserCommentInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author adtec
 * @date 2020-07-28 15:38:50
 */
@Service
public class SysUserCommentInfoServiceImpl extends BaseServiceImpl<SysUserCommentInfoMapper, SysUserCommentInfo> implements SysUserCommentInfoService {
	@Autowired
    private SysUserCommentInfoMapper mapper;
	@Autowired
    private SysUserInfoMapper userMapper;
	
	@Override
	public SysUserCommentInfoQuery pageByQuery(SysUserCommentInfoQuery query) {
		query.setDesc("create_time");
		mapper.pageByQuery(query);
		if(query.getRecords().size()>0) {
			for(SysUserCommentInfo comment : query.getRecords()) {
				if(comment.getCreateUser().equals(query.getCurrentUserId())) {
					comment.setMyComment(true);
				}
			}
		}
        return query;
	}

	@Override
	public SysUserCommentInfo saveComment(SysUserCommentInfo sysUserCommentInfo) {
		super.save(sysUserCommentInfo);
		sysUserCommentInfo.setUserName(userMapper.selectById(sysUserCommentInfo.getCreateUser()).getUserName());
		return sysUserCommentInfo;
	}
}
