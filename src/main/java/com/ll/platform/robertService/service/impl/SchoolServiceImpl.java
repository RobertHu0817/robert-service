package com.ll.platform.robertService.service.impl;

import com.ll.platform.robertService.dao.SchoolMapper;
import com.ll.platform.robertService.model.School;
import com.ll.platform.robertService.model.SchoolQueryParam;
import com.ll.platform.robertService.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title SchoolService.java
 * @Description SchoolService
 * @author Robert
 * @date 2019年12月9日
 */
@Service
public class SchoolServiceImpl implements SchoolService {
	
	@Autowired
	private SchoolMapper schoolMapper;
	
	public List<School> list(SchoolQueryParam schoolQueryParam){
		return schoolMapper.list(schoolQueryParam);
	}
}
