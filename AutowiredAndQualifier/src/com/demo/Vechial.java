package com.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component("v")
public class Vechial {
	@Autowired
	
	private Engine eng;

	public Engine getEng() {
		return eng;
	}
	
	public void setEng(Engine eng) {
		this.eng = eng;
	}

	@Override
	public String toString() {
		return "Vechial [eng=" + eng + "]";
	}
	
}
