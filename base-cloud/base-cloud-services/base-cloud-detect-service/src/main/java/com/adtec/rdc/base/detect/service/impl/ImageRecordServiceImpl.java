package com.adtec.rdc.base.detect.service.impl;


import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;

import com.adtec.rdc.base.detect.mapper.ImageRecordMapper;
import com.adtec.rdc.base.detect.model.po.ImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.adtec.rdc.base.detect.service.ImageRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImageRecordServiceImpl extends BaseServiceImpl<ImageRecordMapper, ImageRecord> implements ImageRecordService {

    @Autowired
    private ImageRecordMapper mapper;
    @Override
    public ImageRecordQuery pageByQuery(ImageRecordQuery query) {
        return query;
    }

}
