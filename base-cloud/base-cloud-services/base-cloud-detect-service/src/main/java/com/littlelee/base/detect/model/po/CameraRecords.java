package com.littlelee.base.detect.model.po;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 摄像识别记录实体类
 */
@TableName("detect_camera_records")
@Data
@Accessors(chain = true)
public class CameraRecords implements Serializable {
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
     * 预测视频路径
     */
    @TableField(value = "out_video", updateStrategy = FieldStrategy.IGNORED)
    private String outVideo;
    /**
     * 最小阈值
     */
    @TableField(value = "conf", updateStrategy = FieldStrategy.IGNORED)
    private String conf;
    /**
     * 识别用户
     */
    @TableField(value = "username", updateStrategy = FieldStrategy.IGNORED)
    private String username;
    /**
     * 检测类别
     */
    @TableField(value = "kind", updateStrategy = FieldStrategy.IGNORED)
    private String kind;
    /**
     * 开始时间
     */
    @TableField(value = "start_time", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime startTime;
    /**
     * 开始时间
     */
    @TableField(value = "del_flag", updateStrategy = FieldStrategy.IGNORED)
    private Integer delFlag;

}
