package com.einfochips.webportal.security;

// This is for Jwt
public class AuthenticationResponse extends ModelBase {

	private static final long serialVersionUID = -6624726180748515507L;

	private String token;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
