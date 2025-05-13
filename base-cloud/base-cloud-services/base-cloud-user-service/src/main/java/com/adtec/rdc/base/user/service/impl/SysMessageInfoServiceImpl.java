package com.adtec.rdc.base.user.service.impl;

import com.adtec.rdc.base.user.mapper.SysMessageInfoMapper;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.adtec.rdc.base.user.service.SysMessageInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类名：SysMessageInfoServiceImpl</br>
 * 模块：</br>
 * 说明：消息服务实现类</br>
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
@Service
public class SysMessageInfoServiceImpl extends ServiceImpl<SysMessageInfoMapper, SysMessageInfo> implements SysMessageInfoService {
    @Autowired
    private SysMessageInfoMapper mapper;

    @Override
    public SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query) {
        mapper.pageByQuery(query);
        return query;
    }

    @Override
    public SysMessageInfo markRead(String id) {
        SysMessageInfo messageInfo = this.getById(id);
        messageInfo.setMessageStatus("1");
        this.updateById(messageInfo);
        return messageInfo;
    }

    @Override
    public void markRead(List<String> idList) {
        this.updateBatchById(this.listByIds(idList).stream().peek(sysMessageInfo -> sysMessageInfo.setMessageStatus("1")).collect(Collectors.toList()));
    }
}
