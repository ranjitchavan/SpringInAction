package com.einfochips.webportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.VersionManagement;

@Repository
public interface VersionManagementRepository
		extends CrudRepository<VersionManagement, Integer> {

	List<VersionManagement> findByisCurrent(int isCurrent);

	VersionManagement findByOsTypeAndApplicationTypeAndIsCurrent(String osType,
			String applicationType, int isCurrent);

	VersionManagement findByOsTypeAndApplicationTypeAndCurrentVersion(String osType,
			String applicationType, String currentVersion);

	@Query("select distinct currentVersion from VersionManagement vm where vm.isCurrent = 0 and vm.applicationType = ?1 and vm.osType = ?2")
	List<String> getCurrentVersions(String applicationType, String osType);

	@Query("select distinct dpId from VersionManagement vm where vm.isCurrent = 0 and vm.applicationType = ?1 and vm.osType = ?2 and vm.currentVersion = ?3")
	List<String> getDpId(String applicationType, String os, String version);

	@Query("select distinct applicationType from VersionManagement vm where vm.isCurrent = 0")
	List<String> getSoftwares();

	@Query("select distinct os from VersionManagement vm where vm.isCurrent = 0 and vm.applicationType = ?1")
	List<String> getOsList(String applocationType);

	@Query("select distinct osType from VersionManagement vm where vm.isCurrent = 0 and vm.applicationType = ?1")
	List<String> getOsSubTypeList(String applocationType);

	VersionManagement findByOsTypeAndApplicationTypeAndCurrentVersionAndDpId(
			String osSubType, String applicationType, String latestVersion, String id);

	VersionManagement findByOsTypeAndApplicationTypeAndIsCurrentAndDpId(String osSubType,
			String applicationType, int i, String dpId);

}