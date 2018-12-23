package com.einfochips.webportal.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.CustomerKeyRotationPolicy;
import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.dto.CustomerKeyRotationPolicyDto;
import com.einfochips.webportal.dto.CustomerKeyRotationPolicyResponse;
import com.einfochips.webportal.services.CustomerKeyRotationPolicyService;
import com.einfochips.webportal.services.CustomerMasterService;
import com.einfochips.webportal.services.UserService;
import com.einfochips.webportal.utility.FieldValidator;
import com.einfochips.webportal.utils.UserTokenParser;

/**
 * Class containing APIs(exposed) for customer wise key rotation policy configuration.
 * 
 * @author asra.shaikh
 *
 */
@RestController
public class CustomerKeyRotationPolicyController {

	@Autowired
	private CustomerKeyRotationPolicyService customerKeyRotationPolicyService;

	@Autowired
	private CustomerMasterService customerMasterService;

	@Autowired
	private UserService userService;

	/**
	 * API to get customer-wise rotation key policies. It returns in response: 1)list of
	 * configured policies 2)Map of customer(s) for which rotation policy is yet to be
	 * configured.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/customer-keyrotation-policies", method = RequestMethod.GET)
	public ResponseEntity<CustomerKeyRotationPolicyResponse> getPageContent() {
		ResponseEntity<CustomerKeyRotationPolicyResponse> response;

		Map<Integer, String> customerMasterMap = getCustomers();
		List<CustomerKeyRotationPolicyDto> customerKeyRotationPolicyDtoList = getKeyRotationPolicies(
				customerMasterMap);
		response = new ResponseEntity<>(new CustomerKeyRotationPolicyResponse(
				customerKeyRotationPolicyDtoList, customerMasterMap), HttpStatus.OK);

		return response;
	}

	/**
	 * API to get specific key rotation policy information of a customer.
	 * 
	 * @param customerKeyRotationPolicyId
	 * @return
	 */
	@RequestMapping(value = "/customer-keyrotation-policy/{customerKeyRotationPolicyId}", method = RequestMethod.GET)
	public ResponseEntity<CustomerKeyRotationPolicyDto> getCustomerKeyRotationPolicy(
			@PathVariable("customerKeyRotationPolicyId") Integer customerKeyRotationPolicyId) {
		ResponseEntity<CustomerKeyRotationPolicyDto> response;
		CustomerKeyRotationPolicyDto customerKeyRotationPolicyDto = customerKeyRotationPolicyService
				.getCustomerKeyRotationPolicy(customerKeyRotationPolicyId);
		if (customerKeyRotationPolicyDto != null) {
			response = new ResponseEntity<>(customerKeyRotationPolicyDto, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

		return response;
	}

	private Map<Integer, String> getCustomers() {
		List<CustomerMaster> customerMasterList = customerMasterService.getAllCustomers();
		Map<Integer, String> customerMasterMap = new HashMap<>();
		if ((customerMasterList != null) && (!customerMasterList.isEmpty())) {
			for (CustomerMaster customerMaster : customerMasterList) {
				customerMasterMap.put(customerMaster.getCustomerMasterId(),
						customerMaster.getCustomerName());
			}
		}
		return customerMasterMap;
	}

	private List<CustomerKeyRotationPolicyDto> getKeyRotationPolicies(
			Map<Integer, String> customerMasterMap) {
		List<CustomerKeyRotationPolicy> customerKeyRotationPolicyList = customerKeyRotationPolicyService
				.getAllCustomerKeyRotationPolicies();
		List<CustomerKeyRotationPolicyDto> customerKeyRotationPolicyDtoList = new ArrayList<>();
		if ((customerKeyRotationPolicyList != null)
				&& (!customerKeyRotationPolicyList.isEmpty())) {
			for (CustomerKeyRotationPolicy customerKeyRotationPolicy : customerKeyRotationPolicyList) {
				Users user = userService
						.getUser(customerKeyRotationPolicy.getCreatedBy());
				String userName = (user != null)
						? user.getFirstName() + " " + user.getLastName() : "";

				int customerMasterId = customerKeyRotationPolicy.getCustomerMasterId();
				String customerName = customerMasterMap.get(customerMasterId);
				customerName = FieldValidator.getNonNullString(customerName);
				customerMasterMap.remove(customerMasterId);

				CustomerKeyRotationPolicyDto customerKeyRotationPolicyDto = new CustomerKeyRotationPolicyDto(
						customerKeyRotationPolicy, customerName, userName);
				customerKeyRotationPolicyDtoList.add(customerKeyRotationPolicyDto);
			}
		}
		return customerKeyRotationPolicyDtoList;
	}

	/**
	 * API to configure/save key rotation policy for a customer.
	 * 
	 * @param customerKeyRotationPolicy
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/customer-keyrotation-policy", method = RequestMethod.POST)
	public ResponseEntity<CustomerKeyRotationPolicy> createCustomerKeyRotationPolicy(
			@RequestBody CustomerKeyRotationPolicy customerKeyRotationPolicy,
			HttpServletRequest httpServletRequest) {

		ResponseEntity<CustomerKeyRotationPolicy> response = null;

		CustomerKeyRotationPolicy savedCustomerKeyRotationPolicy = customerKeyRotationPolicyService
				.createCustomerKeyRotationPolicy(customerKeyRotationPolicy,
						httpServletRequest);
		if (savedCustomerKeyRotationPolicy != null) {
			response = new ResponseEntity<>(savedCustomerKeyRotationPolicy,
					HttpStatus.OK);
		}

		return response;
	}

	/**
	 * API to update existing key rotation policy of a customer.
	 * 
	 * @param customerKeyRotationPolicyId
	 * @param customerKeyRotationPolicy
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/customer-keyrotation-policy/{customerKeyRotationPolicyId}", method = RequestMethod.PUT)
	public ResponseEntity<CustomerKeyRotationPolicy> updateCustomerKeyRotationPolicy(
			@PathVariable("customerKeyRotationPolicyId") Integer customerKeyRotationPolicyId,
			@RequestBody CustomerKeyRotationPolicy customerKeyRotationPolicy,
			HttpServletRequest httpServletRequest) {

		ResponseEntity<CustomerKeyRotationPolicy> response = null;
		Integer userId = UserTokenParser.getUserIdFromToken(httpServletRequest);
		CustomerKeyRotationPolicy updatedCustomerKeyRotationPolicy = customerKeyRotationPolicyService
				.updateCustomerKeyRotationPolicy(customerKeyRotationPolicyId,
						customerKeyRotationPolicy, userId);
		if (updatedCustomerKeyRotationPolicy != null) {
			response = new ResponseEntity<>(updatedCustomerKeyRotationPolicy,
					HttpStatus.OK);
		}
		return response;
	}

	/**
	 * API to remove existing key rotation policy of customer.
	 * 
	 * @param customerKeyRotationPolicyId
	 * @return
	 */
	@RequestMapping(value = "/customer-keyrotation-policy/{customerKeyRotationPolicyId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCustomerKeyRotationPolicy(
			@PathVariable("customerKeyRotationPolicyId") Integer customerKeyRotationPolicyId) {
		ResponseEntity<String> response;
		if (customerKeyRotationPolicyService
				.deleteCustomerKeyRotationPolicy(customerKeyRotationPolicyId)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return response;
	}
}