package com.littlelee.base.detect.service;

import com.littlelee.base.detect.model.bo.CvChkIndTree;
import com.littlelee.base.detect.model.po.CvChkInd;
import com.littlelee.base.detect.model.query.CvChkIndQuery;
import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.model.bo.TreeNode;

import java.io.InputStream;
import java.util.List;

/**
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
public interface DetectIndexService extends BaseService<CvChkInd> {
    /**
     *获取指标树
     * @return
     */
    List<CvChkIndTree> getCvChkIndTree();

    /**
     * 获取指标树节点
     * @return
     */
    List<TreeNode> getAllCvChkIndTree();

    /**
     * 根据indNo获取指标定义信息
     * @param indNo
     * @return
     */
    CvChkIndQuery fetchByIndNo(String indNo);

    /**
     * 根据indNo删除指标定义信息
     * @param indNo
     * @return
     */
    Boolean removeByIndNo(String indNo);

    /**
     * 修改指标定义信息
     * @param check
     * @return
     */
    Boolean updates(CvChkInd check);

    /**
     * 导入指标定义信息
     * @param inputStream
     * @return
     */
    String importInd(InputStream inputStream);

    /**
     * 导出指标定义信息
     * @param absolutePath
     * @param tempOutputFileName
     */
    void exportInd(String absolutePath, String tempOutputFileName);

    /**
     * 根据indNo获取CvChkInd
     * @param indNo
     * @return
     */
    CvChkInd getCvChkInd(String indNo);

    List<TreeNode> treeSearch(String searchValue);
}
