package com.littlelee.base.detect.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.detect.model.po.ImgRecords;

public interface ImgRecordsService extends BaseService<ImgRecords> {
    boolean deleteImgRecords(Long[] ids);
}
