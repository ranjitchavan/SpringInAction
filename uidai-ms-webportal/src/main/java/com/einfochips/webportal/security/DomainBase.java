package com.einfochips.webportal.security;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

//This is for JWT
public class DomainBase implements Serializable {

	private static final long serialVersionUID = -58145495846384044L;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
