package com.littlelee.base.user.mapper;

import com.littlelee.base.user.mapper.SysRoleInfoMapper;
import com.littlelee.base.user.model.query.SysRoleInfoQuery;
import com.littlelee.base.user.BaseUserServiceApplicationTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: littlelee
 * @date: 2018/11/1 17:32
 */
@Slf4j
public class SysRoleInfoMapperTest extends BaseUserServiceApplicationTests {

    @Autowired
    private SysRoleInfoMapper sysRoleMapper;


    @Test
    public void pageByQuery() {
        SysRoleInfoQuery query = new SysRoleInfoQuery();
        query.setRoleName("用户");
        sysRoleMapper.pageByQuery(query);
        log.info(query.toString());
        Assert.assertNotEquals(0, query.getSize());
    }
}