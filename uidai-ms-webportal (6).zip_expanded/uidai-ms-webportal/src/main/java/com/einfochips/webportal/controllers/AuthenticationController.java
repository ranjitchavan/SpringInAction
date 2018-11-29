package com.einfochips.webportal.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.dto.AuthenticationDto;
import com.einfochips.webportal.dto.LoginResponse;
import com.einfochips.webportal.security.TokenUtils;
import com.einfochips.webportal.services.AuthenticationAutherizationService;
import com.einfochips.webportal.services.UserService;

/**
 * @author akash.shinde This class used to authentication for the user
 */
@RestController
public class AuthenticationController extends BaseController {

	// Inject object of AuthenticationAutherizationService
	@Autowired
	private AuthenticationAutherizationService authenticationAutherizationService;

	// JWT Spring Security
	@Autowired
	private AuthenticationManager authenticationManager;

	// Inject object of TokenUtils
	@Autowired
	private TokenUtils tokenUtils;

	// Inject object of UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	// To get value of token header
	@Value("${uidai.token.header}")
	private String tokenHeader;

	// Let's create logger instance
	private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);

	// Initialized the default constructor
	public AuthenticationController() {
		// Empty Constructor
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> isAuthenticate(
			@RequestBody AuthenticationDto authenticationDto) {
		ResponseEntity<LoginResponse> response = null;
		try {
			Users users = authenticationAutherizationService
					.getLoginUserInfo(authenticationDto.getUserLoginId());

			LoginResponse loginResponse = new LoginResponse();
			if (users != null) {
				if (users.isActive()) {
					Authentication authentication = this.authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(
									authenticationDto.getUserLoginId(),
									authenticationDto.getUserPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					UserDetails userDetails = this.userDetailsService
							.loadUserByUsername(users.getEmail());
					String token = this.tokenUtils.generateToken(userDetails, users);
					// Json result set as
					loginResponse.setToken(token);
					loginResponse.setNewUser(users.isNewUser());
					users.setNewUser(false);
					userService.save(users);

					response = new ResponseEntity<>(loginResponse, HttpStatus.OK);
				}
				else {
					LOGGER.error("user inactive");
					response = new ResponseEntity<>(loginResponse, HttpStatus.LOCKED);
				}
			}
			else {
				response = new ResponseEntity<>(loginResponse, HttpStatus.NOT_FOUND);
			}

		}
		catch (BadCredentialsException e) {
			LOGGER.error("Authentication failure", e);
			// Return response 401 unauthorized
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		catch (Exception e) {
			LOGGER.error("Authentication failure", e);
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
