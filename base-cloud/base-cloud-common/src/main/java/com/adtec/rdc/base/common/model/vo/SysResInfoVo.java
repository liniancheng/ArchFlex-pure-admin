package com.adtec.rdc.base.common.model.vo;


import lombok.Data;
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

public class SysResInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

                            /**
     * 数据源类型(1:mysql   2:db2   3:orcle)
     */
    private String dbType;
    /**
     * 数据源类型(1:mysql   2:db2   3:orcle)
     */
    private String dbNm;
                        /**
     * 连接驱动
     */
    private String lkDriver;
                        /**
     * 连接URL
     */
    private String lkUrl;
                        /**
     * 连接用户
     */
    private String lkUser;
                        /**
     * 连接密码
     */
    private String lkPassword;
                        /**
     * 租户id
     */
    private String appId;
                        /**
     * 创建时间
     */
    private Date createdTime;
                        /**
     * 更新时间
     */
    private Date modifyTime;

}
