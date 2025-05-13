package com.adtec.rdc.base.gen.service.impl;

import com.adtec.rdc.base.gen.mapper.ColumnInfoMapper;
import com.adtec.rdc.base.gen.mapper.TableInfoMapper;
import com.adtec.rdc.base.gen.model.config.BaseConfig;
import com.adtec.rdc.base.gen.model.po.ColumnInfo;
import com.adtec.rdc.base.gen.model.po.TableInfo;
import com.adtec.rdc.base.gen.service.SysGenService;
import com.adtec.rdc.base.gen.util.GenUtil;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author: JTao
 * @date: 2018/11/8 14:48
 */
@Service
public class SysGenServiceImpl implements SysGenService {

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Autowired
    private ColumnInfoMapper columnInfoMapper;

    @Override
    public byte[] genCodeByTableName(BaseConfig buildConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : buildConfig.getTableNames()) {
            //查询表信息
        	TableInfo table = tableInfoMapper.getOne(tableName);
            //查询列信息
            List<ColumnInfo> columns = columnInfoMapper.listByTableName(tableName);
            //生成代码
            GenUtil.generatorCode(buildConfig,table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();

    }
}
