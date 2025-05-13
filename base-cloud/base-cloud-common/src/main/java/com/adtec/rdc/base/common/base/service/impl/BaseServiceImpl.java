package com.adtec.rdc.base.common.base.service.impl;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.common.base.service.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: JTao
 * @date 2019-01-23 09:48
 */
public class BaseServiceImpl<K extends BaseMapper<T>, T> extends ServiceImpl<K , T> implements BaseService<T> {

    @Autowired
    protected K baseMapper;

    /**
     * 分页条件查询
     * @param query
     * @return
     */
    @Override
    public IPage<T> pageByQuery(IPage<T> query) {
        return baseMapper.pageByQuery(query);
    }

}
