package com.mslim.userinfoservice.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_USER_INFO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
	
	@Id
	private String userID;
	
	private String userName;
	
	@Transient
	private String rawPassword;
	
	private String encodedPassword;
	
	private Auth auth;
	
	private static PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public void setEncodedPW() {
		encodedPassword = encoder.encode(rawPassword);
	}
	
	public boolean confirmPW(String inputPw) {
		return encoder.matches(inputPw, encodedPassword);
	}
	
	public void clearPW() {
		this.rawPassword = null;
		this.encodedPassword = null;
	}
}
