package com.littlelee.base.user.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.user.mapper.SysUserCommentInfoMapper;
import com.littlelee.base.user.mapper.SysUserInfoMapper;
import com.littlelee.base.user.model.po.SysUserCommentInfo;
import com.littlelee.base.user.model.query.SysUserCommentInfoQuery;
import com.littlelee.base.user.service.SysUserCommentInfoService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
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
		query.addOrder(OrderItem.desc("create_time"));
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
