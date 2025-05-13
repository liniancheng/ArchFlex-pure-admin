package com.adtec.rdc.base.gen.service;

import com.adtec.rdc.base.gen.model.config.BaseConfig;

/**
 * @author: JTao
 * @date: 2018/11/8 14:45
 */
public interface SysGenService {

    /**
     * 根据表名生成代码
     * @param buildConfig
     * @return
     */
    byte[] genCodeByTableName(BaseConfig buildConfig);

}
