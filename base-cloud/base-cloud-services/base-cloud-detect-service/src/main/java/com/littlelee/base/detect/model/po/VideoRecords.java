package com.littlelee.base.detect.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视频识别记录实体类
 */
@TableName("detect_video_records")
@Data
@Accessors(chain = true)
public class VideoRecords implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 识别权重
     */
    @TableField(value = "weight", updateStrategy = FieldStrategy.IGNORED)
    private String weight;
    /**
     * 输入视频
     */
    @TableField(value = "input_video", updateStrategy = FieldStrategy.IGNORED)
    private String inputVideo;
    /**
     * 输出视频
     */
    @TableField(value = "out_video", updateStrategy = FieldStrategy.IGNORED)
    private String outVideo;
    /**
     * 最小置信度阈值
     */
    @TableField(value = "conf", updateStrategy = FieldStrategy.IGNORED)
    private String conf;
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
