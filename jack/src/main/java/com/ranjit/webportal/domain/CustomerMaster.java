package com.einfochips.webportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author darshan.sathwara
 *
 */
@Entity
public class CustomerMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Value("customer_master_id")
	private Integer customerMasterId;

	@Value("customer_name")
	private String customerName;

	@ManyToOne
	@JoinColumn(name = "token_name_id")
	private TokenName tokenNameId;

	/*
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) private
	 * List<DeviceMaster> deviceMaster;
	 */
	public CustomerMaster() {
		// default constructor
	}

	public Integer getCustomerMasterId() {
		return customerMasterId;
	}

	public void setCustomerMasterId(Integer customerMasterId) {
		this.customerMasterId = customerMasterId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public TokenName getTokenNameId() {
		return tokenNameId;
	}

	public void setTokenNameId(TokenName tokenNameId) {
		this.tokenNameId = tokenNameId;
	}

	@Override
	public String toString() {
		return "CustomerMaster [customerMasterId=" + customerMasterId + ", customerName="
				+ customerName + ", tokenNameId=" + tokenNameId + "]";
	}

	/*
	 * public List<DeviceMaster> getDeviceMaster() { return deviceMaster; }
	 * 
	 * public void setDeviceMaster(List<DeviceMaster> deviceMaster) { this.deviceMaster =
	 * deviceMaster; }
	 */

}