package com.spl.demo;

import org.springframework.stereotype.Component;

@Component("city")
public class City {
	private String name;
	private int pincode;
	private String state;
	private String cou;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "City [name=" + name + ", pincode=" + pincode + ", state=" + state + ", cou=" + cou + "]";
	}
	public String getCou() {
		return cou;
	}
	public void setCou(String cou) {
		this.cou = cou;
	}
}
