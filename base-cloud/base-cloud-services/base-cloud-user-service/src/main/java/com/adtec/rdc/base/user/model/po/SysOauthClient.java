package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * oauth2客户端资源认证表
 * </p>
 *
 * @author: JTao
 * @since 2018-12-13
 */
@Data
@Accessors(chain = true)
public class SysOauthClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * (必须的)用来标识客户的Id
     */
    private String clientId;

    private String resourcesIds;

    /**
     * (需要值得信任的客户端)客户端安全码，如果有的话
     */
    @TableField(value="client_secret")
    private String secret;

    /**
     * 用来限制客户端的访问范围，如果为空(默认)的话，那么客户端拥有全部的访问范围
     */
    @TableField(value="client_scope")
    private String scope;

    /**
     * 此客户端可以使用的授权类型，默认为空
     */
    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    /**
     * 此客户端可以使用的权限(基于Spring Security authorities)
     */
    @TableField(value="client_authorities")
    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionInformation;

    @TableField(value="client_autoapprove")
    private String autoapprove;

}
