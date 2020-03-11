package com.ll.platform.robertService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Title Swagger2Config.java
 * @Description Swagger2 配置
 * @author Robert
 * @date 2019年11月29日
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Value("${swagger.baseUrl}")
	private String baseUrl;
	
	@Bean
	public Docket createRestApi() {
		// 全局响应
		ArrayList<ResponseMessage> responseMessage = new ArrayList<>();
		responseMessage.add(new ResponseMessageBuilder().code(200).message("200").responseModel(new ModelRef("OK")).build());
		
		return new Docket(DocumentationType.SWAGGER_2).host(baseUrl).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.ll.platform.robertService.controller"))
				.paths(PathSelectors.any()).build().useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.POST, responseMessage)
				.consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
				.produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE));
	}

	// 基本信息
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Robert Partner API").description("The Robert Service API is built on HTTP and JSON, so any standard HTTP client can send requests to it and parse the responses.")
				.version("1.0").build();
	}
}
