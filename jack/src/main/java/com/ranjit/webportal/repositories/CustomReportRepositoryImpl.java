package com.einfochips.webportal.repositories;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.einfochips.webportal.dto.DeviceReportRequestDto;
import com.einfochips.webportal.dto.DeviceReportResponseDto;
import com.einfochips.webportal.dto.ErrorReportRequestDto;
import com.einfochips.webportal.dto.ErrorReportResponseDto;
import com.einfochips.webportal.utility.Constants;
import com.einfochips.webportal.utility.FieldValidator;

/**
 * Custom repository to provide dynamic and custom error report filtering.
 * 
 * 
 * @author asra.shaikh
 *
 */
@Component
public class CustomReportRepositoryImpl implements CustomReportRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger LOGGER = Logger.getLogger(CustomReportRepository.class);

	@Override
	public List<ErrorReportResponseDto> filterErrorLogs(
			ErrorReportRequestDto errorReportRequestDto) {

		LOGGER.info("Filtering error logs..");

		String selectStatement = "SELECT  e.device_id as deviceId, e.creation_time as creationTime, e.request_type as requestType,  e.attempt_number as attemptNumber, e.comments as comments,"
				+ " IFNULL(d.part_no, (SUBSTRING(e.device_id, 1,LOCATE('-',e.device_id)-1))) as partNumber, IFNULL(d.serial_no, (SUBSTRING(e.device_id, LOCATE('-',e.device_id)+1,LENGTH(e.device_id)))) as serialNumber,"
				+ " c.customer_name as customerName" + " from error_log e"
				+ " LEFT JOIN device_master d ON e.device_id = d.device_id"
				+ " LEFT JOIN customer_master c ON d.customer_master_id = c.customer_master_id";

		String whereCriteria = constructQueryCriteria(errorReportRequestDto);

		Query nativeQuery = entityManager
				.createNativeQuery(selectStatement + whereCriteria, "ErrorReportResults");

		@SuppressWarnings("unchecked")
		List<ErrorReportResponseDto> errorReportResponseDtoList = (List<ErrorReportResponseDto>) nativeQuery
				.getResultList();
		LOGGER.info("Filtered list size is : -> " + errorReportResponseDtoList.size());
		return errorReportResponseDtoList;
	}

	@Override
	public List<DeviceReportResponseDto> filterDevices(
			DeviceReportRequestDto deviceReportRequestDto) {
		LOGGER.info("Filtering devices..");

		String selectStatement = "SELECT d.device_id as deviceId, d.status as deviceStatus, d.device_certificate_expiry_date as expiryDate, "
				+ "c.customer_name as customerName, " + "d.device_type as deviceType, "
				+ "d.desktop_pn as desktopPN, " + "d.desktop_sn as desktopSN, "
				+ "d.serial_no as serialNo, " + "d.part_no as partNo "
				+ "from device_master d "
				+ "JOIN customer_master c ON d.customer_master_id = c.customer_master_id ";

		String whereCriteria = constructQueryCriteria(deviceReportRequestDto);

		Query nativeQuery = entityManager.createNativeQuery(
				selectStatement + whereCriteria, "DeviceReportResults");

		@SuppressWarnings("unchecked")
		List<DeviceReportResponseDto> deviceReportResponseDtoList = (List<DeviceReportResponseDto>) nativeQuery
				.getResultList();

		LOGGER.info("Filtered device list size is : -> "
				+ deviceReportResponseDtoList.size());

		return deviceReportResponseDtoList;
	}

	private String constructQueryCriteria(ErrorReportRequestDto errorReportRequestDto) {

		String where = "";

		if (!FieldValidator.isNull(errorReportRequestDto.getDeviceId())) {
			where += " AND e.device_id = '" + errorReportRequestDto.getDeviceId().trim()
					+ "'";
		}
		where += constructCustomerCriteria(errorReportRequestDto.getCustomerId());

		if ((errorReportRequestDto.getStartDate() != null)
				&& (errorReportRequestDto.getEndDate() != null)) {
			Timestamp startDate = new Timestamp(errorReportRequestDto.getStartDate());
			Timestamp endDate = new Timestamp(errorReportRequestDto.getEndDate());
			LOGGER.info("startDate:" + startDate);
			LOGGER.info("endDate:" + endDate);
			where += " AND (e.creation_time BETWEEN '" + startDate + "' AND '" + endDate
					+ "')";
		}
		where += constructDeviceTypeCriteria(errorReportRequestDto.isL0Device(),
				errorReportRequestDto.isL1Device());
		where = formatCriteria(where);

		return where;
	}

	private String constructQueryCriteria(DeviceReportRequestDto deviceReportRequestDto) {

		String where = "";

		where += constructDeviceIdCriteria(deviceReportRequestDto.getDeviceId().trim());
		where += constructCustomerCriteria(deviceReportRequestDto.getCustomerId());
		where += constructDateCriteria(deviceReportRequestDto.getStartDate(),
				deviceReportRequestDto.getEndDate());
		// Adding condition for Desktop P/N number
		where += constructDesktopPNCtriteria(deviceReportRequestDto.getDesktopPN());
		// Adding condition for Desktop S/N number
		where += constructDesktopSNCtriteria(deviceReportRequestDto.getDesktopSN());
		// Adding condition for for OEM serial number
		where += constructOEMSNCtriteria(deviceReportRequestDto.getSerialNo());
		// Adding condition for for OEM part number
		where += constructOEMPNCtriteria(deviceReportRequestDto.getPartNo());

		if (!FieldValidator.isNull(deviceReportRequestDto.getDeviceStatus())) {
			where += " AND LOWER(TRIM(d.status)) like '"
					+ deviceReportRequestDto.getDeviceStatus().trim().toLowerCase() + "'";
		}
		where += constructDeviceTypeCriteria(deviceReportRequestDto.isL0Device(),
				deviceReportRequestDto.isL1Device());

		where = formatCriteria(where);
		return where;
	}

	// Method will use match condition with desktop pn number form device master
	private String constructDesktopPNCtriteria(String desktopPN) {
		String criteria = "";
		if (!FieldValidator.isNull(desktopPN)) {
			criteria += " AND d.desktop_pn = '" + desktopPN + "'";
		}
		return criteria;
	}

	// Method will use match condition with desktop sn number form device master
	private String constructDesktopSNCtriteria(String desktopSN) {
		String criteria = "";
		if (!FieldValidator.isNull(desktopSN)) {
			criteria += " AND d.desktop_sn = '" + desktopSN + "'";
		}
		return criteria;
	}

	// Method will use match condition with OEM sn number form device master
	private String constructOEMSNCtriteria(String serialNo) {
		String criteria = "";
		if (!FieldValidator.isNull(serialNo)) {
			criteria += " AND d.serial_no = '" + serialNo + "'";
		}
		return criteria;
	}

	// Method will use match condition with OEM pn number form device master
	private String constructOEMPNCtriteria(String partNo) {
		String criteria = "";
		if (!FieldValidator.isNull(partNo)) {
			criteria += " AND d.part_no = '" + partNo + "'";
		}
		return criteria;
	}

	private String constructDeviceIdCriteria(String deviceId) {
		String criteria = "";
		if (!FieldValidator.isNull(deviceId)) {
			criteria += " AND d.device_id = '" + deviceId + "'";
		}
		return criteria;
	}

	private String constructCustomerCriteria(Integer[] customerIdAry) {
		String criteria = "";
		StringBuilder customerIdSb = new StringBuilder("(");

		if ((customerIdAry != null) && (customerIdAry.length > 0)) {

			for (int i = 0; i < customerIdAry.length; i++) {
				customerIdSb.append(customerIdAry[i] + ",");
			}

			String customerIds = customerIdSb.substring(0, customerIdSb.lastIndexOf(","))
					+ ")";
			LOGGER.info("customerIds  -> " + customerIds);

			criteria += " AND c.customer_master_id in " + customerIds;
		}

		return criteria;
	}

	private String constructDateCriteria(Long startDate, Long endDate) {
		String criteria = "";
		if ((startDate != null) && (endDate != null)) {
			Timestamp startDateInTimestamp = new Timestamp(startDate);
			Timestamp endDateInTimestamp = new Timestamp(endDate);
			LOGGER.info("startDate:" + startDate);
			LOGGER.info("endDate:" + endDate);
			criteria += " AND (d.updation_time BETWEEN '" + startDateInTimestamp
					+ "' AND '" + endDateInTimestamp + "')";
		}
		return criteria;
	}

	private String constructDeviceTypeCriteria(boolean l0Device, boolean l1Device) {
		String criteria = "";
		if (l0Device && l1Device) {
			criteria += " AND ((LOWER(TRIM(d.device_type)) like '"
					+ Constants.DEVICE_TYPE_L0.toLowerCase()
					+ "') ||  (LOWER(TRIM(d.device_type)) like '"
					+ Constants.DEVICE_TYPE_L1.toLowerCase() + "'))";
		}
		else if (l0Device) {
			criteria += " AND LOWER(TRIM(d.device_type)) like '"
					+ Constants.DEVICE_TYPE_L0.toLowerCase() + "'";
		}
		else if (l1Device) {
			criteria += " AND LOWER(TRIM(d.device_type)) like '"
					+ Constants.DEVICE_TYPE_L1.toLowerCase() + "'";
		}

		return criteria;
	}

	private String formatCriteria(String where) {
		if (!where.isEmpty()) {
			where = where.substring(4, where.length());
			where = " where" + where;
			LOGGER.debug("where criteria :" + where);
		}
		return where;
	}

}
