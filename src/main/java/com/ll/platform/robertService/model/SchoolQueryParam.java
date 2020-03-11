package com.ll.platform.robertService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * @Title SchoolQueryParam.java
 * @Description 校区查询参数
 * @author Robert
 * @date 2019年12月9日
 */
@Getter
@Setter
public class SchoolQueryParam extends CommonRequiredParam{
	
	@ApiModelProperty(value = "校区id", hidden = true)
	private Integer id;
	
	@ApiModelProperty(value = "校区状态-1 for enabled, 2 for disabled", allowableValues = "1,2", example = "1")
	@Range(min = 1, max = 2)
	private Integer schoolStatus;
}
