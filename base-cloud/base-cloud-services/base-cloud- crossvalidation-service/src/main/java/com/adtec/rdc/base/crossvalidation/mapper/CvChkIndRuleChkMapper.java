package com.adtec.rdc.base.crossvalidation.mapper;

import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndRuleChkVo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.crossvalidation.model.query.CvRuleQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Update;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
public interface CvChkIndRuleChkMapper extends BaseMapper<CvChkIndRuleChk> {

    IPage<CvChkIndRuleChkVo> rulePageByQuery(CvRuleQuery query);
}
