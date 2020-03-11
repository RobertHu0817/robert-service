package com.ll.platform.robertService.service;

import com.ll.platform.robertService.model.School;
import com.ll.platform.robertService.model.SchoolQueryParam;

import java.util.List;

/**
 * @Title SchoolService.java
 * @Description SchoolService
 * @author Robert
 * @date 2019年12月9日
 */
public interface SchoolService {
	
	List<School> list(SchoolQueryParam schoolQueryParam);
}
