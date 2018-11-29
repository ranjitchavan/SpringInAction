package com.einfochips.webportal.repositories;

import org.springframework.data.repository.CrudRepository;

import com.einfochips.webportal.domain.CustomerMaster;

/**
 * @author akash.shinde
 *
 */
public interface CustomerMasterRepository
		extends CrudRepository<CustomerMaster, Integer> {
	public CustomerMaster findByCustomerName(String customerName);
}