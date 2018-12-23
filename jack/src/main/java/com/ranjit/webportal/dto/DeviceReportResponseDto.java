package com.einfochips.webportal.dto;

import java.util.Date;

/**
 * Class representing device report response.
 * 
 * @author asra.shaikh
 *
 */
public class DeviceReportResponseDto {
	private String customerName;
	private Date expiryDate;
	private String deviceType;
	private String desktopPN;
	private String desktopSN;
	private String deviceId;
	private String deviceStatus;
	private String partNo;
	private String serialNo;

	/**
	 * @param deviceId
	 * @param status
	 * @param customerName
	 * @param expiryDate
	 * @param deviceType
	 */

	public DeviceReportResponseDto(String deviceId, String deviceStatus,
			String customerName, Date expiryDate, String deviceType, String desktopPN,
			String desktopSN, String partNo, String serialNo) {
		this.deviceId = deviceId;
		this.deviceStatus = deviceStatus;
		this.customerName = customerName;
		this.expiryDate = expiryDate;
		this.deviceType = deviceType;
		this.desktopPN = desktopPN;
		this.desktopSN = desktopSN;
		this.partNo = partNo;
		this.serialNo = serialNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDesktopPN() {
		return desktopPN;
	}

	public void setDesktopPN(String desktopPN) {
		this.desktopPN = desktopPN;
	}

	public String getDesktopSN() {
		return desktopSN;
	}

	public void setDesktopSN(String desktopSN) {
		this.desktopSN = desktopSN;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceReportResponseDto [customerName=").append(customerName)
				.append(", expiryDate=").append(expiryDate)
				// .append(", desktopPN=").append(desktopPN)
				// .append(", desktopSN=").append(desktopSN)
				.append("]");
		return builder.toString();
	}

}
