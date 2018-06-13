package com.example.demo.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class WebLayerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService service;

	@Ignore
	@Test
	public void testGetUserList() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Ignore
	@Test
	public void testCreateUser() throws Exception {
		JSONObject jsonData = new JSONObject();
		jsonData.put("phone", "13333333338");
		jsonData.put("area", "郑州");
		jsonData.put("password", "123456");
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.toString())).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Ignore
	@Test
	public void testUploadPhoto() throws Exception {

		MockMultipartFile file = new MockMultipartFile("photo", "filename.txt", "text/plain",
				"content in file1".getBytes());
		mvc.perform(
				MockMvcRequestBuilders.multipart("/users/photos").file("file", file.getBytes()).param("name", "test"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

	}

	@Ignore
	@Test
	public void testUploadPhotos() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("photo", "file1.txt", "text/plain",
				"content in file1".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("photo", "file2.txt", "text/plain",
				"content in file2".getBytes());
		MockMultipartFile[] files = { file1, file2 };
		mvc.perform(MockMvcRequestBuilders.multipart("/users/photos").requestAttr("file", files).param("name",
				"test")).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

	}
}
