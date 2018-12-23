package com.einfochips.webportal.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	// Assign SESSION_TIMEOUT_IN_SECONDS to 60
	public static final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 1;

	// Validated credential
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
	}
}
