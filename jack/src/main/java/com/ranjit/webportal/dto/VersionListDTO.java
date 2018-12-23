package com.einfochips.webportal.dto;

import java.util.Date;

public class VersionListDTO {

	private int version_management_id;
	private String application_type;
	private String osType;
	private String os;
	private String current_version;
	private Date creation_time;
	private String userName;
	private boolean signatureExist;
	private String dpId;

	public int getVersion_management_id() {
		return version_management_id;
	}

	public void setVersion_management_id(int version_management_id) {
		this.version_management_id = version_management_id;
	}

	public String getApplication_type() {
		return application_type;
	}

	public void setApplication_type(String application_type) {
		this.application_type = application_type;
	}

	public String getOsType() {
		return osType;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getCurrent_version() {
		return current_version;
	}

	public void setCurrent_version(String current_version) {
		this.current_version = current_version;
	}

	public Date getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isSignatureExist() {
		return signatureExist;
	}

	public void setSignatureExist(boolean signatureExist) {
		this.signatureExist = signatureExist;
	}

	public String getDpId() {
		return dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

}
