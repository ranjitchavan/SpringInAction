package com.einfochips.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.DeviceProviderCertificate;
import com.einfochips.webportal.repositories.DeviceProviderCertificateRepository;
import com.einfochips.webportal.services.DeviceProviderCertificateService;

/**
 * @author akash.shinde
 *
 */
@Service
public class DeviceProviderCertificateServiceImpl
		implements DeviceProviderCertificateService {

	@Autowired
	DeviceProviderCertificateRepository deviceProviderCertificateRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.einfochips.webportal.services.DeviceProviderCertificateService#save(com.
	 * einfochips.webportal.domain.DeviceProviderCertificate)
	 */
	public List<DeviceProviderCertificate> save(
			List<DeviceProviderCertificate> dpcList) {
		return deviceProviderCertificateRepository.save(dpcList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.einfochips.webportal.services.DeviceProviderCertificateService#
	 * getDeviceProviderCertificate()
	 */
	@Override
	public List<DeviceProviderCertificate> getDeviceProviderCertificate() {
		return deviceProviderCertificateRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.einfochips.webportal.services.DeviceProviderCertificateService#
	 * getDeviceProviderById(java.lang.Integer)
	 */
	@Override
	public DeviceProviderCertificate getDeviceProviderById(Integer id) {
		return deviceProviderCertificateRepository.findOne(id);
	}

	@Override
	public List<DeviceProviderCertificate> findCustomerByCustomerId(
			Integer customerMasterId) {
		return deviceProviderCertificateRepository
				.findByCustomerIdAndRevokedOrderByExpiryDateDesc(customerMasterId,
						(byte) 0);
	}

	@Override
	public List<String> getDpId() {
		return deviceProviderCertificateRepository.getDpId();
	}
}
