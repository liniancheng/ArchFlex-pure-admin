package com.adtec.rdc.base.detect.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.detect.model.bo.DetectImage;
import com.adtec.rdc.base.detect.model.bo.DetectVideo;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;

import java.util.Map;

/**
 * 图像识别记录服务接口
 */
public interface VideoService extends BaseService<DetectVideo> {
    /**
     * 根据查询条件分页查询图像识别记录
     * @param query 查询条件
     * @return 分页结果
     */
    ImageRecordQuery pageByQuery(ImageRecordQuery query);

    /**
     * 保存图像识别记录
     * @param video 图像检测
     * @return 保存结果
     */
    Boolean saveImage(DetectVideo video);

    /**
     * 根据ID删除图像识别记录
     * @param id 图像识别记录ID
     * @return 删除结果
     */
    Boolean deleteImageRecord(String id);

    /**
     * 根据ID获取图像识别记录
     * @param id 图像识别记录ID
     * @return 图像识别记录
     */
    DetectVideo getImageById(String id);

    /**
     * 更新图像识别记录
     * @param detectVideo 图像识别记录
     * @return 更新结果
     */
    Boolean updateImageRecord(DetectVideo video);

    /**
     * 图像检测
     * @param detectImage 图像识别记录
     * @return 更新结果
     */
    Map<String, Object> detectVideo(DetectVideo detectVideo);
}