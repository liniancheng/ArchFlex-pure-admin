package com.littlelee.base.detect.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.detect.model.po.CameraRecords;

public interface CameraRecordsService extends BaseService<CameraRecords> {
    boolean deleteCameraRecords(Long[] ids);
}
