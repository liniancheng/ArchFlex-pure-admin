package com.littlelee.base.detect.model.bo;

import lombok.Data;

import java.util.HashMap;

@Data
public class PredictResult {

    /**
     * 预测照片
     */
    String outImg;
    /**
     * 开始检测时间
     */
    String startTime;
    /**
     * 检测结束时间
     */
    String endTime;
    /**
     * 总耗时
     */
    String allTime;
    /**
     * 类别
     */
    String label;
    /**
     * 每种类别的数量
     */
    HashMap<String, FlaskResponse.CountItem> labelCounts;
    /**
     * Flask返回的状态码，200为成功，400为失败
     */
    String status;
    /**
     * Flask返回的信息
     */
    String message;
}
