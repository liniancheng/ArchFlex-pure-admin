package com.adtec.rdc.base.crossvalidation.service.impl;

import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkPlanResultMapper;
import com.adtec.rdc.base.crossvalidation.model.excle.CvChkPlanResultExcleModel;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanResultQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanResultService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
@Service
public class CvChkPlanResultServiceImpl extends BaseServiceImpl<CvChkPlanResultMapper, CvChkPlanResult> implements CvChkPlanResultService {
    @Autowired
    private CvChkPlanResultMapper mapper;
    @Autowired
    private BaseUserService branchInfoService;


    @Override
    public CvChkPlanResultQuery showResult(CvChkPlanResultQuery query) {
        query.setSize(10000000);
        List<CvChkPlanResult> resultList = new ArrayList<>();
        mapper.showResult(query);
        List<CvChkPlanResult> cvChkPlanResultList = query.getRecords();
        for (CvChkPlanResult cvChkPlanResult : cvChkPlanResultList) {
            if (StringUtils.isNotBlank(cvChkPlanResult.getChkSql())) {
                cvChkPlanResult = setSysResult(cvChkPlanResult);
            } else {
                cvChkPlanResult = setSysDefaultResult(cvChkPlanResult);
            }
            resultList.add(cvChkPlanResult);
        }
        //添加频度
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList = addSysDateVal(resultList);
        }
        //数据合并
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList = listChange(resultList);
        }
        //添加法人
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList = addOrgNm(resultList);
        }
        query.setRecords(resultList);
        return query;
    }

    @Override
    public void exportResult(CvChkPlanResultQuery query,String absolutePath, String tempOutputFileName) {
        File templateFile = new File(absolutePath);
        if (!templateFile.exists()) {
            throw new ServiceException("模版文件不存在");
        }
        CvChkPlanResultQuery result = this.showResult(query);
        List<CvChkPlanResult> cvChkPlanResultList = result.getRecords();
        if(CollectionUtils.isNotEmpty(cvChkPlanResultList)){
            List<CvChkPlanResultExcleModel> modelList = this.getCvChkPlanResultExcleModelList(cvChkPlanResultList);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(tempOutputFileName);
                Workbook workbook = this.createWorkbook(modelList, absolutePath);
                workbook.write(fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            try {
                FileUtils.copyFile(templateFile, new File(tempOutputFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Workbook createWorkbook(List<CvChkPlanResultExcleModel> modelList, String absolutePath) throws IOException {
            int rowCount = 1;
            Workbook workbook = new XSSFWorkbook(new FileInputStream(absolutePath));
            Sheet sheetBase = workbook.getSheet("指标校验结果");
            int indexBase = workbook.getSheetIndex(sheetBase);
            Sheet sheetClone = workbook.cloneSheet(indexBase);
            int propSize = 0;
            for (CvChkPlanResultExcleModel mdExcleModel : modelList) {
                propSize++;
                Row rowProp = sheetClone.getRow(propSize + rowCount + 1);
                if (rowProp == null) {
                    rowProp = createRow(sheetClone, propSize + rowCount);
                }
                int colNo = 0;
                Cell cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(propSize);
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getIndNm());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getOrgNm());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getCurrencyVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getT1104PartVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getT1104Frq());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getEastPartVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getEastFrq());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getBigFocusPartVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getBigFocusFrq());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getCustRskPartVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getCustRskFrq());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getFinBaseDataPartVal());
                colNo++;
                cell = rowProp.getCell(colNo);
                if (cell == null) {
                    cell = createCell(rowProp, colNo);
                }
                cell.setCellValue(mdExcleModel.getFinBaseDataFrq());
            }
            workbook.removeSheetAt(indexBase);
            return workbook;
    }

    private static Row createRow(Sheet sheetClone, int index) {
        return sheetClone.createRow(index);
    }

    private static Cell createCell(Row row, int index) {
        return row.createCell(index);
    }

    private List<CvChkPlanResultExcleModel> getCvChkPlanResultExcleModelList(List<CvChkPlanResult> cvChkPlanResultList) {
        List<CvChkPlanResultExcleModel> modelList = new ArrayList<>();
        for (CvChkPlanResult cvChkPlanResult : cvChkPlanResultList) {
            CvChkPlanResultExcleModel model = new CvChkPlanResultExcleModel();
            BeanUtils.copyProperties(cvChkPlanResult,model);
            modelList.add(model);
        }
        return modelList;
    }

    private CvChkPlanResult setSysResult(CvChkPlanResult cvChkPlanResult) {
        String resultVal = cvChkPlanResult.getResultVal();
        resultVal = resultVal == null ? "0.00" : resultVal;
        //千位制
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        //金额
        resultVal = df.format(Double.valueOf(resultVal));
        //转换金额
        if (resultVal.equals("0")) {
            resultVal = "0.00";
        }
        String sysVal = cvChkPlanResult.getSysVal();
        if (StringUtils.equals(sysVal, "R1104")) {
            cvChkPlanResult.setT1104PartVal(resultVal);
        }
        if (StringUtils.equals(sysVal, "EAST3")) {
            cvChkPlanResult.setEastPartVal(resultVal);
        }
        if (StringUtils.equals(sysVal, "KHFX")) {
            cvChkPlanResult.setCustRskPartVal(resultVal);
        }
        if (StringUtils.equals(sysVal, "DWDK")) {
            cvChkPlanResult.setFinBaseDataPartVal(resultVal);
        }
        if (StringUtils.equals(sysVal, "JRTJ")) {
            cvChkPlanResult.setBigFocusPartVal(resultVal);
        }
        return cvChkPlanResult;
    }

    private CvChkPlanResult setSysDefaultResult(CvChkPlanResult cvChkPlanResult) {
        //如果为空就赋值不适用
        String unfined = "不适用";
        String sysVal = cvChkPlanResult.getSysVal();
        if (StringUtils.equals(sysVal, "R1104")) {
            cvChkPlanResult.setT1104PartVal(unfined);
        }
        if (StringUtils.equals(sysVal, "EAST3")) {
            cvChkPlanResult.setEastPartVal(unfined);
        }
        if (StringUtils.equals(sysVal, "KHFX")) {
            cvChkPlanResult.setCustRskPartVal(unfined);
        }
        if (StringUtils.equals(sysVal, "DWDK")) {
            cvChkPlanResult.setFinBaseDataPartVal(unfined);
        }
        if (StringUtils.equals(sysVal, "JRTJ")) {
            cvChkPlanResult.setBigFocusPartVal(unfined);
        }
        return cvChkPlanResult;
    }

    private List<CvChkPlanResult> listChange(List<CvChkPlanResult> cvChkPlanResultList) {
        List<CvChkPlanResult> resultList = new ArrayList<>();
        if (CollectionUtils.isEmpty(cvChkPlanResultList)) {
            return resultList;
        } else {
            String planId = cvChkPlanResultList.get(0).getPlanId();
            String orgVal = cvChkPlanResultList.get(0).getOrgVal();
            List<String> indNos = mapper.getIndNo(planId, orgVal);
            for (String indNo : indNos) {
                List<CvChkPlanResult> planResults =
                        cvChkPlanResultList.stream()
                                .filter(cvChkPlanResult -> cvChkPlanResult.getIndNo().equals(indNo))
                                .collect(Collectors.toList());
                CvChkPlanResult cvChkPlanResult = getFinalResultPlan(planResults);
                resultList.add(cvChkPlanResult);
            }
        }
        return resultList;
    }

    private CvChkPlanResult getFinalResultPlan(List<CvChkPlanResult> planResults) {
        CvChkPlanResult cvChkPlanResult = new CvChkPlanResult();
        if (CollectionUtils.isEmpty(planResults)){
            return null;
        }
        for (CvChkPlanResult planResult : planResults) {
            cvChkPlanResult.setPlanId(planResult.getPlanId());
            cvChkPlanResult.setIndNo(planResult.getIndNo());
            cvChkPlanResult.setIndNm(planResult.getIndNm());
            cvChkPlanResult.setOrgVal(planResult.getOrgVal());
            cvChkPlanResult.setCurrencyVal(planResult.getCurrencyVal());
            String sysVal = planResult.getSysVal();
            if (StringUtils.equals(sysVal, "R1104")) {
                cvChkPlanResult.setT1104Frq(planResult.getT1104Frq());
                cvChkPlanResult.setT1104PartVal(planResult.getT1104PartVal());
            }
            if (StringUtils.equals(sysVal, "EAST3")) {
                cvChkPlanResult.setEastFrq(planResult.getEastFrq());
                cvChkPlanResult.setEastPartVal(planResult.getEastPartVal());
            }
            if (StringUtils.equals(sysVal, "KHFX")) {
                cvChkPlanResult.setCustRskFrq(planResult.getCustRskFrq());
                cvChkPlanResult.setCustRskPartVal(planResult.getCustRskPartVal());
            }
            if (StringUtils.equals(sysVal, "DWDK")) {
                cvChkPlanResult.setFinBaseDataFrq(planResult.getFinBaseDataFrq());
                cvChkPlanResult.setFinBaseDataPartVal(planResult.getFinBaseDataPartVal());
            }
            if (StringUtils.equals(sysVal, "JRTJ")) {
                cvChkPlanResult.setBigFocusFrq(planResult.getBigFocusFrq());
                cvChkPlanResult.setBigFocusPartVal(planResult.getBigFocusPartVal());
            }
        }
        return cvChkPlanResult;
    }

    /**
     * 添加机构/法人名称
     * @param resultList
     * @return
     */
    private List<CvChkPlanResult> addOrgNm(List<CvChkPlanResult> resultList) {
        for (CvChkPlanResult cvChkPlanResult : resultList) {
            SysBranchInfoVo sysBranchInfo = branchInfoService.selectById(cvChkPlanResult.getOrgVal());
            if (sysBranchInfo != null) {
                cvChkPlanResult.setOrgNm(sysBranchInfo.getBranchName());
            }
        }
        return resultList;
    }

    /**
     * 添加系统频度
     *
     * @param resultList
     * @return
     */
    private List<CvChkPlanResult> addSysDateVal(List<CvChkPlanResult> resultList) {
        for (CvChkPlanResult cvChkPlanResult : resultList) {
            String sysVal = cvChkPlanResult.getSysVal();
            if (StringUtils.equals(sysVal, "R1104")) {
                cvChkPlanResult.setT1104Frq(cvChkPlanResult.getDateVal());
            }
            if (StringUtils.equals(sysVal, "EAST3")) {
                cvChkPlanResult.setEastFrq(cvChkPlanResult.getDateVal());
            }
            if (StringUtils.equals(sysVal, "KHFX")) {
                cvChkPlanResult.setCustRskFrq(cvChkPlanResult.getDateVal());
            }
            if (StringUtils.equals(sysVal, "DWDK")) {
                cvChkPlanResult.setFinBaseDataFrq(cvChkPlanResult.getDateVal());
            }
            if (StringUtils.equals(sysVal, "JRTJ")) {
                cvChkPlanResult.setBigFocusFrq(cvChkPlanResult.getDateVal());
            }
        }
        return resultList;
    }
}
