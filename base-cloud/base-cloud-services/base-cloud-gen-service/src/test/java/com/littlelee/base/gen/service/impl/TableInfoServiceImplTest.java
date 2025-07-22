package com.littlelee.base.gen.service.impl;

import com.littlelee.base.gen.model.po.TableInfo;
import com.littlelee.base.gen.model.query.TableInfoQuery;
import com.littlelee.base.gen.service.TableInfoService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: littlelee
 * @date: 2018/11/8 10:07
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class TableInfoServiceImplTest {

    @Autowired
    private TableInfoService tableInfoService;

    @Test
    public void pageByQuery() {
        TableInfoQuery query = new TableInfoQuery();
        tableInfoService.pageByQuery(query);
        log.info(query.toString());
    }

    @Test
    public  void getOne() {
        TableInfo  tableInfo = tableInfoService.getById("sys_user");
        log.info(tableInfo.toString());
    }
}