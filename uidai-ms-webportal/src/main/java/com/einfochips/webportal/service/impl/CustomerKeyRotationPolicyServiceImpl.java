package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.CustomerKeyRotationPolicy;
import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.dto.CustomerKeyRotationPolicyDto;
import com.einfochips.webportal.repositories.CustomerKeyRotationPolicyRepository;
import com.einfochips.webportal.repositories.CustomerMasterRepository;
import com.einfochips.webportal.services.CustomerKeyRotationPolicyService;
import com.einfochips.webportal.utility.EntityDTOBeanUtils;
import com.einfochips.webportal.utils.UserTokenParser;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public class CustomerKeyRotationPolicyServiceImpl
		implements CustomerKeyRotationPolicyService {

	@Autowired
	private CustomerKeyRotationPolicyRepository customerKeyRotationPolicyRepository;

	@Autowired
	private CustomerMasterRepository customerMasterRepository;

	@Override
	public List<CustomerKeyRotationPolicy> getAllCustomerKeyRotationPolicies() {
		List<CustomerKeyRotationPolicy> customerKeyRotationPolicyList = new ArrayList<>();
		customerKeyRotationPolicyRepository
				.findAllByOrderByCustomerKeyRotationPolicyIdDesc()
				.forEach(customerKeyRotationPolicyList::add);
		return customerKeyRotationPolicyList;
	}

	@Override
	public CustomerKeyRotationPolicy createCustomerKeyRotationPolicy(
			CustomerKeyRotationPolicy customerKeyRotationPolicy,
			HttpServletRequest httpServletRequest) {
		setUser(customerKeyRotationPolicy, httpServletRequest);
		Date date = new Date();
		customerKeyRotationPolicy.setCreationTime(date);
		customerKeyRotationPolicy.setUpdationTime(date);
		return customerKeyRotationPolicyRepository.save(customerKeyRotationPolicy);
	}

	@Override
	public CustomerKeyRotationPolicy updateCustomerKeyRotationPolicy(Integer id,
			CustomerKeyRotationPolicy customerKeyRotationPolicy, Integer userId) {
		CustomerKeyRotationPolicy updatedCustomerKeyRotationPolicy = null;
		CustomerKeyRotationPolicy existingCustomerKeyRotationPolicy = customerKeyRotationPolicyRepository
				.findOne(id);
		if (existingCustomerKeyRotationPolicy != null) {
			EntityDTOBeanUtils.copyNotNullProperties(existingCustomerKeyRotationPolicy,
					customerKeyRotationPolicy);
			existingCustomerKeyRotationPolicy.setCreatedBy(userId);
			existingCustomerKeyRotationPolicy.setUpdationTime(new Date());
			updatedCustomerKeyRotationPolicy = customerKeyRotationPolicyRepository
					.save(existingCustomerKeyRotationPolicy);
		}
		return updatedCustomerKeyRotationPolicy;
	}

	private void setUser(CustomerKeyRotationPolicy customerKeyRotationPolicy,
			HttpServletRequest httpServletRequest) {
		// set user
		Integer userId = UserTokenParser.getUserIdFromToken(httpServletRequest);
		if (userId != null) {
			customerKeyRotationPolicy.setCreatedBy(userId);
		}

	}

	@Override
	public boolean deleteCustomerKeyRotationPolicy(Integer id) {
		boolean isDeleted = false;
		CustomerKeyRotationPolicy customerKeyRotationPolicy = customerKeyRotationPolicyRepository
				.findOne(id);
		if (customerKeyRotationPolicy != null) {
			customerKeyRotationPolicyRepository.delete(customerKeyRotationPolicy);
			isDeleted = true;
		}
		return isDeleted;
	}

	@Override
	public CustomerKeyRotationPolicyDto getCustomerKeyRotationPolicy(Integer id) {
		CustomerKeyRotationPolicy customerKeyRotationPolicy = customerKeyRotationPolicyRepository
				.findOne(id);
		CustomerMaster customerMaster = customerMasterRepository
				.findOne(customerKeyRotationPolicy.getCustomerMasterId());
		return prepareResponse(customerKeyRotationPolicy, customerMaster);
	}

	private CustomerKeyRotationPolicyDto prepareResponse(
			CustomerKeyRotationPolicy customerKeyRotationPolicy,
			CustomerMaster customerMaster) {
		CustomerKeyRotationPolicyDto customerKeyRotationPolicyDto = new CustomerKeyRotationPolicyDto();
		customerKeyRotationPolicyDto
				.setCustomerKeyRotationPolicy(customerKeyRotationPolicy);
		if (customerMaster != null) {
			customerKeyRotationPolicyDto
					.setCustomerName(customerMaster.getCustomerName());
		}
		return customerKeyRotationPolicyDto;
	}

}
