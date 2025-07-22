package com.adtec.rdc.base.detect.model.query;

import com.adtec.rdc.base.detect.model.po.DetectImageRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 图像识别记录查询条件类
 */
@Data
public class ImageRecordQuery extends Page<DetectImageRecord> {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 原始图片路径
     */
    private String originalImage;

    /**
     * 预测图片路径
     */
    private String predictedImage;

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

    /**
     * AI建议
     */
    private String aiSuggestion;

    /**
     * 识别时间
     */
    private String recognitionTime;

    /**
     * 识别用户
     */
    private String recognitionUser;

    /**
     * 操作
     */
    private String operation;
}