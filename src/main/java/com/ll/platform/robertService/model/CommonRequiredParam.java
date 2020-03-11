package com.ll.platform.robertService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @Title CommonParam.java
 * @Description 请求公共必填参数
 * @author Robert
 * @date 2019年12月5日
 */
@Getter
@Setter
public class CommonRequiredParam {

	public static final String KEY_ORG_CODE = "orgCode";
	public static final String KEY_SIGN = "sign";
	
	@ApiModelProperty(value = "机构编码", required = true)
	@NotEmpty
	private String orgCode;
	
	@ApiModelProperty(value = "sign", required = true)
	@NotEmpty
	private String sign;
}