package com.einfochips.webportal.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	// To get value of token header
	@Value("${uidai.token.header}")
	private String tokenHeader;

	// Inject object of TokenUtils
	@Autowired
	private TokenUtils tokenUtils;

	// Inject object of UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	// Default constructor
	public AuthenticationTokenFilter() {
		// Empty Constructor
	}

	// This method will do filter
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authToken = request.getHeader(this.tokenHeader);
		String username = tokenUtils.getUsernameFromToken(authToken);
		if (username != null
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			// Load user details
			UserDetails userDetails = this.userDetailsService
					.loadUserByUsername(username);
			if (tokenUtils.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}
