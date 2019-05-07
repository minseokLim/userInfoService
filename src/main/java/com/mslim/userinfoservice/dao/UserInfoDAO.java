package com.mslim.userinfoservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mslim.userinfoservice.vo.UserInfoVO;

public interface UserInfoDAO extends JpaRepository<UserInfoVO, String>{

}
