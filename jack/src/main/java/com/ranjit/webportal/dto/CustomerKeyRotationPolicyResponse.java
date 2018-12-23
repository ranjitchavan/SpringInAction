package com.einfochips.webportal.dto;

import java.util.List;
import java.util.Map;

/**
 * Class defining API response of customer wise key rotation policy configuration.
 * 
 * @author asra.shaikh
 *
 */
public class CustomerKeyRotationPolicyResponse {

	private List<CustomerKeyRotationPolicyDto> customerKeyRotationPolicyDtoList;
	private Map<Integer, String> customerMasterMap;

	/**
	 * Constructor to customer wise create key rotation policy response.
	 * 
	 * @param customerKeyRotationPolicyDtoList
	 * @param customerMasterMap
	 */
	public CustomerKeyRotationPolicyResponse(
			List<CustomerKeyRotationPolicyDto> customerKeyRotationPolicyDtoList,
			Map<Integer, String> customerMasterMap) {
		super();
		this.customerKeyRotationPolicyDtoList = customerKeyRotationPolicyDtoList;
		this.customerMasterMap = customerMasterMap;
	}

	public List<CustomerKeyRotationPolicyDto> getCustomerKeyRotationPolicyDtoList() {
		return customerKeyRotationPolicyDtoList;
	}

	public void setCustomerKeyRotationPolicyDtoList(
			List<CustomerKeyRotationPolicyDto> customerKeyRotationPolicyDtoList) {
		this.customerKeyRotationPolicyDtoList = customerKeyRotationPolicyDtoList;
	}

	public Map<Integer, String> getCustomerMasterMap() {
		return customerMasterMap;
	}

	public void setCustomerMasterMap(Map<Integer, String> customerMasterMap) {
		this.customerMasterMap = customerMasterMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"CustomerKeyRotationPolicyResponse [customerKeyRotationPolicyDtoList=")
				.append(customerKeyRotationPolicyDtoList).append("]");
		return builder.toString();
	}

}
