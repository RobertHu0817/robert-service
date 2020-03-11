package com.ll.platform.robertService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Title CommonPageQueryParam.java
 * @Description 分页查询请求公共参数
 * @author Robert
 * @date 2019年12月5日
 */
@Getter
@Setter
public class CommonPageQueryParam extends CommonParam{

	@ApiModelProperty(value = "page")
	private Integer page = 0;
	
	@ApiModelProperty(value = "size")
	private Integer size = 0;

}