package com.einfochips.webportal.domain;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the uidai_public_certificates database table.
 */

@Entity
@Table(name = "uidai_public_certificates")
@NamedQuery(name = "UidaiPublicCertificate.findAll", query = "SELECT u FROM UidaiPublicCertificate u")
public class UidaiPublicCertificate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uidai_certificate_id")
	private int uidaiCertificateId;

	@Lob
	private byte[] certificate;

	@Column(name = "certificate_type")
	private String certificateType;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date")
	private Date expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date")
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_time")
	private Date updationTime;

	public UidaiPublicCertificate() {
		// default constructor
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

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
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

}