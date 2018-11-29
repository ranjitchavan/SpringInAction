package com.einfochips.webportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.KeyRotationPolicy;

/**
 * Repository for key rotation policy.
 * 
 * @author asra.shaikh
 *
 */
@Repository
public interface KeyRotationPolicyRepository
		extends JpaRepository<KeyRotationPolicy, Integer> {
}