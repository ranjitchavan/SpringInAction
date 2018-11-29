package com.einfochips.webportal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.KeyRotationPolicy;
import com.einfochips.webportal.services.KeyRotationPolicyService;

/**
 * 
 * @author asra.shaikh
 *
 */
@RestController
public class KeyRotationPolicyController {
	/**
	 * 
	 */
	@Autowired
	private KeyRotationPolicyService keyRotationPolicyService;

	/**
	 * This method is used to get configured key rotation policy information from system.
	 * 
	 * @return
	 */

	@RequestMapping(value = "/key-rotation-policy", method = RequestMethod.GET)
	public ResponseEntity<KeyRotationPolicy> getKeyRotationPolicy() {
		ResponseEntity<KeyRotationPolicy> response;
		KeyRotationPolicy keyRotationPolicy = keyRotationPolicyService
				.getKeyRotationPolicy();
		if (keyRotationPolicy != null) {
			response = new ResponseEntity<>(keyRotationPolicy, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}

	/**
	 * This method is used to update key rotation policy information.
	 * 
	 * @param keyRotationId
	 * @param keyRotationPolicy
	 * @return
	 */

	@RequestMapping(value = "/key-rotation-policy/{keyRotationId}", method = RequestMethod.PUT)
	public ResponseEntity<KeyRotationPolicy> updateKeyRotationPolicy(
			@PathVariable("keyRotationId") Integer keyRotationId,
			@RequestBody KeyRotationPolicy keyRotationPolicy) {
		ResponseEntity<KeyRotationPolicy> response;
		KeyRotationPolicy updatedKeyRotationPolicy = keyRotationPolicyService
				.editKeyRotationPolicy(keyRotationId, keyRotationPolicy);
		if (updatedKeyRotationPolicy != null) {
			response = new ResponseEntity<>(updatedKeyRotationPolicy, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return response;
	}

}