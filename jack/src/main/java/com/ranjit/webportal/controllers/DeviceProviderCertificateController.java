package com.einfochips.webportal.controllers;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.domain.DeviceProviderCertificate;
import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.dto.DeviceProviderCertificateDTO;
import com.einfochips.webportal.services.CustomerMasterService;
import com.einfochips.webportal.services.DeviceProviderCertificateService;
import com.einfochips.webportal.services.UserService;
import com.einfochips.webportal.utility.ConversionUtility;
import com.einfochips.webportal.utils.UserTokenParser;

/**
 * @author akash.shinde
 * 
 * THIS Class used to SAVE/GET/UPDATE device certificates
 * 
 * 
 */
@RestController
public class DeviceProviderCertificateController {

	@Autowired
	DeviceProviderCertificateService deviceProviderCertificateService;

	@Autowired
	UserService userService;

	@Autowired
	CustomerMasterService customerMasterService;

	private final static Logger LOGGER = Logger
			.getLogger(DeviceProviderCertificateController.class);

	/**
	 * @param dpcert
	 * @param uicert
	 * @param expiryDate
	 * @param dpprivatekey
	 * @param dpidentifier
	 * @param dpid
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/save-vender-certificate", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<DeviceProviderCertificate> saveVenderCertificate(
			@RequestParam("dpcert") MultipartFile dpcert,
			@RequestParam("uicert") MultipartFile uicert,
			@RequestParam("expiryDate") String expiryDate,
			@RequestParam("dpprivatekey") String dpprivatekey,
			@RequestParam("dpid") String dpid,
			@RequestParam("customerName") String customerName,
			MultipartHttpServletRequest request) {

		try {

			
			String arrOfCustomer[] = customerName.split(",");
			//List<String> customerNames=new ArrayList<String>(Arrays.asList(arrOfCustomer));

			LOGGER.info("Save device vender ceritficate started ");
			
			List<DeviceProviderCertificate> dpcList=new ArrayList<DeviceProviderCertificate>();
			
			

			String dpcertString = ConversionUtility
					.validCertificate(new String(dpcert.getBytes()));
			String uiCertString = ConversionUtility
					.validCertificate(new String(uicert.getBytes()));

			
			if (dpcertString == null || uiCertString == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
			}
			

			byte[] buff = dpcertString.getBytes();
			String str = ConversionUtility.encodeBase64Certificate(buff);
			// set expiry date
			

			
			// LOGGER.info("encode of device vender ceritficate started ");

			
			// LOGGER.info("encode of device vender ceritficate end ");
			byte[] buffu = uiCertString.getBytes();
			// LOGGER.info("encode of device UIDAI ceritficate started ");

			String stru = ConversionUtility.encodeBase64Certificate(buffu);
			// LOGGER.info("encode of device UIDAI ceritficate end ");
			DeviceProviderCertificate dpc;
			
			for(String customeNames:arrOfCustomer) {
				System.out.println(customeNames+":::customeNames");
				 dpc = new DeviceProviderCertificate();
				if (dpid.length() != 0) {
					dpc.setDpId(dpid);
				}

				Integer userId = UserTokenParser.getUserIdFromToken(request);
				dpc.setCreatedBy(userId);
				dpc.setExpiryDate(ConversionUtility.getExpiryDate(buff));
				// set from date
				dpc.setExpiryDate(ConversionUtility.getExpiryDate(buff));
				// set from date
				dpc.setFromDate(ConversionUtility.getFromDate(buff));

				LocalDate localDate = dpc.getExpiryDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				int year = localDate.getYear();
				String month = String.valueOf(localDate.getMonthValue());
				String day = String.valueOf(localDate.getDayOfMonth());

				if (Integer.parseInt(month) < 10) {
					month = String.format("%02d", Integer.parseInt(month));
				}

				if (Integer.parseInt(day) < 10) {
					day = String.format("%02d", Integer.parseInt(day));
				}
				dpc.setFromDate(ConversionUtility.getFromDate(buff));
				dpc.setDpCertificateIdentifier(year + "" + month + "" + day);
				dpc.setCertificate(str.getBytes());
				dpc.setUidaiRootCertificate(stru.getBytes());

				dpc.setUpdationTime(new Date());
				dpc.setCreationTime(new Date());
				dpc.setDpPrivateKeyLabel(dpprivatekey);
				CustomerMaster cm = customerMasterService.isCustomerPresent(customeNames);
				dpc.setCustomerId(cm.getCustomerMasterId());	
				
				dpcList.add(dpc);
			}
			deviceProviderCertificateService.save(dpcList);
			// dpc.setRevoked(false);
			LOGGER.info("vender certificate saved ");
			
			return new ResponseEntity<>(null, HttpStatus.OK);

		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @return device certificate class object
	 */
	@RequestMapping(value = "/get-vender-certificates", method = RequestMethod.GET)
	public ResponseEntity<List<DeviceProviderCertificateDTO>> getVenderCertificates() {
		List<DeviceProviderCertificateDTO> list = new ArrayList<>();

		LOGGER.info("GET all vender ceritficate started ");
		for (DeviceProviderCertificate d : deviceProviderCertificateService
				.getDeviceProviderCertificate()) {
			DeviceProviderCertificateDTO dd = new DeviceProviderCertificateDTO();
			dd.setDpId(d.getDpId());
			dd.setExpiryDate(d.getExpiryDate());
			dd.setCreationTime(d.getCreationTime());
			dd.setUpdationTime(d.getUpdationTime());
			dd.setExpiryDate(d.getExpiryDate());
			dd.setDpPrivateKeyLabel(d.getDpPrivateKeyLabel());
			dd.setDeviceProviderCertificateId(d.getDeviceProviderCertificateId());
			dd.setDpCertificateIdentifier(d.getDpCertificateIdentifier());
			dd.setRevoked(d.getRevoked());

			if (d.getCustomerId() != null) {
				if (d.getCustomerId() != 0) {
					CustomerMaster custObject = customerMasterService
							.getCustomer(d.getCustomerId());
					if (custObject != null) {
						dd.setCustomerId(custObject.getCustomerMasterId());
						dd.setCustomerName(custObject.getCustomerName());
					}
				}
			}
			// dd.setCreatedBy(createdBy);

			if (d.getCreatedBy() != 0) {
				Users uObject = userService.getUser(d.getCreatedBy());
				if (uObject != null) {
					dd.setCreatedBy(uObject.getFirstName() + " " + uObject.getLastName());
				}
			}

			list.add(dd);
		}

		LOGGER.info("GET all vender ceritficate END ");
		if (!list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		else {
			LOGGER.info(" vender ceritficate list is empty ");
			return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * @param response
	 * @param id
	 */
	@RequestMapping(value = "/get-vender-cert/{id}", method = RequestMethod.GET)
	public void downloadVenderCertificate(HttpServletResponse response,
			@PathVariable("id") Integer id) {
		DeviceProviderCertificate device = deviceProviderCertificateService
				.getDeviceProviderById(id);
		LOGGER.info("View  previous uploaded vender ceritficate Started ");
		try {
			if (device.getCertificate() != null) {
				String encodedEmailString = new String(device.getCertificate(), "UTF-8");
				LOGGER.info(
						"View  previous uploaded vender ceritficate decoding started");

				byte[] imageBlob = ConversionUtility
						.decodeBase64Certificate(encodedEmailString);

				InputStream is = null;
				Blob blob = new SerialBlob(imageBlob);
				try {
					is = blob.getBinaryStream();
				}
				catch (SQLException e) {
					LOGGER.info(e.getMessage());
				}
				LOGGER.info("View  previous uploaded vender ceritficate decoding END");
				// MIME type of the file
				response.setContentType("application/octet-stream");
				// Response header
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + "vender.crt" + "\"");
				// Read from the file and write into the response
				// Response header
				// Read from the file and write into the response
				OutputStream os = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				if (is != null)
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}

				os.flush();
				os.close();
				is.close();
				LOGGER.info("View  previous uploaded vender ceritficate END");
			}
			else {
				LOGGER.info("View  previous uploaded vender ceritficate is not found ");
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());

		}

	}

	/**
	 * @param response
	 * @param id
	 */
	@RequestMapping(value = "/get-uidai-cert/{id}", method = RequestMethod.GET)
	public void downloadUIDAICertificate(HttpServletResponse response,
			@PathVariable("id") Integer id) {
		DeviceProviderCertificate device = deviceProviderCertificateService
				.getDeviceProviderById(id);
		try {
			if (device.getCertificate() != null) {
				// data to be loaded into jabascript

				String encodedEmailString = new String(device.getUidaiRootCertificate(),
						"UTF-8");

				byte[] imageBlob = ConversionUtility
						.decodeBase64Certificate(encodedEmailString);

				InputStream is = null;
				Blob blob = new SerialBlob(imageBlob);
				try {
					is = blob.getBinaryStream();
				}
				catch (SQLException e) {
					LOGGER.info(e.getMessage());
				}

				// MIME type of the file
				response.setContentType("application/octet-stream");
				// Response header
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + "UIDAI.crt" + "\"");
				// Read from the file and write into the response
				// Response header
				// Read from the file and write into the response
				OutputStream os = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				if (is != null)
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}

				os.flush();
				os.close();
				is.close();
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

	}

	/**
	 * @param dpcert
	 * @param uicert
	 * @param dpprivatekey
	 * @param dpidentifier
	 * @param dpid
	 * @param revoked
	 * @param deviceProviderCertificateId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update-vender-certificate", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<?> updateVenderCertificate(
			@RequestParam(value = "dpcert", required = false) MultipartFile dpcert,
			@RequestParam(value = "uicert", required = false) MultipartFile uicert,
			@RequestParam("dpprivatekey") String dpprivatekey,
			@RequestParam("dpid") String dpid, @RequestParam("revoked") byte revoked,
			@RequestParam("deviceProviderCertificateId") Integer deviceProviderCertificateId,
			@RequestParam("customerName") String customerName,
			MultipartHttpServletRequest request) {

		try {

			DeviceProviderCertificate device = deviceProviderCertificateService
					.getDeviceProviderById(deviceProviderCertificateId);
			device.setDeviceProviderCertificateId(deviceProviderCertificateId);

			CustomerMaster cm = customerMasterService.isCustomerPresent(customerName);

			if (dpid.length() != 0) {
				device.setDpId(dpid);
			}

			Integer userId = UserTokenParser.getUserIdFromToken(request);
			device.setCreatedBy(userId);

			if (uicert != null) {
				String uicertString = ConversionUtility
						.validCertificate(new String(uicert.getBytes()));

				if (uicertString == null) {
					return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				}

				byte[] buff = uicertString.getBytes();

				String str = ConversionUtility.encodeBase64Certificate(buff);
				device.setUidaiRootCertificate(str.getBytes());
			}

			if (dpcert != null) {
				String dpcertString = ConversionUtility
						.validCertificate(new String(dpcert.getBytes()));

				if (dpcertString == null) {
					return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				}

				byte[] buff = dpcertString.getBytes();
				String str = ConversionUtility.encodeBase64Certificate(buff);

				device.setCertificate(str.getBytes());
				device.setExpiryDate(ConversionUtility.getExpiryDate(buff));

				LocalDate localDate = device.getExpiryDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				int year = localDate.getYear();
				String month = String.valueOf(localDate.getMonthValue());
				String day = String.valueOf(localDate.getDayOfMonth());

				if (Integer.parseInt(month) < 10) {
					month = String.format("%02d", Integer.parseInt(month));
				}

				if (Integer.parseInt(day) < 10) {
					day = String.format("%02d", Integer.parseInt(day));
				}

				device.setDpCertificateIdentifier(year + "" + month + "" + day);

			}

			device.setUpdationTime(new Date());
			device.setDpPrivateKeyLabel(dpprivatekey);
			device.setRevoked(revoked);
			device.setCustomerId(cm.getCustomerMasterId());

			//device = deviceProviderCertificateService.save(device);

			return new ResponseEntity<>(null, HttpStatus.OK);

		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
