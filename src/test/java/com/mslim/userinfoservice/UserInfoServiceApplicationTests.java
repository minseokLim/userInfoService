package com.mslim.userinfoservice;import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mslim.userinfoservice.vo.Auth;
import com.mslim.userinfoservice.vo.UserInfoVO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserInfoServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoServiceApplicationTests {

	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@Before
	public void before() {
		headers.add("Authorization", createHttpAuthenticationHeaderValue("admin", "1111"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void registerUserAndLoginTest() {
		String userId = "mslim";
		UserInfoVO userInfo = new UserInfoVO(userId, "minseokLim", "111", null, Auth.USER);
		userInfo.setEncodedPW();
		
		ResponseEntity<String> response1 = restTemplate.exchange(createURIWithPort("/users"), HttpMethod.POST, 
							  								 new HttpEntity<UserInfoVO>(userInfo, headers), String.class);
		
		String actual = response1.getHeaders().getLocation().toString();
		assertEquals(createURIWithPort("/users/" + userId), actual);
		
		userInfo = new UserInfoVO(userId, null, "111", null, null);
		ResponseEntity<UserInfoVO> response2 = restTemplate.exchange(createURIWithPort("/login"), HttpMethod.POST, new HttpEntity<>(userInfo, headers), UserInfoVO.class);
		
		assertTrue(response2.getBody().getUserID().equals("mslim"));
	}
	
	private String createHttpAuthenticationHeaderValue(String userId, String password) {
		String auth = userId + ":" + password;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(encodedAuth);
	}
	
	private String createURIWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
