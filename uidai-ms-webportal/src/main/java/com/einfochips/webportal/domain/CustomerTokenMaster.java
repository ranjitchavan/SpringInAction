package com.einfochips.webportal.domain;

/**
 * @author darshan.sathwara
 *
 */
public class CustomerTokenMaster {
	private String customerName;
	private Integer tokenNameId;
	private Integer customerMasterId;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getTokenNameId() {
		return tokenNameId;
	}

	public Integer getCustomerNameId() {
		return customerMasterId;
	}

	public Integer getCustomerMasterId() {
		return customerMasterId;
	}

	public void setCustomerMasterId(Integer customerMasterId) {
		this.customerMasterId = customerMasterId;
	}

	public void setTokenNameId(Integer tokenNameId) {
		this.tokenNameId = tokenNameId;
	}

	@Override
	public String toString() {
		return "CustomerTokenMaster [customerName=" + customerName + ", tokenNameId="
				+ tokenNameId + "]";
	}

	/*
	 * public List<DeviceMaster> getDeviceMaster() { return deviceMaster; }
	 * 
	 * public void setDeviceMaster(List<DeviceMaster> deviceMaster) { this.deviceMaster =
	 * deviceMaster; }
	 */

}