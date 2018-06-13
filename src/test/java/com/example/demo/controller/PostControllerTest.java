package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

	public static final String URL = "/users/";
	public static final String USER_INFO = "{\"phone\":\"18738136702\", \"password\":\"123456\", \"area\":\"郑州市二七区\"}";
	public static final String EXPECTED_RESULT = "{\"respCode\":\"200\", \"data\":{\"phone\":\"18738136702\", \"password\":\"123456\", \"area\":\"郑州市二七区\"}}";
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testCreateUserThenReturnSuccess() throws Exception {
		ResultActions resultActions = sendUserInfoByPost(USER_INFO);
		checkResultActions(EXPECTED_RESULT, resultActions);
	}
	
	private ResultActions sendUserInfoByPost(String userInfo) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders
				.post(URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userInfo));
	}
	
	private void checkResultActions(String expectedResult, ResultActions resultActions) throws Exception {
		resultActions.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedResult));
	}
}
