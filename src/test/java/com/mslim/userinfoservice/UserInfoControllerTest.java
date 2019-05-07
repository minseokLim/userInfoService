package com.mslim.userinfoservice;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mslim.userinfoservice.service.UserInfoService;
import com.mslim.userinfoservice.vo.Auth;
import com.mslim.userinfoservice.vo.UserInfoVO;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserInfoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserInfoService userInfoService;
	
	@Test
	public void registerUserTest() throws Exception {
		
		String userInfoJSON = "{\"userID\" : \"mslim\", \"userName\" : \"minseokLim\", \"rawPassword\" : \"123\", \"auth\" : \"USER\"}";
		
		Mockito.when(userInfoService.registerUser(Mockito.any(UserInfoVO.class))).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .content(userInfoJSON).contentType(MediaType.APPLICATION_JSON)
															  .header("Authorization", createHttpAuthenticationHeaderValue("admin", "1111"));
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/users/mslim", response.getHeader(HttpHeaders.LOCATION));
	}
	
	@Test
	public void loginTest() throws Exception {
		
		UserInfoVO userInfo = new UserInfoVO("mslim", "minseokLim", null, null, Auth.USER);
		String userInfoJSON = "{\"userID\" : \"mslim\", \"rawPassword\" : \"123\"}";
		
		Mockito.when(userInfoService.getUserInfo(Mockito.any(UserInfoVO.class))).thenReturn(userInfo);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
															  .content(userInfoJSON).contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .header("Authorization", createHttpAuthenticationHeaderValue("admin", "1111"));
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		String expected = "{userID : mslim, userName : minseokLim, auth : USER}";
		
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}
	
	private String createHttpAuthenticationHeaderValue(String userId, String password) {
		String auth = userId + ":" + password;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(encodedAuth);
	}
}
