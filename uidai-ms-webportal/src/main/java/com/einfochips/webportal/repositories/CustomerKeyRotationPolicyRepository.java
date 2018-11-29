package com.einfochips.webportal.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.CustomerKeyRotationPolicy;

/**
 * Repository for customer wise key rotation policy configuration.
 * 
 * @author asra.shaikh
 *
 */
@Repository
public interface CustomerKeyRotationPolicyRepository
		extends CrudRepository<CustomerKeyRotationPolicy, Integer> {

	/**
	 * Get customer wise key rotation policies in LIFO order of their creation.
	 * 
	 * @return
	 */
	public List<CustomerKeyRotationPolicy> findAllByOrderByCustomerKeyRotationPolicyIdDesc();

}
