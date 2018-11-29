package com.einfochips.webportal.services;

import org.springframework.stereotype.Service;

import com.einfochips.webportal.dto.DeviceReportRequestDto;
import com.einfochips.webportal.dto.DeviceReportResponseDto;
import com.einfochips.webportal.dto.ErrorReportRequestDto;
import com.einfochips.webportal.dto.ErrorReportResponseDto;
import com.einfochips.webportal.dto.ListApiResponse;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public interface ReportService {
	/**
	 * This method is generate device report.
	 * 
	 * @param deviceReportRequestDto
	 * @return
	 */
	public ListApiResponse<DeviceReportResponseDto> generateDeviceReport(
			DeviceReportRequestDto deviceReportRequestDto);

	/**
	 * This method is generate notify report.
	 * 
	 * @param errorReportRequestDto
	 * @return
	 */
	public ListApiResponse<ErrorReportResponseDto> generateNotifyErrorReport(
			ErrorReportRequestDto errorReportRequestDto);

}
