package com.adtec.rdc.base.crossvalidation.util;



import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.crossvalidation.model.bo.JdbcSource;
import com.adtec.rdc.base.crossvalidation.model.bo.ResultBo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckSqlUtil {

    public static List<CvChkPlanDetail> execSql(List<CvChkPlanDetail> cvChkPlanDetailList, List<ResultBo> zjjList,
                                                JdbcSource jdbcSource) {
        List<String> execSqlList = cvChkPlanDetailList.stream().map(CvChkPlanDetail::getExecSql).collect(Collectors.toList());
        try {
            for (int i = 0; i < execSqlList.size(); i++) {
                String execSql = execSqlList.get(i);
                ResultBo resultBo = new ResultBo();
                if (StringUtils.isBlank(execSql)) {
                    resultBo.setBZ("BWB");
                    resultBo.setYE("不适配");
                    cvChkPlanDetailList.get(i).setResultBo(resultBo);
                    continue;
                }
                //获取数据库连接
                Connection connection = JDBCUtils.getConnection(jdbcSource);
                if (connection == null) {
                    throw new ServiceException("获取数据库连接失败,请检查配置!");
                }
                //获取执行预编译的SQL语句对象
                PreparedStatement preparedStatement = connection.prepareStatement(execSql);
                //执行sql查询获取结果集对象
                ResultSet resultSet = preparedStatement.executeQuery();
                String bz = null;
                String ye = null;
                //获取查询结果值
                while (resultSet.next()) {
                    bz = resultSet.getString("BZ");
                    ye = resultSet.getString("YE");
                }
                //关闭相关对象
                JDBCUtils.close(connection, preparedStatement, resultSet);
                //将结果值封装
                if (StringUtils.isBlank(bz) && StringUtils.isBlank(ye)) {
                    resultBo.setBZ("BWB");
                    resultBo.setYE("0");
                    cvChkPlanDetailList.get(i).setResultBo(resultBo);
                    continue;
                }
                resultBo.setBZ(bz);
                resultBo.setYE(ye);
                // 2. 执行sql 根据币种和数据时间获取币种率,获取汇总结果,并将BWB放入相关记录
                BigDecimal sumMoney = new BigDecimal(0);
                resultBo = dealResult(sumMoney, resultBo, zjjList);
                cvChkPlanDetailList.get(i).setResultBo(resultBo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("校验sql出错,请检查校验sql!");
        }
        return cvChkPlanDetailList;
    }

    private static ResultBo dealResult(BigDecimal sumMoney, ResultBo resultBo, List<ResultBo> zjjList) {
        //中间价list
        if (CollectionUtils.isEmpty(zjjList)) {
            zjjList = new ArrayList<>();
            //result结果do
            ResultBo rbo = new ResultBo();
            //人民币
            rbo.setWB("CNY");
            //人民币汇率
            rbo.setZJJ("100.000000");
            //中间价赋值
            zjjList.add(rbo);
        }
        //本外币标志
        boolean bwbFlag = false;
        //做余额的特殊处理
        try {
            new BigDecimal(resultBo.getYE());
        } catch (NullPointerException e) {
            e.printStackTrace();
            resultBo.setYE("0.00");
        }
        //本外币
        if ("BWB".equals(resultBo.getBZ())) {
            bwbFlag = true;
            if (bwbFlag) {
                return resultBo;
            }
            resultBo = new ResultBo();
            resultBo.setBZ("BWB");
            resultBo.setYE(sumMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            return resultBo;
        }
        //人民币
        if ("CNY".equals(resultBo.getBZ())) {
            //相加
            BigDecimal bd = new BigDecimal(resultBo.getYE());
            sumMoney = sumMoney.add(bd);
        } else {
            for (ResultBo zjjDo : zjjList) {
                String wb = zjjDo.getWB();
                Double zjj = Double.valueOf(zjjDo.getZJJ());
                if (!wb.equals(resultBo.getBZ())) {
                    continue;
                }

                BigDecimal bd = BigDecimal.valueOf(Double.valueOf(resultBo.getYE()) * zjj / 100);
                sumMoney = sumMoney.add(bd);
            }
        }
        if (bwbFlag) {
            return resultBo;
        } else {
            resultBo = new ResultBo();
            resultBo.setBZ("BWB");
            resultBo.setYE(sumMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            return resultBo;
        }
    }

    public static List<ResultBo> selectWbAndZjj(JdbcSource jdbcSource, String dataTime, String orgVal) {
        List<ResultBo> resultBolist = new ArrayList<>();
        String selectWbAndZjj = "SELECT WB,ZJJ FROM east3_hlxxb_r where  DIS_DATA_DATE = " + dataTime + " and  LP_INST_ID = " + orgVal;
        try {
            Connection connection = JDBCUtils.getConnection(jdbcSource);
            PreparedStatement preparedStatement = connection.prepareStatement(selectWbAndZjj);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String wb = resultSet.getString("WB");
                String zjj = resultSet.getString("ZJJ");
                ResultBo resultBo = new ResultBo();
                resultBo.setWB(wb);
                resultBo.setZJJ(zjj);
                resultBolist.add(resultBo);
            }
            JDBCUtils.close(connection,preparedStatement,resultSet);
            return resultBolist;
        } catch (Exception e) {
            e.printStackTrace();
            return resultBolist;
        }
    }
}
