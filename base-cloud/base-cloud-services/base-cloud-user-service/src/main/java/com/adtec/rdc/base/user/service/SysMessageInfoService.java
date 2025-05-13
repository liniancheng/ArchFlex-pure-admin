package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 接口名：SysMessageInfoService</br>
 * 模块：</br>
 * 说明：消息服务接口</br>
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
public interface SysMessageInfoService extends IService<SysMessageInfo> {
    SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query);

    SysMessageInfo markRead(String id);

    void markRead(List<String> idList);
}
