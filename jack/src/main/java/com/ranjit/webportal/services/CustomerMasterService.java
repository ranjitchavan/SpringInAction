package com.einfochips.webportal.services;

import java.util.List;

import com.einfochips.webportal.domain.CustomerMaster;

public interface CustomerMasterService {
	public List<CustomerMaster> getAllCustomers();

	public CustomerMaster getCustomer(int id);

	public void addCustomer(CustomerMaster master);

	public CustomerMaster isCustomerPresent(String customerName);

	public CustomerMaster updateCustomer(CustomerMaster cusMaster);

}