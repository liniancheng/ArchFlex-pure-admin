package com.littlelee.base.detect.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.detect.model.bo.DetectImage;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.bo.PredictResult;
import com.littlelee.base.detect.model.query.ImageRecordQuery;

import java.util.Map;

/**
 * 图像识别记录服务接口
 */
public interface ImageService extends BaseService<DetectImage> {

    /**
     * 图像检测
     * @param detectImage 图像识别记录
     * @return 更新结果
     */
    Map<String, Object> detectImage(DetectImage detectImage);

    /**
     * 使用Flask的方式进行图像检测
     * @param request 预测请求参数
     * @return 预测结果
     */
    PredictResult predict(PredictRequest request);

}