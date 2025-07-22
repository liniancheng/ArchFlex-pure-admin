package com.littlelee.base.detect.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CvChkPlanDetailBo implements Serializable {
    private String planId;
    private String dataTime;
    private String resVal;
}
