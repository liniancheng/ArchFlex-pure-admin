package com.adtec.rdc.base.entrancevalidation.model.query;

import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @ClassName ChkVerifyInfoQuery
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-12 10:21:52
 **/
@Data
public class ChkVerifyInfoQuery extends Page<ChkVerifyInfo> {

    /**
     * 法人
     */
    private String lgprInstCd;

    /**
     * 系统名
     */
    private String sysNm;

    /**
     * 系统表名
     */
    private String sysTabNm;

    /**
     * 数据日期
     */
    private String dataDt;
}
