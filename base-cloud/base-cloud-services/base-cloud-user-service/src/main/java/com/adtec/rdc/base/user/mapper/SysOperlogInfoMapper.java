package com.adtec.rdc.base.user.mapper;

import com.adtec.rdc.base.user.model.po.SysOperlogInfo;
import com.adtec.rdc.base.user.model.query.SysOperlogInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 角色资源关联表 Mapper 接口
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
public interface SysOperlogInfoMapper extends BaseMapper<SysOperlogInfo> {

    /**
     * 日志信息分页查询
     * @param query
     */
    IPage<SysOperlogInfo> pageByQuery(SysOperlogInfoQuery query);

}
