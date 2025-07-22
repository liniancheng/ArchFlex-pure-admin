package com.adtec.rdc.base.crossvalidation.model.bo;

import lombok.Data;

@Data
public class ResultBo {
    /**
     * 币种
     */
    private String BZ;
    /**
     * 外币
     */
    private String WB;
    /**
     * 中间价
     */
    private String ZJJ;
    /**
     * 余额
     */
    private String YE;
}
