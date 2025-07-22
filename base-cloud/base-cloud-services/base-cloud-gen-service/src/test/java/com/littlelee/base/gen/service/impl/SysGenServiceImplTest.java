package com.littlelee.base.gen.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.littlelee.base.gen.service.SysGenService;

/**
 * @author: littlelee
 * @date: 2018/11/8 14:58
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SysGenServiceImplTest  {

    @Autowired
    private SysGenService sysGenService;

    @Test
    public void genCodeByTableName() {

    }
}