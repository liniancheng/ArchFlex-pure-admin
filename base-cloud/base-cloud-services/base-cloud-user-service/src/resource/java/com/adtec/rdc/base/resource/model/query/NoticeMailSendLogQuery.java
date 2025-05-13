package com.adtec.rdc.base.resource.model.query;

import java.util.Date;

import com.adtec.rdc.base.resource.model.po.NoticeMailSendLog;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
@Data
public class NoticeMailSendLogQuery extends Page<NoticeMailSendLog> {

    /**
     * 日志id
     */
    private String logId;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 服务器id
     */
    private String srvId;
    /**
     * 发送状态
     */
    private String logState;
    /**
     * 发送时间
     */
    private Date createTime;
    /**
     * 模板id
     */
    private String tempId;
    /**
     * 异常信息
     */
    private String errorStr;

    private String srvName;

    private String tempName;

    private String appId;
}
