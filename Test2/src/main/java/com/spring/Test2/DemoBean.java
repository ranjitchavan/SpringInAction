package com.spring.Test2;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class DemoBean implements Demo{

	String name;
	int age;
	Date d;
	List fruits;
	String colors[];
	Set phones;
	Map faculteis;
	Properties capitals;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}	public Date getD() {
		return d;
	}
	public void setD(Date d) {
		this.d = d;
	}
	public List getFruits() {
		return fruits;
	}

	public void setFruits(List fruits) {
		this.fruits = fruits;
	}
	public String[] getColors() {
		return colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	public Set getPhones() {
		return phones;
	}
	public void setPhones(Set phones) {
		this.phones = phones;
	}
	public Map getFaculteis() {
		return faculteis;
	}
	public void setFaculteis(Map faculteis) {
		this.faculteis = faculteis;
	}
	public Properties getCapitals() {
		return capitals;
	}
	public void setCapitals(Properties capitals) {
		this.capitals = capitals;
	}
	public String sayHello() {
		return "DemoBean [name=" + name + ", age=" + age + ", d=" + d + ", fruits=" + fruits + ", colors="
				+ Arrays.toString(colors) + ", phones=" + phones + ", faculteis=" + faculteis + ", capitals=" + capitals
				+ "]";
	}
	
	

}
