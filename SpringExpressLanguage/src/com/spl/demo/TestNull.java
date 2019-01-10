package com.spl.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("nullEr")
public class TestNull {
	public String sam;

	public String getSam() {
		return sam;
	}
	@Value("Jack")
	public void setSam(String sam) {
		this.sam = sam;
	}

	@Override
	public String toString() {
		return "TestNull [sam=" + sam + "]";
	}
	private boolean bool;

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}
}
