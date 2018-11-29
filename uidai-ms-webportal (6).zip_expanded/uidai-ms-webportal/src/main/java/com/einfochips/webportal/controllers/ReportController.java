package com.einfochips.webportal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.dto.DeviceReportRequestDto;
import com.einfochips.webportal.dto.DeviceReportResponseDto;
import com.einfochips.webportal.dto.ErrorReportRequestDto;
import com.einfochips.webportal.dto.ErrorReportResponseDto;
import com.einfochips.webportal.dto.ListApiResponse;
import com.einfochips.webportal.services.ReportService;

/**
 * This class exposes reporting APIs.
 * 
 * @author asra.shaikh
 *
 */
@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;

	/**
	 * Method to generate device report.
	 * 
	 * @param deviceReportRequestDto : Contains device filter parameters
	 * @return
	 */
	@RequestMapping(value = "/deviceReport", method = RequestMethod.POST)
	public ResponseEntity<ListApiResponse<DeviceReportResponseDto>> generateDeviceReport(
			@RequestBody DeviceReportRequestDto deviceReportRequestDto) {
		ListApiResponse<DeviceReportResponseDto> response = reportService
				.generateDeviceReport(deviceReportRequestDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Method to generate notify error report.
	 * 
	 * @param errorReportRequestDto
	 * @return
	 */
	@RequestMapping(value = "/errorReport", method = RequestMethod.POST)
	public ResponseEntity<ListApiResponse<ErrorReportResponseDto>> generateErrorReport(
			@RequestBody ErrorReportRequestDto errorReportRequestDto) {
		ListApiResponse<ErrorReportResponseDto> response = reportService
				.generateNotifyErrorReport(errorReportRequestDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
