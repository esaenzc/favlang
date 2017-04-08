package com.tcook.github.favlag.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.tcook.github.favlag.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitControllerIntegrationTests {
	
	@Autowired
	private GitHubController ghcontroller;
	
	@Value("${error.invalid.user}")
	private String invalidUsername;
	
    private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		
		mockMvc = MockMvcBuilders.standaloneSetup(ghcontroller).setViewResolvers(viewResolver).build();
	}
	
	
	// Tests
	@Test
	public void testLoadIndexPageOK() throws Exception {	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/favlang");
		
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().size(2))	 	
 		.andExpect(MockMvcResultMatchers.model().attributeExists(Constants.LANGUAGES))
		.andExpect(MockMvcResultMatchers.view().name(Constants.INDEX))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testLoadIndexPageWithUserOK() throws Exception {	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/favlang?username=kioto");
		
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().size(2))
 		.andExpect(MockMvcResultMatchers.model().attributeExists(Constants.USERNAME))
 		.andExpect(MockMvcResultMatchers.model().attributeExists(Constants.LANGUAGES))
 		.andExpect(MockMvcResultMatchers.model().attribute(Constants.USERNAME, "kioto"))
		.andExpect(MockMvcResultMatchers.view().name(Constants.INDEX))
		.andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void testLoadIndexPageWithWrongUserOK() throws Exception {	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/favlang?username=32432aaa");
		
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().size(3))
 		.andExpect(MockMvcResultMatchers.model().attributeExists(Constants.USERNAME))
 		.andExpect(MockMvcResultMatchers.model().attributeExists(Constants.LANGUAGES))
 		.andExpect(MockMvcResultMatchers.model().attribute(Constants.ERROR, invalidUsername))
		.andExpect(MockMvcResultMatchers.view().name(Constants.INDEX))
		.andDo(MockMvcResultHandlers.print());
	}
	
	// This test could be launched in case of GitHub API problems (Connection refused, illegal arguments)
/*	@Test(expected = HttpClientErrorException.class)
	public void testNoUserFound() throws JsonProcessingException, IOException {	
		ghcontroller.index("whatever", new ExtendedModelMap ());
	}	
*/
	
}
