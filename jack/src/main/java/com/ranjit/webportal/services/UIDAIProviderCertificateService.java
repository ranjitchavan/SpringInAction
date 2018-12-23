package com.einfochips.webportal.services;

import java.util.List;

import com.einfochips.webportal.domain.UidaiPublicCertificate;
import com.einfochips.webportal.dto.UidaiPublicCertificateDTO;

public interface UIDAIProviderCertificateService {

	/**
	 * used to add the UIDAI certificate
	 * 
	 * @param uidaiPublicCertificate
	 * @return
	 */
	public UidaiPublicCertificate save(UidaiPublicCertificate uidaiPublicCertificate);

	/**
	 * List the certificates from database
	 * 
	 * @return
	 */
	public List<UidaiPublicCertificateDTO> getAllCert();

	/**
	 * This method used to delete the certificate from database
	 * 
	 * @param uidaiPublicCertificate
	 */
	public void delete(UidaiPublicCertificate uidaiPublicCertificate);
}
