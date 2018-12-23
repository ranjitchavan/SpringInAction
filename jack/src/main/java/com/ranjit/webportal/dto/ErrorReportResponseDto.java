package com.einfochips.webportal.dto;

import java.util.Date;

/**
 * Class representing notify error report response.
 * 
 * @author asra.shaikh
 *
 */
public class ErrorReportResponseDto {

	private String deviceId;
	private String serialNumber;
	private String partNumber;
	private String customerName;
	private String requestType;
	private int attemptNumber;
	private Date creationTime;
	private String comments;

	/**
	 * Constructor to create error report API response.
	 * 
	 * @param deviceId
	 * @param serialNumber
	 * @param partNumber
	 * @param customerName
	 * @param requestType
	 * @param attemptNumber
	 * @param creationTime
	 * @param comments
	 */
	public ErrorReportResponseDto(String deviceId, String serialNumber, String partNumber,
			String customerName, String requestType, int attemptNumber, Date creationTime,
			String comments) {
		super();
		this.deviceId = deviceId;
		this.serialNumber = serialNumber;
		this.partNumber = partNumber;
		this.customerName = customerName;
		this.requestType = requestType;
		this.attemptNumber = attemptNumber;
		this.creationTime = creationTime;
		this.comments = comments;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public int getAttemptNumber() {
		return attemptNumber;
	}

	public void setAttemptNumber(int attemptNumber) {
		this.attemptNumber = attemptNumber;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorReportResponseDto [deviceId=").append(deviceId)
				.append(", serialNumber=").append(serialNumber).append(", partNumber=")
				.append(partNumber).append(", customerName=").append(customerName)
				.append(", requestType=").append(requestType).append(", attemptNumber=")
				.append(attemptNumber).append(", creationTime=").append(creationTime)
				.append(", comments=").append(comments).append("]");
		return builder.toString();
	}

}
