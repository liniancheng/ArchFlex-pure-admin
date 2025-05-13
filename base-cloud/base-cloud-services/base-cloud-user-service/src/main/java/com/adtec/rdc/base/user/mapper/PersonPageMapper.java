package com.adtec.rdc.base.user.mapper;

import com.adtec.rdc.base.user.model.bo.MessageInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PersonPageMapper extends BaseMapper<MessageInfo> {
	SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query);
}
