package com.einfochips.webportal.dto;

import com.einfochips.webportal.domain.TokenName;

/**
 * 
 * @author asra.shaikh
 *
 */
public class CustomerDto {

	private int cutomerId;
	private String customerName;
	private String tokenName;
	private int tokenNameId;

	public CustomerDto(int cutomerId, String customerName, TokenName tokenName) {
		super();
		this.cutomerId = cutomerId;
		this.customerName = customerName;
		this.tokenName = tokenName.getTokenName();
		this.tokenNameId = tokenName.getTokenNameId();
	}

	public int getCutomerId() {
		return cutomerId;
	}

	public void setCutomerId(int cutomerId) {
		this.cutomerId = cutomerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(TokenName tokenName) {
		this.tokenName = tokenName.getTokenName();
	}

	public int getTokenNameId() {
		return tokenNameId;
	}

	public void setTokenNameId(int tokenNameId) {
		this.tokenNameId = tokenNameId;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	@Override
	public String toString() {
		return "CustomerDto [cutomerId=" + cutomerId + ", customerName=" + customerName
				+ ", tokenName=" + tokenName + "]";
	}

}
