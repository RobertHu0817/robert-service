package com.ll.platform.robertService.component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Title RequestWrapperFilter.java
 * @Description 包装HttpServletRequest
 * @author Robert
 * @date 2019年11月29日
 */
public class RequestWrapperFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			// FIXME 有不进这个逻辑的请求吗？
			requestWrapper = new RequestWrapper((HttpServletRequest) request);
		}
		if (requestWrapper != null) {
			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}

}