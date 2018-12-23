package com.einfochips.webportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class OsMaster {
	@Id
	@Column(name = "os_master_id")
	private int os_master_id;
	
	@Value("os_name")
	private String osName;
	
	@Value("os_type")
	private String osType;
	
	

	public OsMaster() {
		// default constructor
	}

	public int getOs_master_id() {
		return os_master_id;
	}

	public void setOs_master_id(int os_master_id) {
		this.os_master_id = os_master_id;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	
}
