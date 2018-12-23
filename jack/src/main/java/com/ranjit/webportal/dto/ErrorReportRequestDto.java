package com.einfochips.webportal.dto;

import java.util.Arrays;

/**
 * Class representing search criteria for error reporting.
 * 
 * @author asra.shaikh
 *
 */
public class ErrorReportRequestDto {
	private String deviceId;
	private Integer[] customerId;
	private Long startDate;
	private Long endDate;
	private boolean l0Device;
	private boolean l1Device;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
		builder.append("ErrorReportRequestDto [deviceId=").append(deviceId)
				.append(", customerId=").append(Arrays.toString(customerId))
				.append(", startDate=").append(startDate).append(", endDate=")
				.append(endDate).append(", l0Device=").append(l0Device)
				.append(", l1Device=").append(l1Device).append("]");
		return builder.toString();
	}

}
