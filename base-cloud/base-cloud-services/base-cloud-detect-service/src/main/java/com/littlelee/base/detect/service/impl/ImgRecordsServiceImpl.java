package com.littlelee.base.detect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.detect.mapper.ImgRecordsMapper;
import com.littlelee.base.detect.model.po.ImgRecords;
import com.littlelee.base.detect.service.ImgRecordsService;
import org.springframework.stereotype.Service;

@Service
public class ImgRecordsServiceImpl extends BaseServiceImpl<ImgRecordsMapper, ImgRecords> implements ImgRecordsService {
    @Override
    public boolean deleteImgRecords(Long[] ids) {
        LambdaUpdateWrapper<ImgRecords> uw = new LambdaUpdateWrapper<ImgRecords>();
        uw.in(ImgRecords::getId, ids);
        uw.set(ImgRecords::getDelFlag, 1);
        return update(uw);
    }
}
