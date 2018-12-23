package com.einfochips.webportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.UidaiPublicCertificate;

@Repository
public interface UIDAIProviderCertificateRepository
		extends JpaRepository<UidaiPublicCertificate, Integer> {

}
