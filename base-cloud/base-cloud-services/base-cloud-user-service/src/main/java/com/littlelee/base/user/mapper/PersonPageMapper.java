package com.littlelee.base.user.mapper;

import com.littlelee.base.user.model.bo.MessageInfo;
import com.littlelee.base.user.model.query.SysMessageInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PersonPageMapper extends BaseMapper<MessageInfo> {
	SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query);
}
