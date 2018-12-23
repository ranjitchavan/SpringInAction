package com.einfochips.webportal.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.einfochips.webportal.domain.VersionManagement;
import com.einfochips.webportal.dto.VersionListDTO;

public interface VersionManagementService {

	public void addLatestVersion(VersionManagement management);

	public void updateLatestVersion(VersionManagement management);

	public List<VersionListDTO> getVersionList(int isCurrent);

	public List<String> getSoftwareList();

	public List<String> getOsList(String applicationType);

	public List<String> getOsSubTypeList(String applicationType);

	public List<String> getCurrentVersions(String applicationType, String os);

	public void changeCurrentVersion(String osType, String applicationType,
			String currentVersion, int isCurrent);

	public VersionManagement ifLatestVersion(String applicationType, String osSubType);

	public void editVersion(VersionManagement management);

	public VersionManagement getVersion(int id);

	public VersionManagement getCurrentVersion(String applicationType, String osSubType,
			String currentVersion);

	public void downloadSignature(Integer versionId, HttpServletResponse response);

	public VersionManagement getCurrentVersionByDpIds(String applicationType, String os,
			String osSubType, String latestVersion, String customerId);

	public VersionManagement ifLatestVersionWithDpId(String applicationType, String os,
			String osSubType, String dpId);

	public void changeCurrentVersionWithDpId(String osType, String application_type,
			String current_version, int i, String dpId);

	List<String> getDpId(String applicationType, String os, String version);

}