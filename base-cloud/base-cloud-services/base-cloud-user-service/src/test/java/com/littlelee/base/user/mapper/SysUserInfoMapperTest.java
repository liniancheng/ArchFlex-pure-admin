package com.littlelee.base.user.mapper;

import com.littlelee.base.user.mapper.SysUserInfoMapper;
import com.littlelee.base.user.BaseUserServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: littlelee
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