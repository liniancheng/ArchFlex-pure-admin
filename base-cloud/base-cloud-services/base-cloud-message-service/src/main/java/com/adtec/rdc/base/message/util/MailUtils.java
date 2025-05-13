package com.adtec.rdc.base.message.util;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MailUtils {
	/**
	 * 获取内容
	 * @param content 内容(支持freemarker)
	 * @param paraMap 参数map
	 * @return
	 * @throws Exception
	 */
	public static String getContent(String content, Map<String, Object> paraMap) throws Exception {
        if (paraMap == null || paraMap.isEmpty()) {
            return content;
        }
        Template tpl = new Template("tpl", content, new Configuration(Configuration.VERSION_2_3_28));
        StringWriter result = new StringWriter();
        tpl.process(paraMap, result);
        return result.toString();
    }
}
