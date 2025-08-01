package com.littlelee.base.detect.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 视频识别记录实体类
 */
@Data
@Accessors(chain = true)
public class VideoRecords implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String weight;
    private String inputVideo;
    private String outVideo;
    private String conf;
    private String username;
    private String kind;
    private String startTime;

}
