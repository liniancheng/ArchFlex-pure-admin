package com.adtec.rdc.base.migrate.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.migrate.model.bo.MigrateExport;


/**
 * 文件导出导出工具类
 * 
 * @author dengchf
 *
 */
public class FileUtils {

	public static synchronized void downloadXml(String fileName, MigrateExport migratExport,
			HttpServletRequest request, HttpServletResponse response) {
		
		ServletOutputStream out = null;
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentType("application/octet-stream; charset=UTF-8");
			out = response.getOutputStream();
			ObjectOutputStream oos = null;
			oos = new ObjectOutputStream(out);
			oos.writeObject(migratExport);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public static MigrateExport readXmlFile(MultipartFile file) {
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		InputStream is = null;
		try{
			is = file.getInputStream();
			bos = new ByteArrayOutputStream();
			int len;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			byte[] bytes = bos.toByteArray();
			
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			return (MigrateExport) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				if(bos != null) {
					bos.close();
					bos = null;
				}
				if(is != null) {
					is.close();
					is = null;
				}
				if(ois != null) {
					ois.close();
					ois = null;
				}
				if(bis != null) {
					bis.close();
					bis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new ServiceException(e.getMessage());
			}
		}
	}

}
