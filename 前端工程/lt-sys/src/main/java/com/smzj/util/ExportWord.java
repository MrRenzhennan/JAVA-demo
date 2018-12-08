package com.smzj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.smzj.common.ConfSystemConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ExportWord {
	
	private static final Logger logger = Logger.getLogger(ExportWord.class);

	private static Configuration configuration = null;

	public ExportWord() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	public String createWord(Map<String, Object> dataMap, HttpServletResponse response, HttpServletRequest request, String templateName) throws IOException {
		configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File(ConfSystemConstants.UPLOAD_SAVE_PATH + "/"));
		Template t = null;
		SecureRandom random = new SecureRandom();
		try {
			t = configuration.getTemplate(templateName,"UTF-8"); // 文件名
		} catch (IOException e) {
			logger.error(e);
		}
		String filepath = ConfSystemConstants.UPLOAD_SAVE_PATH + "/outFile/";
		FileUtil.createFilePath(filepath);
		filepath += "outFile" + random.nextInt(100000) + ".doc";
		File outFile = new File(filepath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
		} catch (FileNotFoundException e1) {
			logger.error(e1);
		}
		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return filepath;
	}
}