package com.ll.platform.robertService.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ll.platform.robertService.common.Result;
import com.ll.platform.robertService.common.ResultCodeEnum;
import com.ll.platform.robertService.component.AuthValidateInterceptor;
import com.ll.platform.robertService.component.RequestWrapperFilter;
import com.ll.platform.robertService.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Title WebMvcConfig.java
 * @Description Spring MVC 配置
 * @author Robert
 * @date 2019年12月1日
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);
	@Autowired
	private AuthValidateInterceptor authValidateInterceptor;
	@Autowired
	private YmlConfig ymlConfig;
	
	
	// 使用阿里 FastJson 作为JSON MessageConverter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(StandardCharsets.UTF_8);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8));
		converters.add(0, converter);
	}

	// 拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authValidateInterceptor).excludePathPatterns(ymlConfig.getIgnoredUrls());
	}
	
	// 过滤器
	@SuppressWarnings({ "rawtypes"})
	@Bean
	public FilterRegistrationBean registerFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new RequestWrapperFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}
	
    // 统一异常处理
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
				Result<?> result;
				if (e instanceof NoHandlerFoundException) {
					result = Result.failure(ResultCodeEnum.NOT_FOUND);
				} else if (e instanceof HttpRequestMethodNotSupportedException){
					result = Result.failure(ResultCodeEnum.METHOD_NOT_ALLOWED, e.getMessage());					
				} else if (e instanceof HttpMessageNotReadableException){
					result = Result.failure(ResultCodeEnum.PARAM_TYPE_BIND_ERROR, e.getMessage());
				} else if (e instanceof MethodArgumentNotValidException){
					// hibernate validate TODO NotEmpty等枚举常量在哪
//					FieldError fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
					switch (((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getCode()) {
					case "NotEmpty":
					case "NotBlank":
					case "NotNull":
						result = Result.failure(ResultCodeEnum.PARAM_NOT_COMPLETE);
						break;
					case "Pattern":
					case "Min":
					case "Max":
					case "Length":
						result = Result.failure(ResultCodeEnum.PARAM_VALUE_ILLEGAL);
						break;
					default:
						result = Result.failure(ResultCodeEnum.PARAM_VALIDATE_FAIL);
						break;
					}
				}else {
					result = Result.failure(ResultCodeEnum.SERVER_ERROR, e.getMessage());
					String message;
					if (handler instanceof HandlerMethod) {
						HandlerMethod handlerMethod = (HandlerMethod) handler;
						message = String.format("Interface [%s] throw exception, method:%s.%s, summary:%s",
								request.getRequestURI(), handlerMethod.getBean().getClass().getName(),
								handlerMethod.getMethod().getName(), e.getMessage());
					} else {
						message = e.getMessage();
					}
					LOGGER.error(message, e);
				}
				LOGGER.info(e.getMessage(), e);
				WebUtil.responseResult(response, result);
				return new ModelAndView();
			}

		});
	}

	// 静态资源配置
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// swagger2
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}