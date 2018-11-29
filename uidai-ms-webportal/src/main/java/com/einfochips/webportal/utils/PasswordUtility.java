package com.einfochips.webportal.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtility {

	// Default constructor
	private PasswordUtility() {
		// Empty constructor
	}

	// Method will encode the password
	static public String passwordEncoder(String password) {
		// Initialized PasswordEncoder object
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	static public Boolean matchPassword(String password, String confirmPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, confirmPassword);
	}

}
