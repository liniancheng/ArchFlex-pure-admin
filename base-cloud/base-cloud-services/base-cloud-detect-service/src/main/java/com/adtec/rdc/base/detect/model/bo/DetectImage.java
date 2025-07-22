package com.adtec.rdc.base.detect.model.bo;

import lombok.Data;


import java.io.Serializable;

@Data
public class DetectImage implements Serializable {

    /**
     * 原始图片路径
     */
    private String originalImage;
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

    /**
     * AI助手使用情况
     */
    private String aiAssistant;
}
