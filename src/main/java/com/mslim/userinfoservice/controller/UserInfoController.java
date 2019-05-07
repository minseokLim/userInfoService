package com.mslim.userinfoservice.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mslim.userinfoservice.service.UserInfoService;
import com.mslim.userinfoservice.vo.UserInfoVO;

@RestController
public class UserInfoController {

	@Autowired
	private UserInfoService service;
	
	@PostMapping("/login")
	public ResponseEntity<UserInfoVO> actionLogin(@RequestBody UserInfoVO userInfoVO) {
		return new ResponseEntity<UserInfoVO>(service.getUserInfo(userInfoVO), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> registerUser(@RequestBody UserInfoVO userInfoVO) {
		boolean result = service.registerUser(userInfoVO);
		
		if(!result) {
			return ResponseEntity.noContent().build();
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(userInfoVO.getUserID()).toUri();
		return ResponseEntity.created(location).build();
	}
}
