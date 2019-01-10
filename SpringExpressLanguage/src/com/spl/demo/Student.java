package com.spl.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Student {
	private int id;
	private String name;
	private double marks;
	private String nullEr;
	private List<String> city;
	public List<String> getCity() {
		return city;
	}
	public void setCity(List<String> city) {
		this.city = city;
	}
	
	public String getNullEr() {
		return nullEr;
	}
	@Autowired
	public void setNullEr(@Value("#{nullEr.getSam()}")String nullEr) {
		this.nullEr = nullEr;
	}
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", marks=" + marks + ", nullEr=" + nullEr + "]";
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public String iAmNull() {
		
		return null;
	}
	
}
