package com.adtec.rdc.base.user.mapper;

import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.user.BaseUserServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: JTao
 * @date: 2018/10/16 17:48
 * @description:
 */
public class SysUserInfoMapperTest extends BaseUserServiceApplicationTests {

    @Autowired
    private SysUserInfoMapper sysUserMapper;

    @Test
    public void loadUserByUsername() {
        System.out.println(sysUserMapper.loadUserByUsername("admin"));
    }
}