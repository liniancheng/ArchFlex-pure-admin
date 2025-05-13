package com.adtec.rdc.base.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 邮件模板
 * </p>
 *
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
@Data
@Accessors(chain = true)
public class NoticeMailTempInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    @TableId(value = "TEMP_ID", type = IdType.UUID)
    private String tempId;

    /**
     * 模板名称
     */
    @TableField(value = "TEMP_NAME", strategy = FieldStrategy.IGNORED) 
    private String tempName;
    /**
     * 模板内容
     */
    @TableField(value = "TEMP_CONTENT", strategy = FieldStrategy.IGNORED) 
    private String tempContent;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT) 
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED, fill = FieldFill.UPDATE) 
    private LocalDateTime modifyTime;
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED) 
    private String appId;

}