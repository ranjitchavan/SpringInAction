package com.einfochips.webportal.dto;

import java.util.Arrays;

/**
 * This class contains filter fields to generate device report.
 * 
 * @author asra.shaikh
 *
 */
public class DeviceReportRequestDto extends GenericDeviceDto {
	private Integer[] customerId;
	private Long startDate;
	private Long endDate;
	private boolean l0Device;
	private boolean l1Device;
	private String desktopPN;
	private String desktopSN;
	private String partNo;
	private String serialNo;

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

	public Integer[] getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer[] customerId) {
		this.customerId = customerId;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public boolean isL0Device() {
		return l0Device;
	}

	public void setL0Device(boolean l0Device) {
		this.l0Device = l0Device;
	}

	public boolean isL1Device() {
		return l1Device;
	}

	public void setL1Device(boolean l1Device) {
		this.l1Device = l1Device;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceReportRequestDto [customerId=")
				.append(Arrays.toString(customerId)).append(", startDate=")
				.append(startDate).append(", endDate=").append(endDate)
				.append(", l0Device=").append(l0Device).append(", l1Device=")
				.append(l1Device).append(", desktopPN=").append(desktopPN)
				.append(", desktopSN=").append(desktopSN).append(", partNo=")
				.append(partNo).append(", serialNo=").append(serialNo).append("]");
		return builder.toString();
	}

}
