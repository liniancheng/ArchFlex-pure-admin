package com.littlelee.base.detect.model.bo;


import lombok.Data;

import java.util.List;

@Data
public class DetectImageBatch {
    private List<String> imageUrls; // 图片 URL 列表

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
