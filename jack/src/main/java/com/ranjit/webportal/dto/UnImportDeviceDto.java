package com.einfochips.webportal.dto;

public class UnImportDeviceDto {

	private String deviceId;
	private String partNo;
	private String serialNo;
	private String reasonOfFailure;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public String getReasonOfFailure() {
		return reasonOfFailure;
	}

	public void setReasonOfFailure(String reasonOfFailure) {
		this.reasonOfFailure = reasonOfFailure;
	}

}
