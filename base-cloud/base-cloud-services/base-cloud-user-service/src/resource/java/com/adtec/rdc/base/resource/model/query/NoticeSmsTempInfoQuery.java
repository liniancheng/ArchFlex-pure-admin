package com.adtec.rdc.base.resource.model.query;

import com.adtec.rdc.base.resource.model.po.NoticeSmsTempInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
@Data
public class NoticeSmsTempInfoQuery extends Page<NoticeSmsTempInfo> {

    /**
     * 主键
     */
    private String tempId;
    /**
     * 扩展属性1
     */
    private String extAttr1;
    /**
     * 扩展属性2
     */
    private String extAttr2;
    /**
     * 扩展属性3
     */
    private String extAttr3;
    /**
     * 短信模板内容
     */
    private String tempHtml;
    /**
     * 短信模板名称
     */
    private String tempName;
    /**
     * 短信模板描述
     */
    private String tempRmk;
    /**
     * 创建用户
     */
    private String tempUser;
    /**
     * 租户ID
     */
    private String appId;

}
