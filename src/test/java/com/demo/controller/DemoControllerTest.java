package com.demo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.config.SecurityConfig;
import com.demo.service.DemoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
	controllers = DemoController.class,
	excludeAutoConfiguration = SecurityAutoConfiguration.class,
	excludeFilters = {
			@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=SecurityConfig.class)
	}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("DemoController 테스트 클래스 ")
class DemoControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(DemoControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DemoService demoServcie;
	
	@Test
	@Order(1)
	public void demo_table_전체_조회_테스트() {
		logger.debug("demo_table_전체_조회_테스트");
		String url = "/selectAll.json";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(result -> {
						MockHttpServletResponse response = result.getResponse();
						String res = response.getContentAsString();
						
						logger.debug("================== " + res + " ==================");
						assertTrue(res.contains("SUCCESS"));
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}

	@Test
	@Order(2)
	public void demo_table_조회_by_id_테스트() {
		logger.debug("demo_table_조회_by_id_테스트");
		String url = "/selectById.json?id=1";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(result -> {
						MockHttpServletResponse response = result.getResponse();
						String res = response.getContentAsString();
						
						logger.debug("================== " + res + " ==================");
						assertTrue(res.contains("SUCCESS"));
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
	
	@Test
	@Order(3)
	public void demo_table_insert_update_테스트() {
		logger.debug("demo_table_insert_update_테스트");
		String url = "/save.json";
		String content = "{\"id\":3, \"col\":\"333\", \"col2\":\"ccc\"}";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content))
					.andExpect(result -> {
						MockHttpServletResponse response = result.getResponse();
						String res = response.getContentAsString();
						
						logger.debug("================== " + res + " ==================");
						assertTrue(res.contains("SUCCESS"));
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
	
	@Test
	@Order(4)
	public void demo_table_delete_테스트() {
		logger.debug("demo_table_delete_테스트");
		String url = "/deleteById.json?id=3";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(result -> {
						MockHttpServletResponse response = result.getResponse();
						String res = response.getContentAsString();
						
						logger.debug("================== " + res + " ==================");
						assertTrue(res.contains("SUCCESS"));
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
}
