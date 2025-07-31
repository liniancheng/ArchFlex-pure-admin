package com.littlelee.base.detect.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetectCamera implements Serializable {

    /**
     * 输入
     */
    private String input;
    /**
     * 模型
     */
    private String model;
    /**
     * 识别权重
     */
    private String recognitionWeight;
    /**
     * 最小阈值
     */
    private Double minThreshold;

}
