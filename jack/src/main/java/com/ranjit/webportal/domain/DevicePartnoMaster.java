package com.einfochips.webportal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the device_partno_master database table.
 * 
 * @author asra.shaikh
 */
@Entity
@Table(name = "device_partno_master")
@NamedQuery(name = "DevicePartnoMaster.findAll", query = "SELECT d FROM DevicePartnoMaster d")
public class DevicePartnoMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "device_partno_master_id")
	private int devicePartnoMasterId;

	@Column(name = "device_type")
	private String deviceType;

	private String mi;

	@Column(name = "part_no")
	private String partNo;

	@Column(name = "application_type")
	private String applicationType;

	public DevicePartnoMaster() {
		// default constructor
	}

	public int getDevicePartnoMasterId() {
		return this.devicePartnoMasterId;
	}

	public void setDevicePartnoMasterId(int devicePartnoMasterId) {
		this.devicePartnoMasterId = devicePartnoMasterId;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getMi() {
		return this.mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

}