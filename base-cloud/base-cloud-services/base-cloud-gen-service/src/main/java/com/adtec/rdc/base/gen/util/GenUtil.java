package com.adtec.rdc.base.gen.util;

import com.adtec.rdc.base.gen.model.config.BaseConfig;
import com.adtec.rdc.base.gen.model.config.ColumnInfoConfig;
import com.adtec.rdc.base.gen.model.config.TableInfoConfig;
import com.adtec.rdc.base.gen.model.po.ColumnInfo;
import com.adtec.rdc.base.gen.model.po.TableInfo;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: JTao
 * @date: 2018/11/8 14:09
 */
public class GenUtil {

	private static Boolean hasBigDecimal = false;

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<String>();
		templates.add("templates/mybatis/po.java.vm");
		templates.add("templates/mybatis/mapper.xml.vm");
		templates.add("templates/mybatis/mapper.java.vm");
		templates.add("templates/mybatis/service.java.vm");
		templates.add("templates/mybatis/serviceImpl.java.vm");
		templates.add("templates/mybatis/query.java.vm");
		templates.add("templates/mybatis/controller.java.vm");
		templates.add("templates/pages/data.d.ts.vm");
		templates.add("templates/pages/DataForm.tsx.vm");
		templates.add("templates/pages/index.tsx.vm");
		templates.add("templates/pages/model.ts.vm");
		templates.add("templates/pages/service.ts.vm");
		templates.add("templates/pages/enUS.ts.vm");
		templates.add("templates/pages/zhCN.ts.vm");
		templates.add("templates/vue/DataForm.vue.vm");
		templates.add("templates/vue/index.vue.vm");
		templates.add("templates/vue/index.js.vm");
		return templates;
	}

	/**
	 * 生成代码
	 * 
	 * @param tableInfo
	 * @param columns
	 * @param zip
	 */
	public static void generatorCode(BaseConfig buildConfig, TableInfo tableInfo, List<ColumnInfo> columns,
			ZipOutputStream zip) {
		hasBigDecimal = false;
		// 配置信息
		Configuration config = getConfig();
		// 表信息
		TableInfoConfig tableConfig = new TableInfoConfig();
		// 配置 包名等配置
		buildTableConfig(buildConfig, config, tableConfig);
		BeanUtils.copyProperties(tableInfo, tableConfig);
		// 构建表基本信息
		buildTableInfo(config, tableConfig, columns);
		// 生成代码
		gen(buildConfig, tableConfig, zip);
	}

	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if (StringUtils.isNotBlank(tablePrefix)) {
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig() {
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, TableInfoConfig tableInfoConfig) {
		String resourcesPath = "main" + File.separator + "resources";
		String className = tableInfoConfig.getClassName();
		String pagePath = "pages" + File.separator + tableInfoConfig.getModuleName() + File.separator
				+ tableInfoConfig.getFuncName();
		if (template.contains("templates/mybatis/po.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getPoPackageName().replace(".", File.separator);
			return path + File.separator + className + ".java";
		}
		if (template.contains("templates/mybatis/query.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getQueryPackageName().replace(".", File.separator);
			return path + File.separator + className + "Query.java";
		}
		if (template.contains("templates/mybatis/mapper.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getMapperPackageName().replace(".", File.separator);
			return path + File.separator + className + "Mapper.java";
		}
		if (template.contains("templates/mybatis/service.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getServicePackageName().replace(".", File.separator);
			return path + File.separator + className + "Service.java";
		}
		if (template.contains("templates/mybatis/serviceImpl.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getServicePackageName().replace(".", File.separator);
			return path + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		if (template.contains("templates/mybatis/controller.java.vm")) {
			String path = "main" + File.separator + "java" + File.separator
					+ tableInfoConfig.getControllerPackageName().replace(".", File.separator);
			return path + File.separator + className + "Controller.java";
		}
		if (template.contains("templates/mybatis/mapper.xml.vm")) {
			return resourcesPath + File.separator + "mapper" + File.separator + className + "Mapper.xml";
		}
		if (template.contains("templates/pages/data.d.ts.vm")) {
			return pagePath + File.separator + "data.d.ts";
		}
		if (template.contains("templates/pages/DataForm.tsx.vm")) {
			return pagePath + File.separator + "components" + File.separator + "DataForm.tsx";
		}
		if (template.contains("templates/pages/index.tsx.vm")) {
			return pagePath + File.separator + "index.tsx";
		}
		if (template.contains("templates/pages/model.ts.vm")) {
			return pagePath + File.separator + "model.ts";
		}
		if (template.contains("templates/pages/service.ts.vm")) {
			return pagePath + File.separator + "service.ts";
		}
		if (template.contains("templates/pages/enUS.ts.vm")) {
			return pagePath + File.separator + "locales" + File.separator + "en-US.ts";
		}
		if (template.contains("templates/pages/zhCN.ts.vm")) {
			return pagePath + File.separator + "locales" + File.separator + "zh-CN.ts";
		}
		if (template.contains("templates/vue/DataForm.vue.vm")) {
			pagePath = pagePath.replaceFirst("pages", "");
			return "vue" + File.separator + "views" + pagePath + File.separator + "modules" + File.separator
					+ "DataForm.vue";
		}
		if (template.contains("templates/vue/index.vue.vm")) {
			pagePath = pagePath.replaceFirst("pages", "");
			return "vue" + File.separator + "views" + pagePath + File.separator + "index.vue";
		}
		if (template.contains("templates/vue/index.js.vm")) {
			pagePath = pagePath.replaceFirst("pages", "");
			return "vue" + File.separator + "api" + pagePath + File.separator + "index.js";
		}
		return null;
	}

	/**
	 * 构建表配置信息 包名等
	 * 
	 * @param buildConfig
	 * @param configuration
	 * @param tableInfoConfig
	 */
	public static void buildTableConfig(BaseConfig buildConfig, Configuration configuration,
			TableInfoConfig tableInfoConfig) {
		BeanUtils.copyProperties(buildConfig, tableInfoConfig);
		// 包名
		if (StringUtils.isEmpty(tableInfoConfig.getPackageName())) {
			tableInfoConfig.setPackageName(configuration.getString("packageName", "com.adtec.rdc"));
		}
		// 模块名
		if (StringUtils.isEmpty(tableInfoConfig.getModuleName())) {
			tableInfoConfig.setModuleName(configuration.getString("moduleName", "base"));
		}
		// 功能英文名
		if (StringUtils.isEmpty(tableInfoConfig.getFuncName())) {
			tableInfoConfig.setFuncName(configuration.getString("funcName", "func"));
		}
		// 功能中文名
		if (StringUtils.isEmpty(tableInfoConfig.getFuncDesc())) {
			tableInfoConfig.setFuncName(configuration.getString("funcDesc", "功能"));
		}
		// po包
		if (StringUtils.isEmpty(tableInfoConfig.getPoPackageName())) {
			tableInfoConfig.setPoPackageName(configuration.getString("poPackageName", "model.po"));
		}
		tableInfoConfig.setPoPackageName(tableInfoConfig.getPackageName() + "." + tableInfoConfig.getModuleName() + "."
				+ tableInfoConfig.getPoPackageName());
		// query包
		if (StringUtils.isEmpty(tableInfoConfig.getQueryPackageName())) {
			tableInfoConfig.setQueryPackageName(configuration.getString("queryPackageName", "model.query"));
		}
		tableInfoConfig.setQueryPackageName(tableInfoConfig.getPackageName() + "." + tableInfoConfig.getModuleName()
				+ "." + tableInfoConfig.getQueryPackageName());
		// controller
		if (StringUtils.isEmpty(tableInfoConfig.getControllerPackageName())) {
			tableInfoConfig.setControllerPackageName(configuration.getString("controllerPackageName", "controller"));
		}
		tableInfoConfig.setControllerPackageName(tableInfoConfig.getPackageName() + "."
				+ tableInfoConfig.getModuleName() + "." + tableInfoConfig.getControllerPackageName());
		// mapper
		if (StringUtils.isEmpty(tableInfoConfig.getMapperPackageName())) {
			tableInfoConfig.setMapperPackageName(configuration.getString("mapperPackageName", "mapper"));
		}
		tableInfoConfig.setMapperPackageName(tableInfoConfig.getPackageName() + "." + tableInfoConfig.getModuleName()
				+ "." + tableInfoConfig.getMapperPackageName());
		// service
		if (StringUtils.isEmpty(tableInfoConfig.getServicePackageName())) {
			tableInfoConfig.setServicePackageName(configuration.getString("servicePackageName", "service"));
		}
		tableInfoConfig.setServicePackageName(tableInfoConfig.getPackageName() + "." + tableInfoConfig.getModuleName()
				+ "." + tableInfoConfig.getServicePackageName());
		// service impl
		if (StringUtils.isEmpty(tableInfoConfig.getServiceImplPackageName())) {
			tableInfoConfig
					.setServiceImplPackageName(configuration.getString("serviceImplPackageName", "service.impl"));
		}
		tableInfoConfig.setServiceImplPackageName(tableInfoConfig.getPackageName() + "."
				+ tableInfoConfig.getModuleName() + "." + tableInfoConfig.getServiceImplPackageName());
		// author
		if (StringUtils.isEmpty(tableInfoConfig.getAuthorName())) {
			tableInfoConfig.setAuthorName(configuration.getString("authorName", "adtec"));
		}
	}

	/**
	 * 构建表基本数据信息
	 * 
	 * @param config
	 * @param tableConfig
	 * @param columns
	 */
	public static void buildTableInfo(Configuration config, TableInfoConfig tableConfig, List<ColumnInfo> columns) {
		// 表名转换成Java类名
		String className = tableToJava(tableConfig.getTableName(), config.getString("tablePrefix"));
		tableConfig.setClassName(className);
		tableConfig.setLowerClassName(StringUtils.uncapitalize(className));

		// 列信息
		List<ColumnInfoConfig> columnInfoConfigs = new ArrayList<>();
		for (ColumnInfo column : columns) {

			ColumnInfoConfig columnEntity = new ColumnInfoConfig();
			BeanUtils.copyProperties(column, columnEntity);
			if (column.getIsNullable().equals("YES")) {
				columnEntity.setNullable(true);
			} else {
				columnEntity.setNullable(false);
			}

			// 列名转换成Java属性名
			String upAttrName = columnToJava(column.getColumnName());
			columnEntity.setUpAttrName(upAttrName);
			String temp = columnEntity.getUpAttrName();
			temp = (new StringBuilder()).append(Character.toLowerCase(temp.charAt(0))).append(temp.substring(1))
					.toString();
			columnEntity.setAttrName(temp);
			// 列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);

			// 列的JAVA类型，转换成TS类型
			String tsType = config.getString(attrType, "string");
			columnEntity.setTsType(tsType);
			if (!hasBigDecimal && StringUtils.equals("BigDecimal", column.getDataType())) {
				hasBigDecimal = true;
			}
			// 是否主键
			if (tableConfig.getPk() == null && StringUtils.equalsIgnoreCase("PRI", column.getColumnKey())) {
				tableConfig.setPk(columnEntity);
			}
			columnInfoConfigs.add(columnEntity);
		}
		tableConfig.setColumnInfo(columnInfoConfigs);
		// 没主键，则第一个字段为主键
		if (tableConfig.getPk() == null) {
			tableConfig.setPk(tableConfig.getColumnInfo().get(0));
		}
	}

	/**
	 * 生成代码
	 * 
	 * @param buildConfig
	 * @param tableConfig
	 * @param zip
	 */
	public static void gen(BaseConfig buildConfig, TableInfoConfig tableConfig, ZipOutputStream zip) {
		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		LocalDateTime now = LocalDateTime.now();
		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableInfo", tableConfig);
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("datetime", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		putVueVar(map);
		VelocityContext context = new VelocityContext(map);
		// 获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			try {
				// 添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableConfig)));
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new RuntimeException("渲染模板失败，表名：" + tableConfig.getTableName(), e);
			}
		}
	}
	
	/**
	 * 存入Vue自带变量 ex: $message, in vm file is ${message}
	 * @param map
	 */
	private static void putVueVar(Map<String, Object> map) {
		map.put("message", "$message");
		map.put("emit", "$emit");
		map.put("refs", "$refs");
	}
}
