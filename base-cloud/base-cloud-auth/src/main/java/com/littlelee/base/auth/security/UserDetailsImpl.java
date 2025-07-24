package com.littlelee.base.auth.security;

import com.littlelee.base.common.enums.UserStatusEnum;
import com.littlelee.base.common.model.vo.SysRoleVo;
import com.littlelee.base.common.model.vo.SysUserVo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author: littlelee
 * @date: 2018/10/9 10:09
 * @description: security 用户对象
 */
@Data
public class UserDetailsImpl implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = -2636609458742965698L;

    private String userId;
    private String username;
    private String password;
    private String status;
    private List<SysRoleVo> roleVos;


    /**
     * 扩展字段：部门ID
     */
    private Long deptId;
    /**
     * 用户角色数据权限集合
     */
    private Integer dataScope;
    private Boolean enabled;
    private Collection<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    public UserDetailsImpl(SysUserVo userVo) {
        this.userId = userVo.getUserId();
        this.username = userVo.getLoginName();
        this.password = userVo.getLoginPwd();
        this.status = userVo.getDelFlag();
        this.roleVos = userVo.getSysRoleVoList();
    }

    public UserDetailsImpl(
            String userId,
            String username,
            String password,
            Integer dataScope,
            Long deptId,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            List<? extends GrantedAuthority> authorities
    ) {
        Assert.isTrue(username != null && !"".equals(username) && password != null,
                "Cannot pass null or empty values to constructor");
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.dataScope = dataScope;
        this.deptId = deptId;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableList(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        roleVos.forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()+"="+role.getRoleId()));
        });
        //添加公共角色(此角色没有任何权限, 前端判断用户是否加载完成时使用)
        authorityList.add(new SimpleGrantedAuthority("SYSTEM_DEFAULT_ROLE_TO_USER=SYSTEM_DEFAULT_ROLE_TO_USER"));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !StringUtils.equals(UserStatusEnum.LOCK.getCode(), status);
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(UserStatusEnum.NORMAL.getCode(), status);
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
