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
 * 短信服务器信息表 
 * </p>
 *
 * @author adtec
 * @date 2020-06-16 13:00:26
 */
@Data
@Accessors(chain = true)
public class NoticeSmsSrvInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "SRV_ID", type = IdType.UUID)
    private String srvId;

    /**
     * 服务器名称
     */
    @TableField(value = "SRV_NAME", strategy = FieldStrategy.IGNORED) 
    private String srvName;
    /**
     * 短信发送接口实现类
     */
    @TableField(value = "SRV_SMS_PROGRAM", strategy = FieldStrategy.IGNORED) 
    private String srvSmsProgram;
    /**
     * 服务器描述
     */
    @TableField(value = "SRV_RMK", strategy = FieldStrategy.IGNORED) 
    private String srvRmk;
    /**
     * 服务器扩展属性
     */
    @TableField(value = "SRV_EXTEND_FIELD", strategy = FieldStrategy.IGNORED) 
    private String srvExtendField;
    
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED) 
    private String appId;

}