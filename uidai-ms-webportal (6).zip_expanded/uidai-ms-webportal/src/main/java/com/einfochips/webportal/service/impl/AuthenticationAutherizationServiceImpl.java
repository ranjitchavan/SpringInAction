package com.einfochips.webportal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.repositories.UserRepository;
import com.einfochips.webportal.security.CerberusUser;
import com.einfochips.webportal.services.AuthenticationAutherizationService;

@Service
public class AuthenticationAutherizationServiceImpl
		implements AuthenticationAutherizationService, UserDetailsService {
	// Let's create logger instance
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationAutherizationServiceImpl.class);

	// Inject object of AuthenticationAutherizationDao
	@Autowired
	private UserRepository userRepository;

	// Initialized the default constructor
	public AuthenticationAutherizationServiceImpl() {
		// Empty Constructor
	}

	@Override
	public boolean getLoginDetails(String userName, String password) {
		Users u = userRepository.findByEmailAndPassword(userName, password);
		if (u == null) {
			return false;
		}
		else {
			return true;
		}

	}

	// This method will Authenticate user
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		LOGGER.info("In login user " + userName);
		// This method will find user details based on userName
		Users users = this.userRepository.findByEmail(userName);
		LOGGER.info("Users: " + users.getEmail());
		if (users != null) {
			LOGGER.info("In user  username found " + users.getEmail());
			return new CerberusUser((int) users.getUserId(), users.getEmail(),
					users.getPassword(), null, null);
		}
		return null;
	}

	// THis method will return user details based on userName
	@Override
	public Users getLoginUserInfo(String userName) {
		return userRepository.findByEmail(userName);
	}

}
