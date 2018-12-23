package com.einfochips.webportal.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.einfochips.webportal.domain.VersionManagement;
import com.einfochips.webportal.dto.VersionListDTO;
import com.einfochips.webportal.services.DeviceProviderCertificateService;
import com.einfochips.webportal.services.OsManagementService;
import com.einfochips.webportal.services.VersionManagementService;
import com.einfochips.webportal.utils.UserTokenParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class VersionManagementController {

	private static final Logger LOGGER = Logger
			.getLogger(VersionManagementController.class);

	@Autowired
	private VersionManagementService service;

	@Autowired
	private OsManagementService osService;

	@Autowired
	private DeviceProviderCertificateService deviceService;

	@Value("${uidai.binary.filepath}")
	private String windowsFilePath;

	@Value("${uidai.binary.filepath}")
	private String linuxFilePath;

	@RequestMapping(value = "/get-previous-versions", method = RequestMethod.GET)
	public ResponseEntity<List<VersionListDTO>> getPreviousVersions() {
		LOGGER.info("------------Get Previous Versions Start------------");
		int isCurrent = 0;

		try {
			List<VersionListDTO> management = service.getVersionList(isCurrent);
			LOGGER.info("------------Get Previous Versions End------------");
			return new ResponseEntity<>(management, HttpStatus.OK);

		}
		catch (Exception e) {
			LOGGER.error("Error in getting previous versions history. Error : ", e);
			LOGGER.info("------------Get Previous Versions End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-current-versions", method = RequestMethod.GET)
	public ResponseEntity<List<VersionListDTO>> getLatestVersions() {
		LOGGER.info("------------Get Current Versions Start------------");
		int isCurrent = 1;
		try {
			List<VersionListDTO> management = service.getVersionList(isCurrent);
			LOGGER.info("------------Get Current Versions End------------");

			return new ResponseEntity<>(management, HttpStatus.OK);

		}
		catch (Exception e) {

			LOGGER.error("Error in getting current versions history. Error : ", e);
			LOGGER.info("------------Get Current Versions End------------");

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/add-latest-version", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<String> saveLatestVersion(
			@RequestParam("version") MultipartFile version,
			@RequestParam(value = "signatureFile", required = false) MultipartFile signatureFile,
			@RequestParam("applicationType") String applicationType,
			@RequestParam("os") String os,
			@RequestParam("osSubType") String osSubTypeArray,
			@RequestParam("latestVersion") String latestVersion,
			@RequestParam("dpId") String dpId, HttpServletRequest httpServletRequest)
			throws JsonParseException, JsonMappingException, IOException, SerialException,
			SQLException {

		LOGGER.info("customerId : " + dpId);
		LOGGER.info("------------Add Latest VersionS Start------------");
		String arr[] = osSubTypeArray.split(",");
		

		MultipartFile file = version;

		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();

		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();

		try {
			for (String osSubType : arr) {

				// return convFile;


				VersionManagement alreadyVersion = null;

				if (applicationType.equalsIgnoreCase("Management Client")
						|| applicationType.equalsIgnoreCase("RD service")) {
					LOGGER.info("Checking for already exists for MC and RD");
					alreadyVersion = service.getCurrentVersionByDpIds(applicationType, os,
							osSubType, latestVersion, dpId);
				}
				else {
					alreadyVersion = service.getCurrentVersion(applicationType, osSubType,
							latestVersion);
				}

				if (alreadyVersion != null) {
					return new ResponseEntity<>("", HttpStatus.CONFLICT);

				}
				else {

					VersionManagement management = new VersionManagement();

					management.setApplication_type(applicationType);
					management.setOs(os);
					management.setOsType(osSubType);
					management.setCurrent_version(latestVersion);
					management.setCreation_time(new Date());
					management.setUpdation_time(new Date());
					management.setisCurrent(1);

					if (dpId != "") {
						management.setDpId(dpId);
					}

					Integer userId = UserTokenParser
							.getUserIdFromToken(httpServletRequest);
					management.setCreated_by(userId);
					String envType = System.getProperty("os.name");
					// management.setCurrent_version_location(blob);
					try {
						// Date appendtofile = new Date();
						if (envType.toLowerCase().contains("windows")) {
							createWindowsDir();
							File dest = new File(windowsFilePath + applicationType + "_"
									+ osSubType + "_" + latestVersion + "_" + dpId + "_"
									+ version.getOriginalFilename());
							FileUtils.copyFile(convFile, dest);
							// convFile.transferTo(
							// new File(windowsFilePath + applicationType + "_"
							// + osSubType + "_" + latestVersion + "_" + dpId
							// + "_" + version.getOriginalFilename()));
							management.setFile_name(windowsFilePath + applicationType
									+ "_" + osSubType + "_" + latestVersion + "_" + dpId
									+ "_" + version.getOriginalFilename());

						}
						else {
							createLinuxDir();
							File dest = new File(linuxFilePath + applicationType + "_"
									+ osSubType + "_" + latestVersion + "_" + dpId + "_"
									+ version.getOriginalFilename());
							FileUtils.copyFile(convFile, dest);
							// multipartFile.transferTo(
							// new File(linuxFilePath + applicationType + "_"
							// + osSubType + "_" + latestVersion + "_" + dpId
							// + "_" + version.getOriginalFilename()));

							management.setFile_name(linuxFilePath + applicationType + "_"
									+ osSubType + "_" + latestVersion + "_" + dpId + "_"
									+ version.getOriginalFilename());
						}

					}
					catch (Exception e) {
						LOGGER.info("ERROR occur during file saving to path "
								+ e.getMessage());
						LOGGER.info(e.getMessage());
					}
					// set signature
					if (signatureFile != null) {
						LOGGER.info("Setting signature..");
						// String signature = new
						// String(signatureFile.getBytes()).replaceAll("[\n\r]",
						// "");
						management.setSignatureFileName(
								signatureFile.getOriginalFilename());
						management.setSignatureFile(new String(signatureFile.getBytes())
								.replaceAll("[\n\r]", "").getBytes());
					}
					VersionManagement management2 = null;

					if (applicationType.equalsIgnoreCase("Management Client")
							|| applicationType.equalsIgnoreCase("RD service")) {
						LOGGER.info("finding for MC and RD");

						management2 = service.ifLatestVersionWithDpId(applicationType, os,
								osSubType, dpId);
					}
					else {
						management2 = service.ifLatestVersion(applicationType, osSubType);
					}

					if (management2 != null) {
						management2.setisCurrent(0);
						service.addLatestVersion(management2);
					}

					service.addLatestVersion(management);
				}
			}
			LOGGER.info("------------Add Latest VersionS End------------");
			return new ResponseEntity<>("", HttpStatus.OK);

		}
		catch (Exception e) {
			LOGGER.error("Error in adding latest version. Error : ", e);
			LOGGER.info("------------Add Latest VersionS End------------");
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
	}

	private void createWindowsDir() {
		File file = new File(windowsFilePath);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOGGER.info("Create New Directory in to " + windowsFilePath);
			}
			else {
				LOGGER.info("Unable to Create Directory in to " + windowsFilePath);
			}
		}
		else {
			LOGGER.info("Directory is already Present ");
		}
	}

	private void createLinuxDir() {
		File file = new File(linuxFilePath);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOGGER.info("Create New Directory in to " + linuxFilePath);
			}
			else {
				LOGGER.info("Unable to Create Directory in to " + linuxFilePath);
			}
		}
		else {
			LOGGER.info("Directory is already Present ");
		}
	}

	@RequestMapping(value = "/get-only-software", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getSoftware() {
		LOGGER.info("------------Get Only VersionS Start------------");
		try {
			List<String> list = service.getSoftwareList();
			LOGGER.info("------------Get Only VersionS End------------");
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in getting software history. Error : ", e);
			LOGGER.info("------------Get Only VersionS End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/get-only-os", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getOs(@RequestBody VersionManagement management) {
		LOGGER.info("------------Get Only Os Start------------");
		try {
			List<String> list = service.getOsList(management.getApplication_type());
			LOGGER.info("------------Get Only Os End------------");
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in getting os history. Error : ", e);
			LOGGER.info("------------Get Only Os End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-only-os-subtype", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getOsSubType(
			@RequestBody VersionManagement management) {
		LOGGER.info("------------Get Only Os Start------------");
		try {
			List<String> list = service
					.getOsSubTypeList(management.getApplication_type());
			LOGGER.info("------------Get Only Os End------------");
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in getting os history. Error : ", e);
			LOGGER.info("------------Get Only Os End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-only-versions", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getVersions(
			@RequestBody VersionManagement management) {
		LOGGER.info("------------Get Only Versions Start------------"
				+ management.getOsType());
		try {
			List<String> list = service.getCurrentVersions(
					management.getApplication_type(), management.getOsType());
			LOGGER.info("------------Get Only Versions End------------");
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in getting versions history. Error : ", e);
			LOGGER.info("------------Get Only Versions End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-only-dpId", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getOnlyDpId(
			@RequestBody VersionManagement management) {
		LOGGER.info("------------Get Only DpId Start------------");
		try {
			List<String> list = service.getDpId(management.getApplication_type(),
					management.getOsType(), management.getCurrent_version());
			LOGGER.info("------------Get Only DpId End------------");
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in getting dpid history. Error : ", e);
			LOGGER.info("------------Get Only Dpid End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update-current-version", method = RequestMethod.POST)
	public ResponseEntity<String> updateCurrentVersion(
			@RequestBody VersionManagement management) {
		LOGGER.info("------------Update Current Version Start------------");
		try {

			if (management.getApplication_type().equalsIgnoreCase("Management Client")
					|| management.getApplication_type().equalsIgnoreCase("RD service")) {

				service.changeCurrentVersionWithDpId(management.getOsType(),
						management.getApplication_type(), management.getCurrent_version(),
						1, management.getDpId());

			}
			else {
				service.changeCurrentVersion(management.getOsType(),
						management.getApplication_type(), management.getCurrent_version(),
						1);
			}
			LOGGER.info("------------Update Current Version End------------");

			return new ResponseEntity<>("", HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in updating current versoin. Error : ", e);
			LOGGER.info("------------Update Current Version End------------");
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/edit-version", method = RequestMethod.POST)
	public ResponseEntity<String> editVersion(@RequestBody VersionManagement management,
			HttpServletRequest httpServletRequest) {
		LOGGER.info("------------Edit Version Start------------");

		try {
			VersionManagement management2 = service.getCurrentVersion(
					management.getApplication_type(), management.getOsType(),
					management.getCurrent_version());
			if (management2 != null) {
				return new ResponseEntity<>("", HttpStatus.CONFLICT);
			}

			Integer userId = UserTokenParser.getUserIdFromToken(httpServletRequest);
			management.setCreated_by(userId);
			service.editVersion(management);

			LOGGER.info("------------Edit Version End------------");
			return new ResponseEntity<>("", HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Error in edit-version. Error : ", e);
			LOGGER.info("------------Edit Version End------------");
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-os-type", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getOs() {
		LOGGER.info("------------Get Only Versions Start------------");

		try {
			List<String> osTypeList = osService.getOsType();
			LOGGER.info("------------Get Only Versions End------------");

			return new ResponseEntity<>(osTypeList, HttpStatus.OK);

		}
		catch (Exception e) {

			LOGGER.error("Error in getting os. Error : ", e);
			LOGGER.info("------------Get Only Versions End------------");

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/get-os-subtype", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getOsSubType(
			@RequestBody VersionManagement management,
			HttpServletRequest httpServletRequest) {
		LOGGER.info(
				"------------Get Only os subtype Start------------" + management.getOs());

		try {
			List<String> osTypeList = osService.getOsSubType(management.getOs());
			LOGGER.info("------------Get Only os subtype End------------"
					+ osTypeList.toString());

			return new ResponseEntity<>(osTypeList, HttpStatus.OK);

		}
		catch (Exception e) {

			LOGGER.error("Error in getting os subtype Error : ", e);
			LOGGER.info("------------Get Only os subtype End------------");

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/download-version/{versionId}", method = RequestMethod.GET)
	public void downloadPDFFile(HttpServletResponse response,
			@PathVariable("versionId") Integer version_id) throws IOException {

		VersionManagement management = service.getVersion(version_id);

		// Blob imageBlob = management.getCurrent_version_location();
		InputStream is = null;
		File initialFile;
		String[] fileName = management.getFile_name().split("/");

		try {
			initialFile = new File(management.getFile_name());
			is = new FileInputStream(initialFile);

		}
		catch (Exception e) {
			return;
		}

		// MIME type of the file
		response.setContentType("application/octet-stream");
		// Response header
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName[fileName.length - 1] + "\"");
		// Read from the file and write into the response
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();

	}

	/**
	 * To download signature file.
	 * 
	 * @param response
	 * @param versionId
	 */
	@RequestMapping(value = "/download-signature/{versionId}", method = RequestMethod.GET)
	public void downloadSignature(HttpServletResponse response,
			@PathVariable("versionId") Integer versionId) {
		service.downloadSignature(versionId, response);
	}

	@RequestMapping(value = "/get-dp-id", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getDpID() {
		ResponseEntity<List<String>> response;
		List<String> dpIdList = deviceService.getDpId();
		response = new ResponseEntity<>(dpIdList, HttpStatus.OK);
		return response;
	}
}