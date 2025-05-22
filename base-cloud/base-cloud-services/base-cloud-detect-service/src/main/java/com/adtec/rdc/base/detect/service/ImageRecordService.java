package com.adtec.rdc.base.detect.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.detect.model.po.ImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;

public interface ImageRecordService extends BaseService<ImageRecord> {

    ImageRecordQuery pageByQuery(ImageRecordQuery query);

}
