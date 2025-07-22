package com.littlelee.base.gen.mapper;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.gen.model.po.ColumnInfo;

import java.util.List;

/**
 * @author: littlelee
 * @date: 2018/11/8 10:29
 */
public interface ColumnInfoMapper {

    /**
     * 查询单个表的列详细信息
     * @param tableName
     * @return
     */
    List<ColumnInfo> listByTableName(@Param("tableName") String tableName);

}
