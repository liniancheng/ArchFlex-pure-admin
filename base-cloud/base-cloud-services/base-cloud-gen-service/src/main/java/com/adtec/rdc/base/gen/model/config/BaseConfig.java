package com.adtec.rdc.base.gen.model.config;

import lombok.Data;

import java.util.List;

/**
 * @author: JTao
 * @date: 2018/11/8 15:52
 */
@Data
public class BaseConfig {

    private List<String> tableNames;
    
    /**
     * 包名
     */
    private String packageName;
    
    /**
     * 模块名称
     */
    private String moduleName;
    
    /**
     * 功能英文名
     */
    private String funcName;

    /**
     * 功能中文名
     */
    private String funcDesc;
    
    /**
     * po类的包名
     */
    private String poPackageName;

    /**
     * query类的包名
     */
    private String queryPackageName;

    /**
     * service类包名
     */
    private String servicePackageName;

    /**
     * serviceImpl类包名
     */
    private String serviceImplPackageName;

    /**
     * mapper类包名
     */
    private String mapperPackageName;

    /**
     * controller类包名
     */
    private String controllerPackageName;

    /**
     * 作者名称
     */
    private String authorName;

}
