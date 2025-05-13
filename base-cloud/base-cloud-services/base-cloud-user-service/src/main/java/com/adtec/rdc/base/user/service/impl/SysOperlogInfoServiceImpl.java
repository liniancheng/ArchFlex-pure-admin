package com.adtec.rdc.base.user.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.user.mapper.SysOperlogInfoMapper;
import com.adtec.rdc.base.user.model.bo.SysOperlogVo;
import com.adtec.rdc.base.user.model.po.SysOperlogInfo;
import com.adtec.rdc.base.user.model.po.SysOperlogText;
import com.adtec.rdc.base.user.model.query.SysOperlogInfoQuery;
import com.adtec.rdc.base.user.model.query.SysOperlogTextQuery;
import com.adtec.rdc.base.user.service.SysOperlogInfoService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author: JTao
 * @date: 2018/11/14 14:57
 * @description: 系统日志服务实现类
 */
@Service
public class SysOperlogInfoServiceImpl extends ServiceImpl<SysOperlogInfoMapper, SysOperlogInfo>
		implements SysOperlogInfoService {
	public static String basePath = "d:\\dgmp";
	@Autowired
	private SysOperlogInfoMapper sysOperlogInfoMapper;
	
	@Async
	@Override
	public Boolean saveOperLog(SysOperlogInfo info) {
		return this.save(info);
	}

	@Override
	public SysOperlogInfoQuery pageByQuery(SysOperlogInfoQuery query) {
		query.setDesc("create_time");
		sysOperlogInfoMapper.pageByQuery(query);
		return query;
	}

	@Override
	public SysOperlogTextQuery pageTextByQuery(SysOperlogTextQuery query) {
		SysOperlogTextQuery pageTextByQuery = new SysOperlogTextQuery();
		File dir = new File(basePath);
		List<SysOperlogText> list = findFile(dir);
		pageTextByQuery.setRecords(list);
		pageTextByQuery.setSize(20);
		pageTextByQuery.setTotal(list.size());
		return pageTextByQuery;
	}

	@Override
	public void download(List<String> ids, HttpServletResponse response) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		QueryWrapper<SysOperlogInfo> query = new QueryWrapper<SysOperlogInfo>();
		query.lambda().in(SysOperlogInfo::getLogId, ids);
		List<SysOperlogInfo> list = sysOperlogInfoMapper.selectList(query);
		List<SysOperlogVo> listVo = new ArrayList<SysOperlogVo>();
		Optional.ofNullable(list).orElse(new ArrayList<SysOperlogInfo>()).forEach(log -> {
			SysOperlogVo vo = new SysOperlogVo();
			BeanUtils.copyProperties(log, vo);
			listVo.add(vo);
		});
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		String fileName;
		try {
			fileName = URLEncoder.encode("操作日志", "UTF-8").replaceAll("\\+", "%20");
			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), SysOperlogVo.class).sheet("操作日志").doWrite(listVo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}

	public  List<SysOperlogText>  findFile(File dir) {
		File[] dirFiles = dir.listFiles();
		List<SysOperlogText> list = new ArrayList<>();
		for(File temp : dirFiles){
		    if(temp.isFile() && temp.getAbsolutePath().endsWith(".log") ){
		    	SysOperlogText txt = new SysOperlogText();
				txt.setLogId(UUID.generate());
				txt.setLogName(temp.getName());
				txt.setLogPath(temp.getAbsolutePath());
				txt.setLogSize( new BigDecimal(String.valueOf(temp.length()/1000.0)).setScale(2, BigDecimal.ROUND_HALF_UP));
				list.add(txt);
			}
		}
		return list;
	}

}
