package com.einfochips.webportal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.domain.CustomerTokenMaster;
import com.einfochips.webportal.domain.TokenName;
import com.einfochips.webportal.dto.CustomerDto;
import com.einfochips.webportal.services.CustomerMasterService;
import com.einfochips.webportal.services.TokenMasterService;
import com.einfochips.webportal.utility.FieldValidator;

/**
 * 
 * @author asra.shaikh
 *
 */
@RestController
public class CustomerController {

	private static final Logger LOGGER = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerMasterService customerMasterService;

	@Autowired
	private TokenMasterService tokenMasterService;

	
	@RequestMapping(value = "/customer-names", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDto>> getCustomers() {
		ResponseEntity<List<CustomerDto>> response;
		List<CustomerMaster> customerMasterList = customerMasterService.getAllCustomers();
		List<CustomerDto> customerDtoList = convertToDto(customerMasterList);
		response = new ResponseEntity<>(customerDtoList, HttpStatus.OK);
		return response;
	}

	
	@RequestMapping(value = "/get-token-names", method = RequestMethod.GET)
	public ResponseEntity<List<TokenName>> getTokenNames() {
		ResponseEntity<List<TokenName>> response;
		List<TokenName> tokenMasterList = tokenMasterService.getAllTokens();

		response = new ResponseEntity<>(tokenMasterList, HttpStatus.OK);
		return response;
	}

	private List<CustomerDto> convertToDto(List<CustomerMaster> customerMasterList) {
		List<CustomerDto> customerDtoList = null;
		if (!FieldValidator.isNull(customerMasterList)) {
			customerDtoList = new ArrayList<>();
			CustomerDto customerDto;
			for (CustomerMaster customerMaster : customerMasterList) {
				customerDto = new CustomerDto(customerMaster.getCustomerMasterId(),
						customerMaster.getCustomerName(),
						customerMaster.getTokenNameId());
				customerDtoList.add(customerDto);
			}
		}
		return customerDtoList;
	}

	/**
	 * @param customerMaster
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add-new-customer", method = RequestMethod.POST)
	public ResponseEntity<CustomerMaster> addNewCustomer(
			@RequestBody CustomerTokenMaster customerTokenMaster,
			HttpServletRequest request) {
		ResponseEntity<CustomerMaster> response = null;

		CustomerMaster customerMaster = new CustomerMaster();
		customerMaster.setCustomerName(customerTokenMaster.getCustomerName());

		TokenName name = new TokenName();
		name.setTokenNameId(customerTokenMaster.getTokenNameId());
		customerMaster.setTokenNameId(name);

		LOGGER.info(customerMaster.toString());
		LOGGER.info("--------------add customer start------------");
		CustomerMaster cusMaster = customerMasterService
				.isCustomerPresent(customerMaster.getCustomerName());
		if (cusMaster != null) {
			LOGGER.info("--------------customer already present in database------------");
			response = new ResponseEntity<>(cusMaster, HttpStatus.FOUND);
		}
		else {
			customerMasterService.addCustomer(customerMaster);
			LOGGER.info("--------------added new customer in database------------");
			response = new ResponseEntity<>(cusMaster, HttpStatus.OK);
		}

		LOGGER.info("--------------add customer end------------");
		return response;
	}

	/**
	 * @param customerMaster
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update-customer", method = RequestMethod.PUT)
	public ResponseEntity<CustomerMaster> updateCustomer(
			@RequestBody CustomerTokenMaster customerTokenMaster,
			HttpServletRequest request) {
		ResponseEntity<CustomerMaster> response = null;

		CustomerMaster customerMaster = new CustomerMaster();
		customerMaster.setCustomerName(customerTokenMaster.getCustomerName());

		TokenName name = new TokenName();
		name.setTokenNameId(customerTokenMaster.getTokenNameId());

		customerMaster.setTokenNameId(name);
		customerMaster.setCustomerMasterId(customerTokenMaster.getCustomerNameId());

		LOGGER.info(customerMaster.toString());
		LOGGER.info("--------------update customer start------------");
		CustomerMaster cusMaster = customerMasterService
				.getCustomer(customerMaster.getCustomerMasterId());
		if (cusMaster != null) {

			cusMaster.setCustomerName(customerMaster.getCustomerName());
			cusMaster.setTokenNameId(customerMaster.getTokenNameId());
			System.out.println("-------------------------"
					+ customerMaster.getTokenNameId() + "---------------------------");
			LOGGER.info(
					"--------------Updating customer from database start------------");
			// cusMaster.setDeviceMaster(null);
			cusMaster = customerMasterService.updateCustomer(cusMaster);
			LOGGER.info("--------------Updating customer from database end ------------");

			response = new ResponseEntity<>(cusMaster, HttpStatus.OK);
		}
		else {
			System.out.println(
					"---------------------not found-----------------------------");
			response = new ResponseEntity<>(cusMaster, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(
				"--------------update customer end !! Returning result !! ------------");
		return response;
	}
}