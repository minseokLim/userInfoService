package com.mslim.userinfoservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mslim.userinfoservice.dao.UserInfoDAO;
import com.mslim.userinfoservice.vo.UserInfoVO;

@Service
@Transactional
public class UserInfoService {

	@Autowired
	private UserInfoDAO dao;
	
	public UserInfoVO getUserInfo(UserInfoVO userInfoVO) {
		UserInfoVO foundUser = dao.findById(userInfoVO.getUserID()).orElse(new UserInfoVO());
		
		if(foundUser.confirmPW(userInfoVO.getRawPassword())) {
			foundUser.clearPW();
			return foundUser;
		}
		
		return null;
	}
	
	public boolean registerUser(UserInfoVO userInfoVO) {
		
		// check if same id is already used
		if(dao.findById(userInfoVO.getUserID()).isPresent()) {
			return false;
		}
		
		userInfoVO.setEncodedPW();
		dao.save(userInfoVO);	
		return true;
	}
}
