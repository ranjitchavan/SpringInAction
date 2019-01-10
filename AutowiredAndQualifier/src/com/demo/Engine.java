package com.demo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("eng")
public class Engine {
	@Value("1")
	private int id;
	@Value("Annotation Su")
	private String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
