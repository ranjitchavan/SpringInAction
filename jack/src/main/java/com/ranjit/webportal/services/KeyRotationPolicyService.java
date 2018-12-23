package com.einfochips.webportal.services;

import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.KeyRotationPolicy;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public interface KeyRotationPolicyService {
	/**
	 * Get key rotation policy information
	 * 
	 * @return
	 */
	public KeyRotationPolicy getKeyRotationPolicy();

	/**
	 * Update key rotation policy
	 * 
	 * @param keyRotationId
	 * @param keyRotationPolicy
	 * @return
	 */
	public KeyRotationPolicy editKeyRotationPolicy(Integer keyRotationId,
			KeyRotationPolicy keyRotationPolicy);

}