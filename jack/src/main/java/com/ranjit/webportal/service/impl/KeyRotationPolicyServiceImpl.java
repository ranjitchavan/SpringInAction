package com.einfochips.webportal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.KeyRotationPolicy;
import com.einfochips.webportal.repositories.KeyRotationPolicyRepository;
import com.einfochips.webportal.services.KeyRotationPolicyService;
import com.einfochips.webportal.utility.EntityDTOBeanUtils;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public class KeyRotationPolicyServiceImpl implements KeyRotationPolicyService {

	@Autowired
	private KeyRotationPolicyRepository keyRotationPolicyRepository;

	@Override
	public KeyRotationPolicy getKeyRotationPolicy() {
		KeyRotationPolicy keyRotationPolicy = null;
		Page<KeyRotationPolicy> keyRotationPolicyPage = keyRotationPolicyRepository
				.findAll(new PageRequest(0, 1, Direction.ASC, "keyRotationId"));
		List<KeyRotationPolicy> keyRotationPolicyList = keyRotationPolicyPage
				.getContent();
		if ((keyRotationPolicyList != null) && !(keyRotationPolicyList.isEmpty())) {
			keyRotationPolicy = keyRotationPolicyList.get(0);
		}
		return keyRotationPolicy;
	}

	@Override
	public KeyRotationPolicy editKeyRotationPolicy(Integer keyRotationId,
			KeyRotationPolicy keyRotationPolicy) {
		KeyRotationPolicy updatedKeyRotationPolicy = null;
		KeyRotationPolicy existingKeyRotationPolicy = keyRotationPolicyRepository
				.findOne(keyRotationId);
		if (existingKeyRotationPolicy != null) {
			EntityDTOBeanUtils.copyNotNullProperties(existingKeyRotationPolicy,
					keyRotationPolicy);
			existingKeyRotationPolicy.setUpdationTime(new Date());
			updatedKeyRotationPolicy = keyRotationPolicyRepository
					.save(existingKeyRotationPolicy);
		}
		return updatedKeyRotationPolicy;
	}
}