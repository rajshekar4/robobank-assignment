package com.rabobank.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.robobank.controller.RobobankController;
import com.robobank.service.ExtractService;

@RunWith(SpringRunner.class)
@WebMvcTest
class RobobankControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ExtractService extractService;
	
	@InjectMocks
	private RobobankController robobankController;
	
	private InputStream is;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(robobankController).build();
	}

	@Test
	void testProcess() throws IOException, Exception 
	{
		Map<Integer,String> records= new HashMap<Integer, String>();
		records.put(112806,"duplicate record found in the statement");
		is = robobankController.getClass().getClassLoader().getResourceAsStream("records.csv");
		 MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.csv", "multipart/form-data", is);
		when(extractService.processStatement(mockMultipartFile)).thenReturn(records);
		 mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/process")
	                .contentType(MediaType.MULTIPART_FORM_DATA).content(mockMultipartFile.getBytes()))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest())
	                .andDo(MockMvcResultHandlers.print());	
		 
		 }


	@Test
	void testObjectToString() 
	{
		Map<Integer,String> records= new HashMap<Integer, String>();
		records.put(112806,"duplicate record found in the statement");
		String str = robobankController.ObjectToString(records);
		assertEquals("","{\"112806\":\"duplicate record found in the statement\"}",str);
	}

}
