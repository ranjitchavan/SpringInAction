package com.einfochips.webportal.domain;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author darshan.sathwara
 *
 */
@Entity
public class VersionManagement {
	@Id
	@GeneratedValue
	private Integer version_management_id;

	@Value("application_type")
	private String applicationType;

	private String os;

	@Value("os_type")
	private String osType;

	@Value("current_version")
	private String currentVersion;

	private String file_name;

	@Lob
	private Blob current_version_location;

	@Value("is_current")
	private int isCurrent;

	private int created_by;
	private Date creation_time;
	private Date updation_time;

	@Column(name = "dp_id")
	private String dpId;

	@Lob
	@Column(name = "signature_file")
	private byte[] signatureFile;

	@Column(name = "signature_file_name")
	private String signatureFileName;

	public VersionManagement() {
		// default constructor
	}

	public Integer getVersion_management_id() {
		return version_management_id;
	}

	public void setVersion_management_id(Integer version_management_id) {
		this.version_management_id = version_management_id;
	}

	public String getApplication_type() {
		return applicationType;
	}

	public void setApplication_type(String application_type) {
		this.applicationType = application_type;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCurrent_version() {
		return currentVersion;
	}

	public void setCurrent_version(String current_version) {
		this.currentVersion = current_version;
	}

	public java.sql.Blob getCurrent_version_location() {
		return current_version_location;
	}

	public void setCurrent_version_location(java.sql.Blob current_version_location) {
		this.current_version_location = current_version_location;
	}

	public int getisCurrent() {
		return isCurrent;
	}

	public void setisCurrent(int is_current) {
		this.isCurrent = is_current;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public Date getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}

	public Date getUpdation_time() {
		return updation_time;
	}

	public void setUpdation_time(Date updation_time) {
		this.updation_time = updation_time;
	}

	public byte[] getSignatureFile() {
		return this.signatureFile;
	}

	public void setSignatureFile(byte[] signatureFile) {
		this.signatureFile = signatureFile;
	}

	public String getSignatureFileName() {
		return this.signatureFileName;
	}

	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}


	public String getDpId() {
		return dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VersionManagement [version_management_id=")
				.append(version_management_id).append(", applicationType=")
				.append(applicationType).append(", os=").append(os)
				.append(", currentVersion=").append(currentVersion).append(", file_name=")
				.append(file_name).append(", current_version_location=")
				.append(current_version_location).append(", isCurrent=").append(isCurrent)
				.append(", created_by=").append(created_by).append(", creation_time=")
				.append(creation_time).append(", updation_time=").append(updation_time)
				.append(", signatureFile=").append(Arrays.toString(signatureFile))
				.append(", signatureFileName=").append(signatureFileName).append("]");
		return builder.toString();
	}

}