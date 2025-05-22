package com.adtec.rdc.base.detect.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndRuleChkVo;
import com.adtec.rdc.base.detect.model.bo.ImageRecordVo;
import com.adtec.rdc.base.detect.model.po.ImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface ImageRecordMapper extends BaseMapper<ImageRecord> {

    IPage<ImageRecordVo> pageByQuery(ImageRecordQuery query);

}
