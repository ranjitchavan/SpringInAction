package com.einfochips.webportal.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.AppStatus;
import com.einfochips.webportal.services.AppService;
import com.einfochips.webportal.utility.Constants;
import com.einfochips.webportal.utils.CallApi;

@RestController
public class AuditLogController {

	private static final Logger LOGGER = Logger.getLogger(AuditLogController.class);

	@Autowired
	AppService appService;

	@Value("${auditlog.api.ishttps}")
	private String isHttps;

	// API to delete App
	@RequestMapping(value = "/delete-app", method = RequestMethod.POST)
	public ResponseEntity<AppStatus> deleteApp(@RequestBody AppStatus appStatus,
			HttpServletRequest httpServletRequest) {
		LOGGER.info("------IN DELETE APP START-----");
		ResponseEntity<AppStatus> response = null;
		try {
			appService.deleteApp(appStatus);
			LOGGER.info("DELETED app successfully");
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.info("------ERROR WHILE DELETING APP-----");
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	// API to get App
	@RequestMapping(value = "/get-app", method = RequestMethod.GET)
	public ResponseEntity<List<AppStatus>> getApp(HttpServletRequest httpServletRequest) {
		LOGGER.info("------IN GET  APP START-----");
		ResponseEntity<List<AppStatus>> response = null;
		List<AppStatus> apStatus = new ArrayList<>();
		for (AppStatus ap : appService.findAllApp()) {
			String api = new String();
			if (isHttps.equalsIgnoreCase("true")) {
				api = "https://" + ap.getIpAddress() + Constants.GET_AUDIT_LOG_STATUS;
			}
			else {
				api = "http://" + ap.getIpAddress() + Constants.GET_AUDIT_LOG_STATUS;
			}
			try {
				ResponseEntity<String> logStatus = CallApi
						.restCallForGetAuditLogStatus(api);
				LOGGER.info("logStatus.getBody(): " + logStatus);
				ap.setStatus(logStatus.getBody());
				LOGGER.info(ap.toString());
			}
			catch (Exception e) {
				ap.setStatus("ERROR");
				LOGGER.info("------ERROR WHILE GETTING APP-----");
			}
			apStatus.add(ap);
		}
		LOGGER.info("RETURING LIST");
		response = new ResponseEntity<>(apStatus, HttpStatus.OK);
		return response;
	}

	// API to Add App
	@RequestMapping(value = "/add-app", method = RequestMethod.POST)
	public ResponseEntity<AppStatus> addApp(@RequestBody AppStatus appStatus,
			HttpServletRequest httpServletRequest) {
		LOGGER.info("------IN ADD  APP START-----");
		ResponseEntity<AppStatus> response;
		try {

			if (appStatus.getId() != null) {
				return response = new ResponseEntity<>(appService.saveAppData(appStatus),
						HttpStatus.OK);
			}
			else {
				AppStatus appSts = appService.findByIpAdress(appStatus.getIpAddress());
				if (appSts != null) {
					return response = new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
				else {
					return response = new ResponseEntity<>(
							appService.saveAppData(appStatus), HttpStatus.OK);
				}
			}
		}
		catch (Exception e) {
			return response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// API to Add App
	@RequestMapping(value = "/enable-disable-logs", method = RequestMethod.POST)
	public ResponseEntity<AppStatus> enableDisableLogs(@RequestBody String appStatus,
			HttpServletRequest httpServletRequest) {
		LOGGER.info("------IN ADD  APP START-----");
		LOGGER.info("API CALLED: " + Constants.DISABLE_ENABLE_LOGS);

		for (AppStatus ap : appService.findAllApp()) {
			LOGGER.info("APP LOG STATUS: " + appStatus);
			String api = new String();
			if (isHttps.equalsIgnoreCase("true")) {
				api = "https://" + ap.getIpAddress() + Constants.DISABLE_ENABLE_LOGS;
			}
			else {
				api = "http://" + ap.getIpAddress() + Constants.DISABLE_ENABLE_LOGS;
			}
			CallApi.restCallForDisableAndEnableLogss(api, appStatus);
		}

		ResponseEntity<AppStatus> response;
		return response = new ResponseEntity<>(HttpStatus.OK);
	}
}
