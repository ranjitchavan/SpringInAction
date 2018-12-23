package com.einfochips.webportal.security;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class ModelBase implements Serializable {

	private static final long serialVersionUID = 2665537090169190789L;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
