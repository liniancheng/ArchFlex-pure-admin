package com.littlelee.base.user.service;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import com.littlelee.base.user.model.po.SysOperlogInfo;
import com.littlelee.base.user.model.query.SysOperlogInfoQuery;
import com.littlelee.base.user.model.query.SysOperlogTextQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author: littlelee
 * @since 2018-10-08
 */
public interface SysOperlogInfoService extends IService<SysOperlogInfo> {
    /**
     * 分页条件查询
     * @param query
     * @return
     */
    SysOperlogInfoQuery pageByQuery(SysOperlogInfoQuery query);
    SysOperlogTextQuery pageTextByQuery(SysOperlogTextQuery query);
    void download(List<String> ids, HttpServletResponse response);
    /**
     * 不使用mq时通过feign调用此方法
     * @param info
     * @return
     */
    Boolean saveOperLog(SysOperlogInfo info);
}
