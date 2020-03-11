package com.ll.platform.robertService.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Title WebUtil.java
 * @Description IO流工具类
 * @author Robert
 * @date 2019年11月29日
 */
public class IOStreamUtil {
	
	/**
	 * 将inputStream里的数据读取出来并转换成字符串
	 * @param inputStream 输入流
	 * @return 字符串
	 * @throws IOException 
	 */
	public static String inputStream2String(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}

		return sb.toString();
	}
}
