package com.littlelee.base.detect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.detect.mapper.CameraRecordsMapper;
import com.littlelee.base.detect.model.po.CameraRecords;
import com.littlelee.base.detect.service.CameraRecordsService;
import org.springframework.stereotype.Service;

/**
 * 摄像识别记录服务实现类
 */
@Service
public class CameraRecordsServiceImpl extends BaseServiceImpl<CameraRecordsMapper, CameraRecords> implements CameraRecordsService {
    @Override
    public boolean deleteCameraRecords(Long[] ids) {
        LambdaUpdateWrapper<CameraRecords> uw = new LambdaUpdateWrapper<>();
        uw.in(CameraRecords::getId, ids);
        uw.set(CameraRecords::getDelFlag, 1);
        return update(uw);
    }
}
