package com.einfochips.webportal.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.CustomerKeyRotationPolicy;
import com.einfochips.webportal.dto.CustomerKeyRotationPolicyDto;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public interface CustomerKeyRotationPolicyService {

	/**
	 * This method is used to get all customer-wise configured key rotation policies.
	 * 
	 * @return
	 */
	List<CustomerKeyRotationPolicy> getAllCustomerKeyRotationPolicies();

	/**
	 * This method is used to get specific customer key rotation policy.
	 * 
	 * @param id
	 * @return
	 */
	CustomerKeyRotationPolicyDto getCustomerKeyRotationPolicy(Integer id);

	/**
	 * This method is used to persist customer-wise key rotation policy configuration to
	 * DB.
	 * 
	 * @param customerKeyRotationPolicy
	 * @param httpServletRequest
	 * @return
	 */
	CustomerKeyRotationPolicy createCustomerKeyRotationPolicy(
			CustomerKeyRotationPolicy customerKeyRotationPolicy,
			HttpServletRequest httpServletRequest);

	/**
	 * This method is used to update existing key rotation policy configuration of a
	 * customer.
	 * 
	 * @param id
	 * @param customerKeyRotationPolicy
	 * @param userId
	 * @return
	 */
	CustomerKeyRotationPolicy updateCustomerKeyRotationPolicy(Integer id,
			CustomerKeyRotationPolicy customerKeyRotationPolicy, Integer userId);

	/**
	 * This method is used to remove specific customer key rotation policy information
	 * from system.
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteCustomerKeyRotationPolicy(Integer id);

}
