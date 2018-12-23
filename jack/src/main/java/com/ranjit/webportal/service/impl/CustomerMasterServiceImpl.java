package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.repositories.CustomerMasterRepository;
import com.einfochips.webportal.services.CustomerMasterService;

@Service
public class CustomerMasterServiceImpl implements CustomerMasterService {
	@Autowired
	private CustomerMasterRepository customerMasterRepository;

	@Override
	public List<CustomerMaster> getAllCustomers() {
		List<CustomerMaster> customerMasterList = new ArrayList<>();
		customerMasterRepository.findAll().forEach(customerMasterList::add);
		return customerMasterList;
	}

	@Override
	public CustomerMaster getCustomer(int id) {
		return customerMasterRepository.findOne(id);
	}

	@Override
	public void addCustomer(CustomerMaster master) {
		customerMasterRepository.save(master);
	}

	@Override
	public CustomerMaster isCustomerPresent(String customerName) {
		return customerMasterRepository.findByCustomerName(customerName);
	}

	@Override
	public CustomerMaster updateCustomer(CustomerMaster cusMaster) {
		return customerMasterRepository.save(cusMaster);
	}
}
