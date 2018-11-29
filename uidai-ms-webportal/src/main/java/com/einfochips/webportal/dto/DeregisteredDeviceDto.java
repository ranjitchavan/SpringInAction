package com.einfochips.webportal.dto;

public class DeregisteredDeviceDto {

	private long deviceMasterId;
	private String deviceId;
	private String status;

	public long getDeviceMasterId() {
		return deviceMasterId;
	}

	public void setDeviceMasterId(long deviceMasterId) {
		this.deviceMasterId = deviceMasterId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeregisteredDeviceDto [deviceMasterId=").append(deviceMasterId)
				.append(", deviceId=").append(deviceId).append("]");
		return builder.toString();
	}

}
