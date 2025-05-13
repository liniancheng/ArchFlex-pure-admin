package com.adtec.rdc.base.gen.service;

import com.adtec.rdc.base.gen.model.po.TableInfo;
import com.adtec.rdc.base.gen.model.query.TableInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: JTao
 * @date: 2018/11/8 10:04
 */
public interface TableInfoService extends IService<TableInfo> {

    /**
     * 分页查询表信息
     * @param query
     * @return
     */
    TableInfoQuery pageByQuery(TableInfoQuery query);

    /**
     * 查询单个表信息
     * @param tableName
     * @return
     */
    TableInfo getOne(String tableName);

}
