package com.einfochips.webportal.controllers;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.einfochips.webportal.domain.UidaiPublicCertificate;
import com.einfochips.webportal.dto.UidaiPublicCertificateDTO;
import com.einfochips.webportal.services.UIDAIProviderCertificateService;
import com.einfochips.webportal.utility.Constants;
import com.einfochips.webportal.utility.ConversionUtility;
import com.einfochips.webportal.utils.UserTokenParser;

/**
 * @author akash.shinde This class is used to peform all operation related to UIDAI public
 * certificate
 */
@RestController
public class UIDAIProviderCertificateController {
	/**
	 * 
	 */
	@Autowired
	UIDAIProviderCertificateService uidaiProviderCertificateService;

	private static final Logger LOGGER = Logger
			.getLogger(UIDAIProviderCertificateController.class);

	/**
	 * 
	 * This method used to save the UIDAI certificate into database
	 * 
	 * @param uidaicert
	 * @param expiryDate
	 * @param envtype
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/save-uidai-public-certificate", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<UidaiPublicCertificate> saveUIDAIPublicCertificate(
			@RequestParam("uidaicert") MultipartFile uidaicert,
			@RequestParam("expiryDate") String expiryDate,
			@RequestParam("envtype") String envtype, HttpServletRequest request) {
		LOGGER.info("------------save UIDAI certificate  Start------------");
		Integer userId = UserTokenParser.getUserIdFromToken(request);
		UidaiPublicCertificate upc = new UidaiPublicCertificate();
		byte[] buff = null;
		String str = null;
		try {
			LOGGER.info(
					"------------ UIDAI certificate  convertion  start  using encoding ------------");

			String uidaiString = ConversionUtility
					.validCertificate(new String(uidaicert.getBytes()));

			if (uidaiString == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
			}

			buff = uidaiString.getBytes();
			str = ConversionUtility.encodeBase64Certificate(buff);
			upc.setCertificate(str.getBytes());
			LOGGER.info("------------ UIDAI certificate  convertion  end  ------------");
		}
		catch (IOException e) {
			LOGGER.error(
					"------------ ERROR occur during UIDAI certificate  conversion ------------",
					e);
		}

		// upc.setCreatedBy(UserTokenParser.getUserIdFromToken(request));
		if (ConversionUtility.getExpiryDate(buff) != null) {
			upc.setExpiryDate(ConversionUtility.getExpiryDate(buff));
		}
		if (ConversionUtility.getFromDate(buff) != null) {
			upc.setFromDate(ConversionUtility.getFromDate(buff));
		}

		upc.setUpdationTime(new Date());
		upc.setCreationTime(new Date());
		upc.setCertificateType(envtype);
		upc.setCreatedBy(userId);
		upc = uidaiProviderCertificateService.save(upc);
		LOGGER.info("------------ UIDAI certificate  saved  ------------");
		return new ResponseEntity<>(upc, HttpStatus.OK);
	}

	/**
	 * 
	 * This method used to GET all certificate from database which are not deleted based
	 * on flag
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/get-all-uidai-public-certificate", method = RequestMethod.GET)
	public ResponseEntity<List<UidaiPublicCertificateDTO>> getAllUIDAIPublicCertificates(
			HttpServletRequest request) {

		LOGGER.info("------------GET ALL UIDAI certificate  Start  ------------");
		if (!uidaiProviderCertificateService.getAllCert().isEmpty()) {
			LOGGER.info(
					"------------GET ALL UIDAI certificate  end returnig certificate  ------------");
			return new ResponseEntity<>(uidaiProviderCertificateService.getAllCert(),
					HttpStatus.OK);
		}
		else {
			LOGGER.info(
					"------------NO UIDAI certificate found in database ------------");
			return new ResponseEntity<>(uidaiProviderCertificateService.getAllCert(),
					HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * 
	 * This method used to DELETE the certificate from the database based on id
	 * 
	 * @param uidaiPublicCertificate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete-uidai-public-certificate", method = RequestMethod.POST)
	public ResponseEntity<List<UidaiPublicCertificate>> deleteUIDAIPublicCertificate(
			@RequestBody UidaiPublicCertificateDTO uidaiPublicCertificate,
			HttpServletRequest request) {
		LOGGER.info(uidaiPublicCertificate.toString());
		UidaiPublicCertificate u = new UidaiPublicCertificate();
		u.setUidaiCertificateId(uidaiPublicCertificate.getUidaiCertificateId());

		LOGGER.info("------------DELETE UIDAI certificate start-----------");
		uidaiProviderCertificateService.delete(u);
		LOGGER.info("------------DELETE UIDAI certificate end -----------");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
