package com.einfochips.webportal.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.einfochips.webportal.security.JWTParser;

import io.jsonwebtoken.Claims;

public final class UserTokenParser {

	// Let's create instance of logger
	private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenParser.class);

	// Default Constructor
	private UserTokenParser() {
		// Empty constructor
	}

	public static Integer getUserIdFromToken(HttpServletRequest request) {
		Claims body = JWTParser.tokenParser(request);
		LOGGER.info(body.toString());
		Integer userId = (Integer) body.get("id");
		LOGGER.info(userId + " userId");
		return userId;
	}
}
