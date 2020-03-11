package com.ll.platform.robertService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ll.platform.robertService.dao")
public class RobertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobertServiceApplication.class, args);
	}

}
