package com.littlelee.base.detect.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 图片识别记录实体类
 */
@TableName("detect_img_records")
@Data
@Accessors(chain = true)
public class ImgRecords {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 识别权重
     */
    @TableField(value = "weight", updateStrategy = FieldStrategy.IGNORED)
    private String weight;
    /**
     * 输入照片
     */
    @TableField(value = "input_img", updateStrategy = FieldStrategy.IGNORED)
    private String inputImg;
    /**
     * 输出照片
     */
    @TableField(value = "out_img", updateStrategy = FieldStrategy.IGNORED)
    private String outImg;
    /**
     * 置信度
     */
    @TableField(value = "confidence", updateStrategy = FieldStrategy.IGNORED)
    private String confidence;
    /**
     * 总用时
     */
    @TableField(value = "all_time", updateStrategy = FieldStrategy.IGNORED)
    private String allTime;
    /**
     * 最小置信度阈值
     */
    @TableField(value = "conf", updateStrategy = FieldStrategy.IGNORED)
    private String conf;
    /**
     * 标签
     */
    @TableField(value = "label", updateStrategy = FieldStrategy.IGNORED)
    private String label;
    /**
     * 用户名
     */
    @TableField(value = "username", updateStrategy = FieldStrategy.IGNORED)
    private String username;
    /**
     * 类别
     */
    @TableField(value = "kind", updateStrategy = FieldStrategy.IGNORED)
    private String kind;
    /**
     * 识别时间
     */
    @TableField(value = "start_time", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime startTime;
    /**
     * 删除标志
     */
    @TableField(value = "del_flag", updateStrategy = FieldStrategy.IGNORED)
    private Integer delFlag;
}
