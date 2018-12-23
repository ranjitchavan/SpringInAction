package com.einfochips.webportal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the device_provider_certificate database table.
 * 
 */
@Entity
@Table(name = "device_provider_certificate")
@NamedQuery(name = "DeviceProviderCertificate.findAll", query = "SELECT d FROM DeviceProviderCertificate d")
public class DeviceProviderCertificate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "device_provider_certificate_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int deviceProviderCertificateId;

	@Lob
	private byte[] certificate;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "dp_certificate_identifier")
	private String dpCertificateIdentifier;

	@Column(name = "dp_id")
	private String dpId;

	@Column(name = "dp_private_key_label")
	private String dpPrivateKeyLabel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date")
	private Date expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date")
	private Date fromDate;

	private byte revoked;

	@Lob
	@Column(name = "uidai_root_certificate")
	private byte[] uidaiRootCertificate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_time")
	private Date updationTime;

	// bi-directional many-to-one association to DeviceMaster
	@OneToMany(mappedBy = "deviceProviderCertificate")
	private List<DeviceMaster> deviceMasters;

	@Column(name = "customer_id")
	private Integer customerId;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public DeviceProviderCertificate() {
		// default constructor
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

	public List<DeviceMaster> getDeviceMasters() {
		return this.deviceMasters;
	}

	public void setDeviceMasters(List<DeviceMaster> deviceMasters) {
		this.deviceMasters = deviceMasters;
	}

	public DeviceMaster addDeviceMaster(DeviceMaster deviceMaster) {
		getDeviceMasters().add(deviceMaster);
		deviceMaster.setDeviceProviderCertificate(this);

		return deviceMaster;
	}

	public DeviceMaster removeDeviceMaster(DeviceMaster deviceMaster) {
		getDeviceMasters().remove(deviceMaster);
		deviceMaster.setDeviceProviderCertificate(null);

		return deviceMaster;
	}

	public String getDpPrivateKeyLabel() {
		return dpPrivateKeyLabel;
	}

	public void setDpPrivateKeyLabel(String dpPrivateKeyLabel) {
		this.dpPrivateKeyLabel = dpPrivateKeyLabel;
	}

}