package com.adtec.rdc.base.detect.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.detect.mapper.ImageRecordMapper;
import com.adtec.rdc.base.detect.model.po.DetectImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.adtec.rdc.base.detect.service.ImageRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图像识别记录服务实现类
 */
@Service
public class ImageRecordServiceImpl extends BaseServiceImpl<ImageRecordMapper, DetectImageRecord> implements ImageRecordService {

    @Autowired
    private ImageRecordMapper mapper;

    @Override
    public ImageRecordQuery pageByQuery(ImageRecordQuery query) {
        // 设置排序
        query.setAsc("id");
        // 执行分页查询
        mapper.pageByQuery(query);
        List<DetectImageRecord> records = query.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            // 这里可以添加额外的业务逻辑，例如设置额外的字段信息
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveImageRecord(DetectImageRecord imageRecord) {
        // 保存图像识别记录
        return this.save(imageRecord);
    }

    @Override
    public Boolean deleteImageRecord(String id) {
        // 根据ID删除图像识别记录
        return this.removeById(id);
    }

    @Override
    public DetectImageRecord getImageRecordById(String id) {
        // 根据ID获取图像识别记录
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateImageRecord(DetectImageRecord imageRecord) {
        // 更新图像识别记录
        return this.updateById(imageRecord);
    }
}