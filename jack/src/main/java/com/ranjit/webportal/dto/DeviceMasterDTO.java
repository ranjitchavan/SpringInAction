package com.einfochips.webportal.dto;

/**
 * Class representing device master response.
 * 
 * @author darshan.kadia
 *
 */

public class DeviceMasterDTO {

	private String deviceId;
	private String deviceCode;
	private String errorCode;
	private String errorDesc;
	private String status;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DeviceMasterDTO [deviceId=" + deviceId + ", deviceCode=" + deviceCode
				+ ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + "]";
	}

}
