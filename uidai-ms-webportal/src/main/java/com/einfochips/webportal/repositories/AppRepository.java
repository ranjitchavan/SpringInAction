package com.einfochips.webportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.AppStatus;

/**
 * @author akash.shinde
 *
 */
@Repository
public interface AppRepository extends JpaRepository<AppStatus, Integer> {
	AppStatus findByIpAddress(String ipAddress);
}
