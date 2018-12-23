package com.einfochips.webportal.dto;

/**
 * 
 * @author asra.shaikh
 *
 */
public class GenericDeviceDto {
	private String deviceId;
	private String deviceStatus;

	/**
	 * default constructor
	 */
	public GenericDeviceDto() {
		super();
	}

	/**
	 * @param deviceId
	 * @param deviceStatus
	 */
	public GenericDeviceDto(String deviceId, String deviceStatus) {
		super();
		this.deviceId = deviceId;
		this.deviceStatus = deviceStatus;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericDeviceDto [deviceId=").append(deviceId)
				.append(", deviceStatus=").append(deviceStatus).append("]");
		return builder.toString();
	}

}
