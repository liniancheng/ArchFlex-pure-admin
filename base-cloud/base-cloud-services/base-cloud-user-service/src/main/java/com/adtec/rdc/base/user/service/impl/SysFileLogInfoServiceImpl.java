package com.adtec.rdc.base.user.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.user.model.po.SysOperlogText;
import com.adtec.rdc.base.user.model.query.SysOperlogTextQuery;
import com.adtec.rdc.base.user.service.SysFileLogInfoService;

@Service
public class SysFileLogInfoServiceImpl implements SysFileLogInfoService {
	public static String basePath = "/home/sh";
	
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
