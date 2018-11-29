package com.einfochips.webportal.utils;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// TODO:There are two purpose we used first one is allocation communication with UI and Jwt token 
@Component
public class GCCorsFilter extends OncePerRequestFilter {

	// Let's create logger instance
	private static final Logger LOGGER = LoggerFactory.getLogger(GCCorsFilter.class);

	// Set default value of tokenHeader
	@Value("${uidai.token.header}")
	private String tokenHeader;

	// Default constructor
	public GCCorsFilter() {
		// Empty Constructor
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept,X-Auth-Token");
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			LOGGER.info("Status OK");
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

}
