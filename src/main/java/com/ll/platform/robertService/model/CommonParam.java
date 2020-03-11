package com.ll.platform.robertService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Title CommonParam.java
 * @Description 请求公共参数
 * @author Robert
 * @date 2019年12月6日
 */
@Getter
@Setter
public class CommonParam extends CommonRequiredParam {
	
	public static final String KEY_SCHOOL_ID = "schoolId";
	
	@ApiModelProperty(value = "校区id", example = "1", required = true)
	@NotNull
	private Integer schoolId;
}