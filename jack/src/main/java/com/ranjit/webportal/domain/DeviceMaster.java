package com.einfochips.webportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.einfochips.webportal.dto.DeviceReportResponseDto;

/** The persistent class for the device_master database table. */
@SqlResultSetMapping(name = "DeviceReportResults", classes = @ConstructorResult(targetClass = DeviceReportResponseDto.class, columns = {
		@ColumnResult(name = "deviceId"), @ColumnResult(name = "deviceStatus"),
		@ColumnResult(name = "customerName"),
		@ColumnResult(name = "expiryDate", type = Date.class),
		@ColumnResult(name = "deviceType"), @ColumnResult(name = "desktopPN"),
		@ColumnResult(name = "desktopSN"), @ColumnResult(name = "partNo"),
		@ColumnResult(name = "serialNo") }))
@Entity
@Table(name = "device_master")
@NamedQuery(name = "DeviceMaster.findAll", query = "SELECT d FROM DeviceMaster d")
public class DeviceMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "device_master_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long deviceMasterId;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "device_certificate_expiry_date")
	private Date deviceCertificateExpiryDate;

	@Column(name = "device_code")
	private String deviceCode;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "part_no")
	private String partNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registered_date")
	private Date registeredDate;

	@Column(name = "serial_no")
	private String serialNo;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_time")
	private Date updationTime;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "desktop_pn")
	private String desktopPN;

	@Column(name = "desktop_sn")
	private String desktopSN;

	@Column(name = "firmware_version")
	private String firmwareVersion;

	// bi-directional many-to-one association to CustomerMaster
	@ManyToOne
	@JoinColumn(name = "customer_master_id")
	private CustomerMaster customerMaster;

	// bi-directional many-to-one association to DeviceProviderCertificate
	@ManyToOne
	@JoinColumn(name = "device_provider_certificate_id")
	private DeviceProviderCertificate deviceProviderCertificate;

	@Column(name = "device_type")
	private String deviceType;

	@Column(name = "enable_migration")
	private String enableMigration;

	public DeviceMaster() {
		// default constructor
	}

	public long getDeviceMasterId() {
		return this.deviceMasterId;
	}

	public void setDeviceMasterId(long deviceMasterId) {
		this.deviceMasterId = deviceMasterId;
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

	public Date getDeviceCertificateExpiryDate() {
		return this.deviceCertificateExpiryDate;
	}

	public void setDeviceCertificateExpiryDate(Date deviceCertificateExpiryDate) {
		this.deviceCertificateExpiryDate = deviceCertificateExpiryDate;
	}

	public String getDeviceCode() {
		return this.deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Date getRegisteredDate() {
		return this.registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdationTime() {
		return this.updationTime;
	}

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

	public CustomerMaster getCustomerMaster() {
		return this.customerMaster;
	}

	public void setCustomerMaster(CustomerMaster customerMaster) {
		this.customerMaster = customerMaster;
	}

	public DeviceProviderCertificate getDeviceProviderCertificate() {
		return this.deviceProviderCertificate;
	}

	public void setDeviceProviderCertificate(
			DeviceProviderCertificate deviceProviderCertificate) {
		this.deviceProviderCertificate = deviceProviderCertificate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDesktopPN() {
		return desktopPN;
	}

	public void setDesktopPN(String desktopPN) {
		this.desktopPN = desktopPN;
	}

	public String getDesktopSN() {
		return desktopSN;
	}

	public void setDesktopSN(String desktopSN) {
		this.desktopSN = desktopSN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	// ADDED changes for L0 to L1 migration START

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getEnableMigration() {
		return enableMigration;
	}

	public void setEnableMigration(String enableMigration) {
		this.enableMigration = enableMigration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceMaster other = (DeviceMaster) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		}
		else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}

	// ADDED changes for L0 to L1 migration END
	@Override
	public String toString() {
		return "DeviceMaster [deviceMasterId=" + deviceMasterId + ", createdBy="
				+ createdBy + ", creationTime=" + creationTime
				+ ", deviceCertificateExpiryDate=" + deviceCertificateExpiryDate
				+ ", deviceCode=" + deviceCode + ", deviceId=" + deviceId + ", partNo="
				+ partNo + ", registeredDate=" + registeredDate + ", serialNo=" + serialNo
				+ ", status=" + status + ", updationTime=" + updationTime
				+ ", productName=" + productName + ", desktopPN=" + desktopPN
				+ ", desktopSN=" + desktopSN + ", firmwareVersion=" + firmwareVersion
				+ ", customerMaster=" + customerMaster + ", deviceProviderCertificate="
				+ deviceProviderCertificate + ", deviceType=" + deviceType
				+ ", enableMigration=" + enableMigration + "]";
	}

}