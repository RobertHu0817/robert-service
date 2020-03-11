package com.ll.platform.robertService.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * @Title School.java
 * @Description 校区
 * @author Robert
 * @date 2019年11月30日
 */
@Getter
@Setter
public class School {

	@ApiModelProperty(value = "校区id")
	private Integer id;
	
	@ApiModelProperty(value = "校区名称")
	private String schoolName;
	
	@ApiModelProperty(value = "校区状态-1 for enabled, 2 for disabled", allowableValues = "1,2", example = "1")
	@Range(min = 1, max = 2)
	private Integer schoolStatus;
	
	@ApiModelProperty(value = "机构编码")
	private String orgCode;
}
