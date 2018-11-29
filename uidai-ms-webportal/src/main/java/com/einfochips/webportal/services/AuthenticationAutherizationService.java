package com.einfochips.webportal.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.einfochips.webportal.domain.Users;

public interface AuthenticationAutherizationService {

	// Method will validate login details
	boolean getLoginDetails(String userName, String password);

	// Method will load user details based on userName
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	// Method will get farmer details based on userName
	Users getLoginUserInfo(String userName);

}
