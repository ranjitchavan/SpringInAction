package com.einfochips.webportal.dto;

public class AuthenticationDto {

	private String userLoginId;
	private String userPassword;
	private String role;
	private String securityQuestionAnswer;
	private String userNewPassword;

	public AuthenticationDto() {
		// Empty Constructor
	}

	public String getUserNewPassword() {
		return userNewPassword;
	}

	public void setUserNewPassword(String userNewPassword) {
		this.userNewPassword = userNewPassword;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AuthenticationDto [userLoginId=" + userLoginId + ", userPassword="
				+ userPassword + ", role=" + role + ", securityQuestionAnswer="
				+ securityQuestionAnswer + ", userNewPassword=" + userNewPassword + "]";
	}

}
