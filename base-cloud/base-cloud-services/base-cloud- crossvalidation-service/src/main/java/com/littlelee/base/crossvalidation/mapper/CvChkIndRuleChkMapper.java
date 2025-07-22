package com.littlelee.base.crossvalidation.mapper;

import com.littlelee.base.crossvalidation.model.bo.CvChkIndRuleChkVo;
import com.littlelee.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.crossvalidation.model.query.CvRuleQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Update;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
public interface CvChkIndRuleChkMapper extends BaseMapper<CvChkIndRuleChk> {

    IPage<CvChkIndRuleChkVo> rulePageByQuery(CvRuleQuery query);
}
