package com.einfochips.webportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.einfochips.webportal.domain.DeviceProviderCertificate;

/**
 * @author akash.shinde
 *
 */
@Repository
public interface DeviceProviderCertificateRepository
		extends JpaRepository<DeviceProviderCertificate, Integer> {
	List<DeviceProviderCertificate> findByCustomerIdAndRevokedOrderByExpiryDateDesc(
			Integer customerMasterId, byte i);
	
	@Query("select distinct dpId from DeviceProviderCertificate")
	List<String> getDpId();
}
