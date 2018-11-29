package com.einfochips.webportal.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.dto.DeviceReportRequestDto;
import com.einfochips.webportal.dto.DeviceReportResponseDto;
import com.einfochips.webportal.dto.ErrorReportRequestDto;
import com.einfochips.webportal.dto.ErrorReportResponseDto;
import com.einfochips.webportal.dto.ListApiResponse;
import com.einfochips.webportal.repositories.CustomReportRepository;
import com.einfochips.webportal.services.ReportService;
import com.einfochips.webportal.utility.Constants;
import com.einfochips.webportal.utility.FieldValidator;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public class ReportServiceImpl implements ReportService {
	private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

	@Autowired
	private CustomReportRepository customReportRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ListApiResponse<DeviceReportResponseDto> generateDeviceReport(
			DeviceReportRequestDto deviceReportRequestDto) {
		LOGGER.info("deviceReportRequestDto:" + deviceReportRequestDto);
		List<DeviceReportResponseDto> deviceReportResponseDtoList = null;

		String error = validateRequest(deviceReportRequestDto);
		if (FieldValidator.isNull(error)) {
			deviceReportResponseDtoList = customReportRepository
					.filterDevices(deviceReportRequestDto);
			LOGGER.info("Searched result size :"
					+ (FieldValidator.isNull(deviceReportResponseDtoList) ? 0
							: deviceReportResponseDtoList.size()));
		}
		ListApiResponse<DeviceReportResponseDto> response = new ListApiResponse<>(error,
				deviceReportResponseDtoList);
		LOGGER.info("Response : ->" + response);
		return response;
	}

	@Override
	public ListApiResponse<ErrorReportResponseDto> generateNotifyErrorReport(
			ErrorReportRequestDto errorReportRequestDto) {
		LOGGER.info("errorReportRequestDto:" + errorReportRequestDto);
		List<ErrorReportResponseDto> errorReportResponseDtoList = null;

		String error = validateRequest(errorReportRequestDto);
		if (FieldValidator.isNull(error)) {
			errorReportResponseDtoList = customReportRepository
					.filterErrorLogs(errorReportRequestDto);
			LOGGER.info("Searched result size :"
					+ (FieldValidator.isNull(errorReportResponseDtoList) ? 0
							: errorReportResponseDtoList.size()));
		}
		ListApiResponse<ErrorReportResponseDto> response = new ListApiResponse<>(error,
				errorReportResponseDtoList);
		LOGGER.info("Response : ->" + response);

		return response;
	}

	private String validateRequest(DeviceReportRequestDto deviceReportRequestDto) {
		String error = "";
		boolean invalidRequest = FieldValidator
				.isNull(deviceReportRequestDto.getDeviceId())
				&& FieldValidator.isNull(deviceReportRequestDto.getDeviceStatus());
		if (invalidRequest) {
			invalidRequest = (deviceReportRequestDto.getCustomerId() == null)
					&& (deviceReportRequestDto.getStartDate() == null)
					&& (deviceReportRequestDto.getEndDate() == null);
		}
		if (invalidRequest) {
			invalidRequest = (!deviceReportRequestDto.isL0Device())
					&& (!deviceReportRequestDto.isL1Device());
		}

		// validate deviceId format
		if (!invalidRequest) {
			String deviceId = deviceReportRequestDto.getDeviceId();
			if (!FieldValidator.isNull(deviceId)) {
				error = validateDeviceId(deviceId);
			}
		}
		else {
			error = Constants.ERROR_INVALID_INPUT_DEVICE_REPORT;
			LOGGER.error(Constants.ERROR_INVALID_INPUT_DEVICE_REPORT);
		}

		return error;
	}

	private String validateRequest(ErrorReportRequestDto errorReportRequestDto) {
		String error = "";
		boolean invalidRequest = FieldValidator
				.isNull(errorReportRequestDto.getDeviceId());

		if (invalidRequest) {
			invalidRequest = (errorReportRequestDto.getCustomerId() == null)
					&& (errorReportRequestDto.getStartDate() == null)
					&& (errorReportRequestDto.getEndDate() == null);
		}
		if (invalidRequest) {
			invalidRequest = (!errorReportRequestDto.isL0Device())
					&& (!errorReportRequestDto.isL1Device());
		}

		// validate deviceId format
		if (!invalidRequest) {
			String deviceId = errorReportRequestDto.getDeviceId();
			if (!FieldValidator.isNull(deviceId)) {
				error = validateDeviceId(deviceId);
			}

		}
		else {
			error = Constants.ERROR_INVALID_INPUT_ERROR_REPORT;
			LOGGER.error(Constants.ERROR_INVALID_INPUT_ERROR_REPORT);
		}
		return error;
	}

	private String validateDeviceId(String deviceId) {

		String error = validateLength(deviceId);
		if (FieldValidator.isNull(error)) {
			String[] deviceParts = deviceId.split("-");
			// check for null
			error = checkForNull(deviceParts);
			if (FieldValidator.isNull(error)) {
				error = validateDeviceIdFormat(deviceParts);
			}
		}

		LOGGER.error("Error in deviceId validation ->  " + error);
		return error;
	}

	private String validateLength(String deviceId) {
		String error = "";
		if (deviceId.contains("-")) {
			if (deviceId.length() > 24) {
				error = Constants.ERROR_DEVICEID_LENGTH_EXCEEDED;
			}
		}
		else {
			error = Constants.ERROR_MISSING_SERIAL_NO_MSG;
		}
		return error;
	}

	private String validateDeviceIdFormat(String[] deviceParts) {
		String error = "";
		// validate part no.
		if ((deviceParts[0].length() < 3)
				|| (!"000".equalsIgnoreCase(deviceParts[0].substring(0, 3)))) {
			error = Constants.ERROR_INVALID_PART_NO_MSG;
		}
		else {
			// validate serial no.
			if (deviceParts[1].length() > 4) {
				char manufacturingPlant = deviceParts[1].charAt(4);
				// check serial number validation
				if (!(Character.isLetter(manufacturingPlant)
						&& (Character.toLowerCase(manufacturingPlant) == 's'
								|| Character.toLowerCase(manufacturingPlant) == 'i'))) {
					error = Constants.ERROR_INVALID_SERIAL_NO_MSG;
				}
			}
			else {
				error = Constants.ERROR_INVALID_SERIAL_NO_MSG;
			}

		}
		return error;
	}

	private String checkForNull(String[] deviceParts) {
		LOGGER.info("deviceParts length: ->" + deviceParts.length);
		String error = "";
		if (deviceParts.length == 0) {
			error = Constants.ERROR_MISSING_PART_AND_SERIAL_NO_MSG;
		}
		else {
			if (FieldValidator.isNull(deviceParts[0])) {
				error = Constants.ERROR_MISSING_PART_NO_MSG;
			}
			else if ((deviceParts.length < 2)
					|| (FieldValidator.isNull(deviceParts[1]))) {
				error = Constants.ERROR_MISSING_SERIAL_NO_MSG;
			}
		}

		return error;
	}

}
