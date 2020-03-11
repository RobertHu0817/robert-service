package com.ll.platform.robertService.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @Title HibernateValidatorConfig.java
 * @Description hibernate validator配置
 * @author Robert
 * @date 2019年12月5日
 */
@Configuration
public class HibernateValidatorConfig {

	@Bean
	Validator validator() {
		// 配置为快速失败模式
		return Validation.byProvider(HibernateValidator.class).configure()
				.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory().getValidator();
	}
}
