package com.einfochips.webportal.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class UidaiPublicCertificateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int uidaiCertificateId;

	private byte[] certificate;

	private String certificateType;

	private String createdBy;

	private Date creationTime;

	private Date expiryDate;

	private Date fromDate;

	private Date updationTime;

	public UidaiPublicCertificateDTO() {
		// default constructory
	}

	public int getUidaiCertificateId() {
		return this.uidaiCertificateId;
	}

	public void setUidaiCertificateId(int uidaiCertificateId) {
		this.uidaiCertificateId = uidaiCertificateId;
	}

	public byte[] getCertificate() {
		return this.certificate;
	}

	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}

	public String getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
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

	public Date getUpdationTime() {
		return this.updationTime;
	}

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

	@Override
	public String toString() {
		return "UidaiPublicCertificateDTO [uidaiCertificateId=" + uidaiCertificateId
				+ ", certificate=" + Arrays.toString(certificate) + ", certificateType="
				+ certificateType + ", createdBy=" + createdBy + ", creationTime="
				+ creationTime + ", expiryDate=" + expiryDate + ", fromDate=" + fromDate
				+ ", updationTime=" + updationTime + "]";
	}

}