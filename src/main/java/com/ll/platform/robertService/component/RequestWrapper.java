package com.ll.platform.robertService.component;

import com.ll.platform.robertService.util.IOStreamUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Title RequestWrapper.java
 * @Description 包装HttpServletRequest，解决POST请求中的BODY参数不能重复读取多次的问题
 * @author Robert
 * @date 2019年12月2日
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	
	// 存储body数据的容器
	private final byte[] body;

	RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		// 将body数据存储起来
		body = IOStreamUtil.inputStream2String(request.getInputStream()).getBytes();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return inputStream.read();
			}

			@Override
			public void setReadListener(ReadListener listener) {}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}
	
	String getBodyString() {
		return new String(body);
	}
}
