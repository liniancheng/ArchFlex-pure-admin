package com.adtec.rdc.base.resource.model.query;

import java.util.Date;

import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
@Data
public class NoticeMailTempInfoQuery extends Page<NoticeMailTempInfo> {

    /**
     * 模板id
     */
    private String tempId;
    /**
     * 模板名称
     */
    private String tempName;
    /**
     * 模板内容
     */
    private String tempContent;
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
