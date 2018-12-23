package com.einfochips.webportal.services;

import java.util.List;

import com.einfochips.webportal.domain.DeviceProviderCertificate;

public interface DeviceProviderCertificateService {

	/**
	 * Used to save the certificate into database
	 * 
	 * @param dpcList
	 * @return
	 */
	public List<DeviceProviderCertificate> save(
			List<DeviceProviderCertificate> dpcList);

	/**
	 * 
	 * Used to return list of device provider certificate
	 * @return
	 */
	public List<DeviceProviderCertificate> getDeviceProviderCertificate();

	/**
	 * @param id
	 * @return
	 */
	public DeviceProviderCertificate getDeviceProviderById(Integer id);

	/**
	 * @param customerMasterId
	 * @return
	 */
	public List<DeviceProviderCertificate> findCustomerByCustomerId(
			Integer customerMasterId);
	
	/**
	 * @param 
	 * @return dpId
	 */
	public List<String> getDpId();

}
