package com.adtec.rdc.base.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 短信模板表 
 * </p>
 *
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
@Data
@Accessors(chain = true)
public class NoticeSmsTempInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "TEMP_ID", type = IdType.UUID)
    private String tempId;

    /**
     * 扩展属性1
     */
    @TableField(value = "EXT_ATTR1", strategy = FieldStrategy.IGNORED) 
    private String extAttr1;
    /**
     * 扩展属性2
     */
    @TableField(value = "EXT_ATTR2", strategy = FieldStrategy.IGNORED) 
    private String extAttr2;
    /**
     * 扩展属性3
     */
    @TableField(value = "EXT_ATTR3", strategy = FieldStrategy.IGNORED) 
    private String extAttr3;
    /**
     * 短信模板内容
     */
    @TableField(value = "TEMP_HTML", strategy = FieldStrategy.IGNORED) 
    private String tempHtml;
    /**
     * 短信模板名称
     */
    @TableField(value = "TEMP_NAME", strategy = FieldStrategy.IGNORED) 
    private String tempName;
    /**
     * 短信模板描述
     */
    @TableField(value = "TEMP_RMK", strategy = FieldStrategy.IGNORED) 
    private String tempRmk;
    /**
     * 创建用户
     */
    @TableField(value = "TEMP_USER", strategy = FieldStrategy.IGNORED) 
    private String tempUser;
    
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED) 
    private String appId;

}