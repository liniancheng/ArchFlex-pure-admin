package com.adtec.rdc.base.detect.service.impl;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.detect.mapper.DetectIndexMapper;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndTree;
import com.adtec.rdc.base.detect.model.po.CvChkInd;
import com.adtec.rdc.base.detect.model.query.CvChkIndQuery;
import com.adtec.rdc.base.detect.service.DetectIndexService;
import com.adtec.rdc.base.detect.util.CvChkIndTreeUtils;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;


import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
@Service
public class DetectIndexServiceImpl extends BaseServiceImpl<DetectIndexMapper, CvChkInd> implements DetectIndexService {
    @Autowired
    private DetectIndexMapper mapper;

    @Override
    public List<CvChkIndTree> getCvChkIndTree() {
        QueryWrapper<CvChkInd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(CvChkInd::getMenuOrder);
        List<CvChkInd> cvChkIndList = mapper.selectList(queryWrapper);
        List<CvChkIndTree> cvChkIndTreeList = CvChkIndTreeUtils.list2Tree(cvChkIndList, CommonConstants.TREE_ROOT);
        return cvChkIndTreeList;
    }

    @Override
    public List<TreeNode> getAllCvChkIndTree() {
        QueryWrapper<CvChkInd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(CvChkInd::getMenuOrder);
        List<CvChkInd> cvChkIndList = mapper.selectList(queryWrapper);
        List<TreeNode> treeNodes = CvChkIndTreeUtils.list2TreeNode(cvChkIndList, CommonConstants.TREE_ROOT);
        return treeNodes;
    }

    @Override
    public CvChkIndQuery fetchByIndNo(String indNo) {
        CvChkIndQuery query = new CvChkIndQuery();
        List<CvChkInd> result = new ArrayList<>();
        QueryWrapper<CvChkInd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CvChkInd::getIndNo, indNo);
        CvChkInd cvChkInd = mapper.selectOne(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CvChkInd::getParentNo, indNo);
        List<CvChkInd> cvChkIndList = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(cvChkIndList)) {
            result.add(cvChkInd);
            query.setRecords(result);
            return query;
        }
        result.add(cvChkInd);
        result.addAll(cvChkIndList);
        while (true) {
            List<String> indNoList = cvChkIndList.stream().map(CvChkInd::getIndNo).collect(Collectors.toList());
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(CvChkInd::getParentNo, indNoList);
            cvChkIndList = mapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(cvChkIndList)) {
                break;
            }
            result.addAll(cvChkIndList);
        }
        query.setRecords(result);
        return query;
    }

    @Override
    public Boolean removeByIndNo(String indNo) {
        CvChkIndQuery query = this.fetchByIndNo(indNo);
        List<CvChkInd> cvChkIndList = query.getRecords();
        List<String> indNos = cvChkIndList.stream().map(CvChkInd::getIndNo).collect(Collectors.toList());
        return this.removeByIds(indNos);
    }

    @Override
    public Boolean updates(CvChkInd check) {
        if (!check.getUpdateIndLevel()) {
            return super.updateById(check);
        }
        Integer level = null;
        String indNo = check.getIndNo();
        CvChkInd cvChkInd = mapper.selectById(indNo);
        //未更新前指标层级
        String indLevel = cvChkInd.getIndLevel();
        Integer oldLevel = Integer.parseInt(indLevel);
        //获取当前指标的子集(包含自己)
        CvChkIndQuery query = this.fetchByIndNo(indNo);
        List<CvChkInd> children = query.getRecords();
        children.remove(cvChkInd);
        //获取修改后的父级指标编号
        String parentNo = check.getParentNo();
        if (!StringUtils.equals(parentNo, "-1")) {
            CvChkInd parentInd = mapper.selectById(parentNo);
            String parentLevel = parentInd.getIndLevel();
            Integer newLevel = Integer.parseInt(parentLevel) + 1;
            check.setIndLevel(newLevel.toString());
            level = newLevel - oldLevel;
        } else {
            check.setIndLevel("1");
            level = 1 - oldLevel;
        }
        if (CollectionUtils.isNotEmpty(children)) {
            for (CvChkInd child : children) {
                String childIndLevel = child.getIndLevel();
                Integer childLevel = Integer.parseInt(childIndLevel);
                childLevel = childLevel + level;
                child.setIndLevel(childLevel.toString());
            }
        }
        children.add(check);
        return this.updateBatchById(children);
    }

    @Override
    public String importInd(InputStream inputStream) {
        return null;
    }

    @Override
    public void exportInd(String absolutePath, String tempOutputFileName) {

    }

    @Override
    public CvChkInd getCvChkInd(String indNo) {
        return mapper.selectById(indNo);
    }

    @Override
    public List<TreeNode> treeSearch(String searchValue) {
        List<TreeNode> treeNodes = new ArrayList<>();
        QueryWrapper<CvChkInd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(CvChkInd::getIndNm, searchValue);
        List<CvChkInd> cvChkIndList = mapper.selectList(queryWrapper);
        // 只有一个结果直接转树节点返回,多结果进行转树形
        if (CollectionUtils.isNotEmpty(cvChkIndList)) {
            if (cvChkIndList.size() == 1){
                TreeNode treeNode = CvChkIndTreeUtils.copyIndInfoToTreeNode(cvChkIndList.get(0));
                treeNodes.add(treeNode);
                return treeNodes;
            }
            getFinalList(cvChkIndList);
            treeNodes = CvChkIndTreeUtils.list2TreeNode(cvChkIndList, CommonConstants.TREE_ROOT);
            return treeNodes;
        }else {
            throw new ServiceException("暂无指标数据,请添加后搜索!");
        }
    }

    private void getFinalList(List<CvChkInd> cvChkIndList) {
        //根据指标获取其对应的父级指标编号
        List<String> parentList = cvChkIndList.stream().
                map(CvChkInd::getParentNo).distinct().collect(Collectors.toList());
        //一层一层找到第一级返回最终结果集
        while (true) {
            QueryWrapper<CvChkInd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(CvChkInd::getIndNo, parentList);
            List<CvChkInd> cvChkInds = mapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(cvChkInds)) {
                // 将上层数据放入结果集
                for (CvChkInd cvChkInd : cvChkInds) {
                    if (!cvChkIndList.contains(cvChkInd)) {
                        cvChkIndList.add(cvChkInd);
                    } else {
                        continue;
                    }
                }
            }
            if (parentList.size() == 1 && parentList.get(0).equals("-1")) {
                break;
            } else {
                if (CollectionUtils.isNotEmpty(cvChkInds)) {
                    parentList = cvChkInds.stream().map(CvChkInd::getParentNo).distinct().collect(Collectors.toList());
                }
            }
        }
    }
}
