package com.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
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
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("DemoController 에러 테스트 클래스 ")
class DemoControllerErrorTest {

	private static final Logger logger = LoggerFactory.getLogger(DemoControllerErrorTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DemoService demoServcie;
	
	@Test
	@Order(1)
	public void demo_table_조회_by_id_파라미터_누락_에러_테스트() {
		logger.debug("demo_table_조회_by_id_파라미터_누락_에러_테스트");
		String url = "/selectById.json?";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError())
					.andExpect(result -> {
						String expClsNm = result.getResolvedException().getClass().getCanonicalName();
						logger.debug(expClsNm);
						
						assertEquals("org.springframework.web.bind.MissingServletRequestParameterException", expClsNm);
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
	
	@Test
	@Order(2)
	public void demo_table_조회_by_id_파라미터_null_에러_테스트() {
		logger.debug("demo_table_조회_by_id_파라미터_null_에러_테스트");
		String url = "/selectById.json?id=";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError())
					.andExpect(result -> {
						String expClsNm = result.getResolvedException().getClass().getCanonicalName();
						logger.debug(expClsNm);
						
						assertEquals("NullPointerException", expClsNm);
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
	
	@Test
	@Order(3)
	public void demo_table_삭제_누락_에러_테스트() {
		logger.debug("demo_table_삭제_누락_에러_테스트");
		String url = "/deleteById.json?";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError())
					.andExpect(result -> {
						String expClsNm = result.getResolvedException().getClass().getCanonicalName();
						logger.debug(expClsNm);
						
						assertEquals("org.springframework.web.bind.MissingServletRequestParameterException", expClsNm);
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
	
	@Test
	@Order(4)
	public void demo_table_삭제_파라미터_null_에러_테스트() {
		logger.debug("demo_table_삭제_파라미터_null_에러_테스트");
		String url = "/deleteById.json?id=";
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete(url)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError())
					.andExpect(result -> {
						String expClsNm = result.getResolvedException().getClass().getCanonicalName();
						logger.debug(expClsNm);
						
						assertEquals("java.lang.NullPointerException", expClsNm);
					});
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}
}
