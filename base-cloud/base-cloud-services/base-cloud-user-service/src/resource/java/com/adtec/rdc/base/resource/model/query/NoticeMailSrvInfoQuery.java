package com.adtec.rdc.base.resource.model.query;

import java.util.Date;

import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
@Data
public class NoticeMailSrvInfoQuery extends Page<NoticeMailSrvInfo> {

    /**
     * 服务器标识
     */
    private String srvId;
    /**
     * 服务器名称
     */
    private String srvName;
    /**
     * 服务器描述
     */
    private String srvRmk;
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String loginPwd;
    /**
     * 邮件服务器地址
     */
    private String srvUrl;
    /**
     * 邮箱地址
     */
    private String defaultFrom;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifyTime;
    /**
     * 租户ID
     */
    private String appId;

}
