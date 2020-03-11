package com.ll.platform.robertService.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title BkOrgParam.java
 * @Description 机构查询参数
 * @author Robert
 * @date 2019年11月30日
 */
@Getter
@Setter
public class OrgQueryParam {

	private Integer id;
	private String orgCode;
	private Integer serviceOpen;
}
