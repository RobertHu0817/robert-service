package com.ll.platform.robertService.controller;

import com.ll.platform.robertService.common.Result;
import com.ll.platform.robertService.model.School;
import com.ll.platform.robertService.model.SchoolQueryParam;
import com.ll.platform.robertService.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Title SchoolController.java
 * @Description 校区控制器
 * @author Robert
 * @date 2019年12月9日
 */
@RestController
@RequestMapping("/school")
@Api(tags = "School")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 新建课程
	 * @param schoolQueryParam
	 * @return
	 */
	@ApiOperation("查询机构下属校区")
	@PostMapping("/list")
	public Result<List<School>> list(@RequestBody @Valid SchoolQueryParam schoolQueryParam) {
		return Result.success(schoolService.list(schoolQueryParam));
	}
}
