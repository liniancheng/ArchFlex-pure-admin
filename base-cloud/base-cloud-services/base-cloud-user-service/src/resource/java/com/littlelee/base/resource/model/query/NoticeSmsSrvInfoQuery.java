package com.littlelee.base.resource.model.query;

import com.littlelee.base.resource.model.po.NoticeSmsSrvInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author littlelee
 * @date 2020-06-16 13:00:26
 */
@Data
public class NoticeSmsSrvInfoQuery extends Page<NoticeSmsSrvInfo> {

    /**
     * 主键
     */
    private String srvId;
    /**
     * 服务器名称
     */
    private String srvName;
    /**
     * 短信发送接口实现类
     */
    private String srvSmsProgram;
    /**
     * 服务器描述
     */
    private String srvRmk;
    /**
     * 服务器扩展属性
     */
    private String srvExtendField;
    
    /**
     * 租户ID
     */
    private String appId;

}
