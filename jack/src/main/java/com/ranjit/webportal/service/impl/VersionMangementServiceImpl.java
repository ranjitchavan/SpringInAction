package com.einfochips.webportal.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.domain.VersionManagement;
import com.einfochips.webportal.dto.VersionListDTO;
import com.einfochips.webportal.repositories.VersionManagementRepository;
import com.einfochips.webportal.services.UserService;
import com.einfochips.webportal.services.VersionManagementService;

@Service
public class VersionMangementServiceImpl implements VersionManagementService {

	private static final Logger LOGGER = Logger
			.getLogger(VersionMangementServiceImpl.class);

	@Autowired
	private VersionManagementRepository version;

	@Autowired
	private UserService userService;

	@Override
	public void addLatestVersion(VersionManagement management) {
		// TODO Auto-generated method stub
		version.save(management);
	}

	@Override
	public void updateLatestVersion(VersionManagement management) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VersionListDTO> getVersionList(int isCurrent) {
		List<VersionListDTO> list = new ArrayList<>();

		for (VersionManagement vm : version.findByisCurrent(isCurrent)) {
			VersionListDTO v = new VersionListDTO();
			Users user = userService.getUserForCreatedby(vm.getCreated_by());
			v.setVersion_management_id(vm.getVersion_management_id());
			v.setApplication_type(vm.getApplication_type());
			v.setCurrent_version(vm.getCurrent_version());
			v.setCreation_time(vm.getUpdation_time());
			v.setOsType(vm.getOsType());
			v.setOs(vm.getOs());

			if (vm.getDpId() != null) {

				v.setDpId(vm.getDpId());

			}
			else {
				v.setDpId("");
			}

			v.setUserName(
					(user != null) ? user.getFirstName() + " " + user.getLastName() : "");

			if (vm.getSignatureFile() != null) {
				v.setSignatureExist(true);
			}

			list.add(v);
		}

		return list;
	}

	@Override
	public List<String> getCurrentVersions(String applicationType, String os) {
		return version.getCurrentVersions(applicationType, os);
	}

	@Override
	public List<String> getDpId(String applicationType, String os, String version1) {
		return version.getDpId(applicationType, os, version1);
	}

	@Override
	public List<String> getSoftwareList() {
		return version.getSoftwares();
	}

	@Override
	public List<String> getOsList(String applicationType) {
		return version.getOsList(applicationType);
	}

	@Override
	public List<String> getOsSubTypeList(String applicationType) {
		return version.getOsSubTypeList(applicationType);
	}

	@Override
	public VersionManagement ifLatestVersion(String applicationType, String osSubType) {
		return version.findByOsTypeAndApplicationTypeAndIsCurrent(osSubType,
				applicationType, 1);
	}

	@Override
	public void editVersion(VersionManagement management) {
		VersionManagement management2 = version
				.findOne(management.getVersion_management_id());
		if (management2 != null) {
			management2.setApplication_type(management.getApplication_type());
			management2.setOs(management.getOs());
			management2.setCurrent_version(management.getCurrent_version());
			management2.setUpdation_time(new Date());
			management2.setisCurrent(1);
			management2.setCreated_by(management.getCreated_by());

			version.save(management2);
		}
	}

	@Override
	public VersionManagement getVersion(int id) {
		return version.findOne(id);
	}

	@Override
	public VersionManagement getCurrentVersion(String applicationType, String osSubType,
			String currentVersion) {
		return version.findByOsTypeAndApplicationTypeAndCurrentVersion(osSubType,
				applicationType, currentVersion);
	}

	@Override
	public void downloadSignature(Integer versionId, HttpServletResponse response) {
		try {

			VersionManagement versionManagement = getVersion(versionId);
			LOGGER.info("versionManagement is :  " + versionManagement);
			if (versionManagement.getSignatureFile() != null) {
				Blob signatureFileBlob = new SerialBlob(
						versionManagement.getSignatureFile());
				InputStream inputStream = signatureFileBlob.getBinaryStream();

				// setting response properties
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ versionManagement.getSignatureFileName() + "\"");

				// Read from the file and write into the response
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}
			else {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment; filename='Signature.txt'");
			}

		}
		catch (Exception e) {
			LOGGER.error("Error: ", e);
		}
	}

	@Override
	public VersionManagement getCurrentVersionByDpIds(String applicationType, String os,
			String osSubType, String latestVersion, String dpId) {
		return version.findByOsTypeAndApplicationTypeAndCurrentVersionAndDpId(osSubType,
				applicationType, latestVersion, dpId);
	}

	@Override
	public VersionManagement ifLatestVersionWithDpId(String applicationType, String os,
			String osSubType, String dpId) {
		return version.findByOsTypeAndApplicationTypeAndIsCurrentAndDpId(osSubType,
				applicationType, 1, dpId);
	}

	@Override
	public void changeCurrentVersion(String osType, String applicationType,
			String currentVersion, int isCurrent) {
		VersionManagement management = version.findByOsTypeAndApplicationTypeAndIsCurrent(
				osType, applicationType, isCurrent);

		if (management != null) {
			management.setisCurrent(0);
			management.setUpdation_time(new Date());
			version.save(management);
		}

		VersionManagement management2 = version
				.findByOsTypeAndApplicationTypeAndCurrentVersion(osType, applicationType,
						currentVersion);

		if (management2 != null) {
			management2.setisCurrent(1);
			management2.setUpdation_time(new Date());
			version.save(management2);
		}

	}

	@Override
	public void changeCurrentVersionWithDpId(String osSubType, String applicationType,
			String currentVersion, int isCurrent, String dpId) {

		VersionManagement management = version
				.findByOsTypeAndApplicationTypeAndIsCurrentAndDpId(osSubType,
						applicationType, isCurrent, dpId);
		if (management != null) {
			management.setisCurrent(0);
			management.setUpdation_time(new Date());
			version.save(management);
		}

		System.out.println(management.toString());

		VersionManagement management2 = version
				.findByOsTypeAndApplicationTypeAndCurrentVersionAndDpId(osSubType,
						applicationType, currentVersion, dpId);

		if (management2 != null) {
			management2.setisCurrent(1);
			management2.setUpdation_time(new Date());
			version.save(management2);
		}
	}
}
