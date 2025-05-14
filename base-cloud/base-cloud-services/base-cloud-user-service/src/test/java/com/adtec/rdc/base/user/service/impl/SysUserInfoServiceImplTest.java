package com.adtec.rdc.base.user.service.impl;

import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.user.BaseUserServiceApplicationTests;
import com.adtec.rdc.base.user.service.SysUserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SysUserInfoServiceImplTest extends BaseUserServiceApplicationTests {

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Test
    public void save() {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setUserId("user_admin");
        sysUserVo.setLoginName("admin");
        sysUserVo.setUserName("系统管理员");
        sysUserVo.setLoginPwd("admin");
        sysUserVo.setUserEmail("123456@163.com");
        sysUserVo.setUserMobTel("13812345678");
        sysUserVo.setDelFlag("0");
        sysUserVo.setBranchNo("1000");
        sysUserVo.setAppId("base");
        sysUserInfoService.save(sysUserVo);
    }
}
