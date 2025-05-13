package com.adtec.rdc.base.anno.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公告类型信息表
 * </p>
 *
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
@Data
@Accessors(chain = true)
public class SysAnnoTypeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "type_id", type = IdType.UUID)
    private String typeId;
    /**
	 * 租户id
	 */
	@TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
	private String appId;

    /**
     * 类型名称
     */
    @TableField(value = "type_name", strategy = FieldStrategy.IGNORED) 
    private String typeName;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED) 
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED) 
    private LocalDateTime modifyTime; 
    /**
     * 类型描述
     */
    @TableField(value = "type_rmk", strategy = FieldStrategy.IGNORED) 
    private String typeRmk;

}