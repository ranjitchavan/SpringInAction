package com.einfochips.webportal.repositories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.einfochips.webportal.dto.DeviceReportRequestDto;
import com.einfochips.webportal.dto.DeviceReportResponseDto;
import com.einfochips.webportal.dto.ErrorReportRequestDto;
import com.einfochips.webportal.dto.ErrorReportResponseDto;

/**
 * Custom repository to filter error logs to generate reports.
 * 
 * @author asra.shaikh
 *
 */
@Component
public interface CustomReportRepository {

	/**
	 * This method filters error logs by device Id, customer, date range and device
	 * type;L0 OR L1.
	 * 
	 * @param errorReportRequestDto : contains input parameter(s) to filter report
	 * @return : List of filtered error log(s)
	 */
	List<ErrorReportResponseDto> filterErrorLogs(
			ErrorReportRequestDto errorReportRequestDto);

	/**
	 * This method filters devices by deviceId, customer, date(range), device status and
	 * type.
	 * 
	 * @param deviceReportRequestDto
	 * @return
	 */
	List<DeviceReportResponseDto> filterDevices(
			DeviceReportRequestDto deviceReportRequestDto);
}
