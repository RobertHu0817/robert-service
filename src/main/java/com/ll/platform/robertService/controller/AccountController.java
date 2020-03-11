package com.ll.platform.robertService.controller;

import com.ll.platform.robertService.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Title AccountController.java
 * @Description 账号控制器
 * @author Robert
 * @date 2019年12月6日
 */
@RestController
@RequestMapping("/account")
@ApiIgnore
public class AccountController {
	
	/**
	 * 禁用/启用账号
	 * @return
	 */
	@PostMapping("/disable")
	public Result<?> disable() {
		return Result.success();
	}
}
