package com.einfochips.webportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.einfochips.webportal.dto.ErrorReportResponseDto;

/**
 * The persistent class for the error_log database table.
 * 
 * @author asra.shaikh
 * 
 */
@SqlResultSetMapping(name = "ErrorReportResults", classes = @ConstructorResult(targetClass = ErrorReportResponseDto.class, columns = {
		@ColumnResult(name = "deviceId"), @ColumnResult(name = "serialNumber"),
		@ColumnResult(name = "partNumber"), @ColumnResult(name = "customerName"),
		@ColumnResult(name = "requestType"),
		@ColumnResult(name = "attemptNumber", type = Integer.class),
		@ColumnResult(name = "creationTime", type = Date.class),
		@ColumnResult(name = "comments") }))
@Entity
@Table(name = "error_log")
@NamedQuery(name = "ErrorLog.findAll", query = "SELECT e FROM ErrorLog e")
public class ErrorLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "error_log_id")
	private String errorLogId;

	@Column(name = "attempt_number")
	private int attemptNumber;

	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "request_id")
	private String requestId;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "response_error")
	private String responseError;

	public ErrorLog() {
		// default constructor
	}

	public String getErrorLogId() {
		return this.errorLogId;
	}

	public void setErrorLogId(String errorLogId) {
		this.errorLogId = errorLogId;
	}

	public int getAttemptNumber() {
		return this.attemptNumber;
	}

	public void setAttemptNumber(int attemptNumber) {
		this.attemptNumber = attemptNumber;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getResponseError() {
		return this.responseError;
	}

	public void setResponseError(String responseError) {
		this.responseError = responseError;
	}

}