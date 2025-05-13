package com.adtec.rdc.base.user.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.user.mapper.SysResInfoMapper;
import com.adtec.rdc.base.user.model.bo.JdbcSource;
import com.adtec.rdc.base.user.model.po.SysResInfo;
import com.adtec.rdc.base.user.model.query.SysResInfoQuery;
import com.adtec.rdc.base.user.service.SysResInfoService;

import com.adtec.rdc.base.user.util.JDBCUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * @author adtec
 * @date 2022-03-29 14:14:04
 */
@Service
public class SysResInfoServiceImpl extends BaseServiceImpl<SysResInfoMapper, SysResInfo> implements SysResInfoService {
    @Autowired
    private SysResInfoMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysResInfoQuery pageByQuery(SysResInfoQuery query) {
        mapper.pageByQuery(query);
        return query;
    }

    @Override
    public List<SysResInfo> fetchList() {
        List<SysResInfo> sysResInfoList = mapper.selectList(null);
        for (SysResInfo sysResInfo : sysResInfoList) {
            if (StringUtils.equals("1", sysResInfo.getDbType())) {
                sysResInfo.setDbNm("mysql");
            }
            if (StringUtils.equals("2", sysResInfo.getDbType())) {
                sysResInfo.setDbNm("db2");
            }
            if (StringUtils.equals("3", sysResInfo.getDbType())) {
                sysResInfo.setDbNm("orcle");
            }
        }
        return sysResInfoList;
    }

    @Override
    public Boolean test(SysResInfo resall) {
        JdbcSource jdbcSource = new JdbcSource();
        jdbcSource.setDriverClass(resall.getLkDriver());
        jdbcSource.setUrl(resall.getLkUrl());
        jdbcSource.setUsername(resall.getLkUser());
        jdbcSource.setPassword(resall.getLkPassword());
        Connection connection = JDBCUtils.getConnection(jdbcSource);
        if (connection == null){
            return false;
        }
        return true;
    }
}
