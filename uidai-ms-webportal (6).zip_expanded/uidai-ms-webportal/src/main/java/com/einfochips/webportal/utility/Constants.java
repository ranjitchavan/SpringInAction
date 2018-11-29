package com.einfochips.webportal.utility;

/**
 * 
 * @author asra.shaikh
 *
 */
public class Constants {
	private Constants() {

	}

	public static final String DEVICE_TYPE_L0 = "L0";
	public static final String DEVICE_TYPE_L1 = "L1";

	public static final String ERROR_MISSING_SERIAL_NO_MSG = "Serial number is missing from Device Id. Please provide valid Device Id with serial number.";
	public static final String ERROR_MISSING_PART_NO_MSG = "Part number is missing from Device Id. Please provide valid Device Id with part number.";
	public static final String ERROR_DEVICEID_LENGTH_EXCEEDED = "Exceeded maximum length allowed for Device Id.";
	public static final String ERROR_INVALID_PART_NO_MSG = "Format of part number of Device Id is not valid. Please provide Device Id with part number in proper format.";
	public static final String ERROR_INVALID_SERIAL_NO_MSG = "Format of serial number of Device Id is not valid. Please provide Device Id with serial number in proper format.";

	public static final String ERROR_INVALID_INPUT_DEVICE_REPORT = "Invalid request. Either Customer Name, Select Date (From - To), Device Id, Device Status or Device Level should be provided.";
	public static final String ERROR_INVALID_INPUT_ERROR_REPORT = "Invalid request. Either Customer Name, Select Date (From - To), Device Id or Device Level should be provided.";
	public static final String ERROR_MISSING_PART_AND_SERIAL_NO_MSG = "Device Id neither contains part number nor serial number. Please provide valid Device Id with part number and serial number.";

	public static final String DEVICE_STATUS_DE_REGISTERED = "Unregistered from Backend";
	public static final String DEVICE_STATUS_NOT_REGISTERED = "Not Registered";

	public static final String DEVICE_STATUS_UNREGISTERED_FROM_CLIENT = "Unregistered from Client";

	public static final String CERTIFICATE_VALIDATE_START_STRING = "-----BEGIN CERTIFICATE-----";
	public static final String CERTIFICATE_VALIDATE_END_STRING = "-----END CERTIFICATE-----";

	public static final String DISABLE_ENABLE_LOGS = "/uidai-ms-device-api/DISABLE_ENABLE_LOGS";
	public static final String GET_AUDIT_LOG_STATUS = "/uidai-ms-device-api/GET_AUDIT_LOG_STATUS";

}
