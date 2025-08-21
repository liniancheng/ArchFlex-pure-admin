package com.littlelee.base.detect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.detect.mapper.VideoRecordsMapper;
import com.littlelee.base.detect.model.po.VideoRecords;
import com.littlelee.base.detect.service.VideoRecordsService;
import org.springframework.stereotype.Service;

/**
 * 视频识别记录服务实现类
 */
@Service
public class VideoRecordsServiceImpl extends BaseServiceImpl<VideoRecordsMapper, VideoRecords> implements VideoRecordsService {
    @Override
    public boolean deleteVideoRecords(Long[] ids) {
        LambdaUpdateWrapper<VideoRecords> uw = new LambdaUpdateWrapper<VideoRecords>();
        uw.in(VideoRecords::getId, ids);
        uw.set(VideoRecords::getDelFlag, 1);
        return update(uw);
    }
}
