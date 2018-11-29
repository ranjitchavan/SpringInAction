package com.einfochips.webportal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author darshan.sathwara
 *
 */
@Entity
public class TokenName {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Value("token_name_id")
	private Integer tokenNameId;

	@Value("token_name")
	private String tokenName;

	public Integer getTokenNameId() {
		return tokenNameId;
	}

	public void setTokenNameId(Integer tokenNameId) {
		this.tokenNameId = tokenNameId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	@Override
	public String toString() {
		return "TokenName [tokenNameId=" + tokenNameId + ", tokenName=" + tokenName + "]";
	}

}