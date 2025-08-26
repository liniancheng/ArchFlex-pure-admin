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
     * 总耗时
     */
    String allTime;
    /**
     * 每种类别的数量
     */
    HashMap<String, Integer> labelCounts;
    /**
     * Flask返回的状态码，200为成功，400为失败
     */
    String status;
    /**
     * Flask返回的信息
     */
    String message;
}
