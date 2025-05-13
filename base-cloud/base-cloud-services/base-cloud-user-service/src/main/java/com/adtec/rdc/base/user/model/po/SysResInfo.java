package com.adtec.rdc.base.user.model.po;

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
 * 数据源配置信息
 * </p>
 *
 * @author adtec
 * @date 2022-03-29 14:14:04
 */
@Data
@Accessors(chain = true)
public class SysResInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

                            /**
     * 数据源类型(1:mysql   2:db2   3:orcle)
     */
    @TableField(value = "DB_TYPE", strategy = FieldStrategy.IGNORED)
    private String dbType;
    /**
     * 数据源类型(1:mysql   2:db2   3:orcle)
     */
    @TableField(exist = false)
    private String dbNm;
                        /**
     * 连接驱动
     */
    @TableField(value = "LK_DRIVER", strategy = FieldStrategy.IGNORED)
    private String lkDriver;
                        /**
     * 连接URL
     */
    @TableField(value = "LK_URL", strategy = FieldStrategy.IGNORED)
    private String lkUrl;
                        /**
     * 连接用户
     */
    @TableField(value = "LK_USER", strategy = FieldStrategy.IGNORED)
    private String lkUser;
                        /**
     * 连接密码
     */
    @TableField(value = "LK_PASSWORD", strategy = FieldStrategy.IGNORED)
    private String lkPassword;
                        /**
     * 租户id
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED)
    private String appId;
                        /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME", strategy = FieldStrategy.IGNORED)
    private Date createdTime;
                        /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED)
    private Date modifyTime;

}
