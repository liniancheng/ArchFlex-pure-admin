package com.adtec.rdc.base.entrancevalidation.model.query;

import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @ClassName ChkVerifyConfigQuery
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 11:17:11
 **/
@Data
public class ChkVerifyConfigQuery extends Page<ChkVerifyConfig> {

    /**
     * 系统名
     */
    private String sysNm;

    /**
     * 系统表名
     */
    private String sysTabNm;

    /**
     * 列名
     */
    private String colNm;

}
