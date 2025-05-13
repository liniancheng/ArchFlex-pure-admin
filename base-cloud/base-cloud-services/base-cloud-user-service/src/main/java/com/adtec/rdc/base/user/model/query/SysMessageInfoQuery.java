package com.adtec.rdc.base.user.model.query;

import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author hewei
 * @date 2020-01-13 10:24:57
 */
@Data
public class SysMessageInfoQuery extends Page<SysMessageInfo> {

    /**
     * 消息类型:1-提醒;2-通知
     */
    private String messageType;
    /**
     * 消息状态:0-未读;1-已读
     */
    private String messageStatus;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 消息接收用户
     */
    private String messageRevUser;
    /**
     * 删除标记
     */
    private String delFlag;
    /**
     * 应用ID
     */
    private String appId;
}
