package com.einfochips.webportal.dto;

import com.einfochips.webportal.domain.CustomerKeyRotationPolicy;

/**
 * 
 * @author asra.shaikh
 *
 */
public class CustomerKeyRotationPolicyDto {

	private CustomerKeyRotationPolicy customerKeyRotationPolicy;
	private String customerName;
	private String userName;

	/**
	 * Parameterized construct used to construct DTO.
	 * 
	 * @param customerKeyRotationPolicy
	 * @param customerName
	 * @param userName
	 */
	public CustomerKeyRotationPolicyDto(
			CustomerKeyRotationPolicy customerKeyRotationPolicy, String customerName,
			String userName) {
		super();
		this.customerKeyRotationPolicy = customerKeyRotationPolicy;
		this.customerName = customerName;
		this.userName = userName;
	}

	public CustomerKeyRotationPolicyDto() {
		// default constructor
	}

	public CustomerKeyRotationPolicy getCustomerKeyRotationPolicy() {
		return customerKeyRotationPolicy;
	}

	public void setCustomerKeyRotationPolicy(
			CustomerKeyRotationPolicy customerKeyRotationPolicy) {
		this.customerKeyRotationPolicy = customerKeyRotationPolicy;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
