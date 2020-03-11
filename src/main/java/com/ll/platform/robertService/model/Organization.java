package com.ll.platform.robertService.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Title BkOrg.java
 * @Description 机构
 * @author Robert
 * @date 2019年11月30日
 */
@Getter
@Setter
public class Organization {

	private Integer id;
	private String orgCode;
	private Integer serviceOpen;
	private String serviceSecret;
	private List<Integer> schoolIds;
}
