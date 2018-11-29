package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.UidaiPublicCertificate;
import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.dto.UidaiPublicCertificateDTO;
import com.einfochips.webportal.repositories.UIDAIProviderCertificateRepository;
import com.einfochips.webportal.services.UIDAIProviderCertificateService;
import com.einfochips.webportal.services.UserService;

/**
 * @author akash.shinde
 *
 */
@Service
public class UIDAIProviderCertificateServiceImpl
		implements UIDAIProviderCertificateService {

	@Autowired
	UIDAIProviderCertificateRepository uidaiProviderCertificateRepository;

	@Autowired
	UserService userService;

	@Override
	public UidaiPublicCertificate save(UidaiPublicCertificate uidaiPublicCertificate) {
		return uidaiProviderCertificateRepository.save(uidaiPublicCertificate);
	}

	@Override
	public List<UidaiPublicCertificateDTO> getAllCert() {

		List<UidaiPublicCertificateDTO> list = new ArrayList<>();
		for (UidaiPublicCertificate p : uidaiProviderCertificateRepository.findAll()) {
			UidaiPublicCertificateDTO uidaiPublicCertificate = new UidaiPublicCertificateDTO();
			uidaiPublicCertificate.setCertificateType(p.getCertificateType());
			uidaiPublicCertificate.setUidaiCertificateId(p.getUidaiCertificateId());
			// uidaiPublicCertificate.setCreatedBy(p.getCreatedBy());

			if (p.getCreatedBy() != 0) {
				Users uObject = userService.getUser(p.getCreatedBy());
				if (uObject != null) {
					uidaiPublicCertificate.setCreatedBy(
							uObject.getFirstName() + " " + uObject.getLastName());
				}
			}

			uidaiPublicCertificate.setExpiryDate(p.getExpiryDate());
			uidaiPublicCertificate.setUpdationTime(p.getUpdationTime());
			uidaiPublicCertificate.setFromDate(p.getFromDate());
			list.add(uidaiPublicCertificate);

		}

		return list;
	}

	@Override
	public void delete(UidaiPublicCertificate uidaiPublicCertificate) {
		uidaiProviderCertificateRepository
				.delete(uidaiPublicCertificate.getUidaiCertificateId());
		;
	}

}
