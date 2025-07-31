package com.littlelee.base.detect.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.detect.model.bo.DetectCamera;
import com.littlelee.base.detect.model.bo.DetectImage;

import java.util.Map;


public interface CameraService extends BaseService<DetectCamera> {

    /**
     * 摄像检测
     * @param detectCamera 图像识别记录
     * @return 更新结果
     */
    Map<String, Object> detectCamera(DetectCamera detectCamera);

}
