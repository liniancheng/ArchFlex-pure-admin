package com.littlelee.base.detect.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.detect.model.po.VideoRecords;

public interface VideoRecordsService extends BaseService<VideoRecords> {
    boolean deleteVideoRecords(Long[] ids);
}
