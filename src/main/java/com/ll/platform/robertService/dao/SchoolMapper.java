package com.ll.platform.robertService.dao;

import com.ll.platform.robertService.model.School;
import com.ll.platform.robertService.model.SchoolQueryParam;

import java.util.List;

/**
 * @Title SchoolMapper.java
 * @Description SchoolMapper
 * @author Robert
 * @date 2019年12月9日
 */
public interface SchoolMapper {

	public List<School> list(SchoolQueryParam param);

	public List<Integer> getSchoolIds(SchoolQueryParam param);
}
