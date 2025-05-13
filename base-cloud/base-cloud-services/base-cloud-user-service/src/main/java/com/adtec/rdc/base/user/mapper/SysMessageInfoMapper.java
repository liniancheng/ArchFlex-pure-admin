package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;

/**
 * 接口名：SysMessageInfoMapper</br>
 * 模块：</br>
 * 说明：消息mapper接口</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright ADTEC
 * @since 2020/1/13
 */
public interface SysMessageInfoMapper extends BaseMapper<SysMessageInfo> {
	List<SysMessageInfo> queryMessageListByUserIdAndIdList(@Param("userId")String userId, @Param("idList")List<String> idList);
}
