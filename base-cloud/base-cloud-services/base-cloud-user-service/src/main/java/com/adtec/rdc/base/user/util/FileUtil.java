package com.adtec.rdc.base.user.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.adtec.rdc.base.common.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	

	private final static int BUFFER = 1024;

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName:指定的文件全路径名 strDestDir: 移动到指定的文件夹
	 * 返回值: 如果成功true;否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean copyTo(String strSourceFileName, String strDestDir) {
		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);

		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			log.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}

		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				log.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}

		try {
			String strAbsFilename = strDestDir + File.separator
					+ fileSource.getName();

			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

			log.debug("开始拷贝文件");

			int count = -1;

			long nWriteSize = 0;
			long nFileSize = fileSource.length();

			byte[] data = new byte[BUFFER];

			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

				fileOutput.write(data, 0, count);

				nWriteSize += count;

				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;

				String msg = null;

				if (size <= 100 && size >= 0) {
					msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
					log.debug(msg);
				} else if (size > 100) {
					msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
					log.debug(msg);
				}

			}

			fileInput.close();
			fileOutput.close();

			log.debug("拷贝文件成功!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功 能: 删除指定的文件 参 数: 指定绝对路径的文件名 strFileName 返回值: 如果删除成功true否则false;
	 * 
	 * @param strFileName
	 * @return
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			log.debug(strFileName + "不存在!");
			return false;
		}

		return fileDelete.delete();
	}

	/**
	 * 功 能: 移动文件(只能移动文件) 参 数: strSourceFileName: 是指定的文件全路径名 strDestDir:
	 * 移动到指定的文件夹中 返回值: 如果成功true; 否则false
	 * 
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean moveFile(String strSourceFileName, String strDestDir) {
		if (copyTo(strSourceFileName, strDestDir))
			return delete(strSourceFileName);
		else
			return false;
	}

	/**
	 * 功 能: 创建文件夹 参 数: strDir 要创建的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean makeDir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists()) {
			return fileNew.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 功 能: 删除文件夹 参 数: strDir 要删除的文件夹名称 返回值: 如果成功true;否则false
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean removeDir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();
			if(fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					String subFile = strDir + File.separator + fileList[i];
					File tmp = new File(subFile);
					if (tmp.isFile())
						tmp.delete();
					else if (tmp.isDirectory())
						removeDir(subFile);
				}
			}
			rmDir.delete();
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 将文件转成输出流
	 * 
	 * @param fileName
	 * @return
	 */
	public static ByteArrayOutputStream file2OutStream(String fileName)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int reader = 0;
		while ((reader = in.read(buffer)) != -1) {
			out.write(buffer, 0, reader);
		}
		in.close();
		out.close();
		return out;
	}

	public static Map<String, String> iconMap = new HashMap<String, String>();
	static {
		iconMap.put("BMP", "file");
		iconMap.put("RAR", "file-zip");
		iconMap.put("ZIP", "file-zip");
		iconMap.put("TXT", "file-text");
		iconMap.put("DOC", "file-word");
		iconMap.put("XLS", "file-excel");
		iconMap.put("DIR", "folder");
		iconMap.put("EXE", "file");
		iconMap.put("GIF", "file-image");
		iconMap.put("HTM", "file");
		iconMap.put("JPG", "file-image");
		iconMap.put("OTH", "file");
		iconMap.put("DOCX", "file-word");
		iconMap.put("XLSX", "file-excel");
		iconMap.put("PDF", "file-pdf");
	}

	public static void download(String fileName, String realFileName,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println(realFileName);
		InputStream input = FileUtils.openInputStream(new File(realFileName));
		byte[] data = IOUtils.toByteArray(input);
		fileName = encoderName(request.getHeader("User-Agent"), fileName);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
		IOUtils.closeQuietly(input);
	}

	public static void download(String fileName, byte[] dataByte,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		byte[] data = dataByte;
		fileName = encoderName(request.getHeader("User-Agent"), fileName);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
	
	/**
	 * 将流转换成list,并生成零时文件
	 * @param fTmp 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> readFile(InputStream is, File fTmp) throws ServiceException {
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		FileOutputStream Out = null; 
		try{
			bos = new ByteArrayOutputStream();
			int len;
			byte[] b = new byte[1024];
			Out = new FileOutputStream(fTmp);
			while ((len = is.read(b)) != -1) {
				Out.write(b,0,len);
				bos.write(b, 0, len);
			}
			Out.close();
			byte[] bytes = bos.toByteArray();
			
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			List<Object> list = (List<Object>) ois.readObject();
			return list;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				if(bos!=null){
					bos.close();
					bos = null;	
				}
				if(is!=null){
					is.close();
					is = null;
				}
				if(ois!=null){
					ois.close();
					ois = null;
				}
				if(bis!=null){
					bis.close();
					bis = null;	
				}
				if(Out!=null){
					Out.close();
					Out = null;	
				}	
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		}

	}
	
	/**
	 * 将流转换成list
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> readFile(InputStream is) throws ServiceException {
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try{
			bos = new ByteArrayOutputStream();
			int len;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			byte[] bytes = bos.toByteArray();
			
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			List<Object> list = (List<Object>) ois.readObject();
			return list;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				if(bos!=null){
					bos.close();
					bos = null;	
				}
				if(is!=null){
					is.close();
					is = null;
				}
				if(ois!=null){
					ois.close();
					ois = null;
				}
				if(bis!=null){
					bis.close();
					bis = null;	
				}
			} catch (IOException e) {
				throw new ServiceException(e.getMessage());
			}
		}

	}
	
	public static void download(String fileName, List<Object> o,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ServletOutputStream out = null;
		response.reset();
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setContentType("application/octet-stream; charset=UTF-8");
		out = response.getOutputStream();
		ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(out);
		oos.writeObject(o);
		out.flush();
		out.close();
	}
	
	public static HSSFRow createRow(HSSFSheet sheet, int index) {
		return sheet.createRow(index);
	}

	public static HSSFCell createCell(HSSFRow row, int index) {
		return row.createCell(index);
	}

	// yujz 2013-05-1
	public static void download(String fileName, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		OutputStream Out = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		fileName = fileName + ".xls";
		fileName = encoderName(request.getHeader("User-Agent"), fileName);
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		workbook.write(Out);
		Out.close();
	}

	
	
	public static void copyFile(String infileName, String outfileName, boolean makeDir) throws FileNotFoundException, IOException {
		File infile = new File(infileName);
		File outfile = new File(outfileName);
		if(makeDir){
			if(!outfile.getParentFile().exists()){
				outfile.getParentFile().mkdirs();
			}
		}
		copyFile(infile, outfile);
	}
	
	public static void copyFile(File infile, File outfile) throws FileNotFoundException, IOException {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		try{
			fin = new FileInputStream(infile);
			fout = new FileOutputStream(outfile);
			fcin = fin.getChannel();
			fcout = fout.getChannel();
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			while (true) {
				buffer.clear();
				int r = fcin.read(buffer);
				if (r == -1) {
					break;
				}
				buffer.flip();
				fcout.write(buffer);
			}
		}catch(FileNotFoundException fnfe){
			throw new FileNotFoundException(fnfe.getMessage());
		}catch(IOException ioe){
			throw new FileNotFoundException(ioe.getMessage());
		}finally{
			if(fcout != null) fcout.close();
			if(fcin != null) fcin.close();
			if(fout != null) fout.close();
			if(fin != null) fin.close();
			outfile = null;
			infile = null;
		}
		
	}
	
	private enum EXPLORER_TYPE{IE, FIREFOX, CHROME, EDGE, OTHER};
	
	private static EXPLORER_TYPE getExplorerType(String userAgent){
		userAgent = userAgent.toUpperCase();
		if(userAgent.indexOf("MSIE")>0){
			return EXPLORER_TYPE.IE;
		}else if(userAgent.indexOf("FIREFOX") >0){
			return EXPLORER_TYPE.FIREFOX;
		}else if(userAgent.indexOf("CHROME") > 0){
			return EXPLORER_TYPE.CHROME;
		}else if(userAgent.indexOf("EDGE") > 0){
			return EXPLORER_TYPE.CHROME;
		}else if(userAgent.indexOf("GECKO") >0){//没有其他标识，则判断为IE11
			return EXPLORER_TYPE.IE;
		}else{
			return EXPLORER_TYPE.OTHER;
		}
	}
	
	private static String encoderName(String userAgent, String name) throws UnsupportedEncodingException{
		EXPLORER_TYPE type = getExplorerType(userAgent);
		if (type == EXPLORER_TYPE.FIREFOX || type == EXPLORER_TYPE.CHROME) {
			name = new String(name.getBytes("UTF-8"), "ISO8859-1");
		} else if (type == EXPLORER_TYPE.IE || type == EXPLORER_TYPE.EDGE) {
			name = URLEncoder.encode(name, "UTF-8");
		}
		return name;
	}
	
	/**
	 * 获取文件的后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		String result = null;
		if(fileName != null && fileName.indexOf(".")>0){
			result = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		}
		return result;
	}
    
	public static boolean newFile(String fileName, String path, InputStream in){
		boolean r = false;
		try{
			if(path == null || path.trim().length() == 0 )throw new Exception("路径为空，请检查！");
			if(fileName == null || fileName.trim().length() == 0)throw new Exception("文件名称为空，请检查！");
			path = path.replaceAll("\\\\", "/");
			FileUtil.makeDir(path);
			if(!path.endsWith("/")) path += "/";
			File file = new File(path + fileName);
			file.createNewFile();
			FileOutputStream fo = new FileOutputStream(file);
			byte [] bytes = new byte[1024];
			int n = 0;
			while((n = in.read(bytes)) != -1){
				fo.write(bytes, 0, n);
			}
			fo.close();
			in.close();
			r = true;
		}catch(Exception e){
			r = false;
		}
		return r;
	}
}