package com.adtec.rdc.base.detect.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 图像识别记录实体类
 */
@Data
@Accessors(chain = true)
public class DetectImageRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 原始图片路径
     */
    @TableField(value = "original_image", strategy = FieldStrategy.IGNORED)
    private String originalImage;

    /**
     * 预测图片路径
     */
    @TableField(value = "predicted_image", strategy = FieldStrategy.IGNORED)
    private String predictedImage;

    /**
     * 识别权重
     */
    @TableField(value = "recognition_weight", strategy = FieldStrategy.IGNORED)
    private String recognitionWeight;

    /**
     * 最小阈值
     */
    @TableField(value = "min_threshold", strategy = FieldStrategy.IGNORED)
    private Double minThreshold;

    /**
     * AI助手使用情况
     */
    @TableField(value = "ai_assistant", strategy = FieldStrategy.IGNORED)
    private String aiAssistant;

    /**
     * AI建议
     */
    @TableField(value = "ai_suggestion", strategy = FieldStrategy.IGNORED)
    private String aiSuggestion;

    /**
     * 识别时间
     */
    @TableField(value = "recognition_time", strategy = FieldStrategy.IGNORED)
    private Date recognitionTime;

    /**
     * 识别用户
     */
    @TableField(value = "recognition_user", strategy = FieldStrategy.IGNORED)
    private String recognitionUser;

    /**
     * 操作
     */
    @TableField(value = "operation", strategy = FieldStrategy.IGNORED)
    private String operation;

    /**
     * 额外字段，不在数据库中存储
     */
    @TableField(exist = false)
    private String extraField1;

    /**
     * 额外字段，不在数据库中存储
     */
    @TableField(exist = false)
    private String extraField2;

    /**
     * 额外字段，不在数据库中存储
     */
    @TableField(exist = false)
    private List<String> extraListField;
}