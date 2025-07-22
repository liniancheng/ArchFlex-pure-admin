package com.littlelee.base.gen.service.impl;

import com.littlelee.base.gen.mapper.TableInfoMapper;
import com.littlelee.base.gen.model.po.TableInfo;
import com.littlelee.base.gen.model.query.TableInfoQuery;
import com.littlelee.base.gen.service.TableInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: littlelee
 * @date: 2018/11/8 10:05
 */
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements TableInfoService {

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Override
    public TableInfoQuery pageByQuery(TableInfoQuery query) {
        tableInfoMapper.pageByQuery(query);
        return query;
    }

    @Override
    public TableInfo getOne(String tableName) {
        return tableInfoMapper.getOne(tableName);
    }
}
