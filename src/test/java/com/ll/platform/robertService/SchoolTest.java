package com.ll.platform.robertService;

import com.ll.platform.robertService.component.RequestWrapperFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.TreeMap;

@RunWith(SpringRunner.class)
//@WebMvcTest(StudentController.class)
@SpringBootTest
@Transactional
public class SchoolTest {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setMockMvc() {
		// ?? 过滤器还得自己加？
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new RequestWrapperFilter()).build();
	}
	
	@Test
	public void listTest() throws Exception {
		TreeMap<String, Object> reqMap = new TreeMap<>();
		reqMap.put("orgCode", "TEST_ORG");
		reqMap.put("schoolStatus", 1);
		TestUtil.postWithSign(mockMvc, "/school/list", reqMap);
	}

}
