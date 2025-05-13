package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;

import com.adtec.rdc.base.crossvalidation.model.po.CvDictionary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 20:05:47
 */
@Data
public class CvDictionaryQuery extends Page<CvDictionary> {

        /**
     * 字典类型
     */
    private String dictType;
        /**
     * 字典key
     */
    private String dictKey;
        /**
     * 字典值
     */
    private String dictValue;
        /**
     * key描述
     */
    private String keyDesc;
        /**
     * 值描述
     */
    private String valueEx;
        /**
     * 系统key
     */
    private String systemKey;
        /**
     * 
     */
    private String v1;
        /**
     * 
     */
    private String v2;
        /**
     * 
     */
    private String v3;
        /**
     * 
     */
    private String ssId;
    
}
