package com.einfochips.webportal.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the device_provider_certificate database table.
 * 
 */

public class DeviceProviderCertificateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int deviceProviderCertificateId;

	private byte[] certificate;

	private String createdBy;

	private Date creationTime;

	private String dpCertificateIdentifier;

	private String dpId;

	private String dpPrivateKeyLabel;

	private Integer customerId;
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getDpPrivateKeyLabel() {
		return dpPrivateKeyLabel;
	}

	public void setDpPrivateKeyLabel(String dpPrivateKeyLabel) {
		this.dpPrivateKeyLabel = dpPrivateKeyLabel;
	}

	private Date expiryDate;

	private Date fromDate;

	private byte revoked;

	private byte[] uidaiRootCertificate;

	private Date updationTime;

	public DeviceProviderCertificateDTO() {
		// default constructory
	}

	public int getDevicePeroviderCertificateId() {
		return this.deviceProviderCertificateId;
	}

	public void setDeviceProviderCertificateId(int deviceProviderCertificateId) {
		this.deviceProviderCertificateId = deviceProviderCertificateId;
	}

	public byte[] getCertificate() {
		return this.certificate;
	}

	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getDpCertificateIdentifier() {
		return this.dpCertificateIdentifier;
	}

	public void setDpCertificateIdentifier(String dpCertificateIdentifier) {
		this.dpCertificateIdentifier = dpCertificateIdentifier;
	}

	public String getDpId() {
		return this.dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	public int getDeviceProviderCertificateId() {
		return deviceProviderCertificateId;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public byte getRevoked() {
		return this.revoked;
	}

	public void setRevoked(byte revoked) {
		this.revoked = revoked;
	}

	public byte[] getUidaiRootCertificate() {
		return this.uidaiRootCertificate;
	}

	public void setUidaiRootCertificate(byte[] uidaiRootCertificate) {
		this.uidaiRootCertificate = uidaiRootCertificate;
	}

	public Date getUpdationTime() {
		return this.updationTime;
	}

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

}