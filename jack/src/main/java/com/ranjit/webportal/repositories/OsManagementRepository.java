package com.einfochips.webportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.einfochips.webportal.domain.OsMaster;

public interface OsManagementRepository extends CrudRepository<OsMaster, Integer> {

	@Query("select distinct osType from OsMaster")
	List<String> getOsType();
	
	@Query("select  osName from OsMaster os where os.osType = ?1")
	List<String> getOsSubType(String os);

}