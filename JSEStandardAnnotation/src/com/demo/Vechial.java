package com.demo;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Named("v1")
public class Vechial {
	@Inject
	@Named("ebean")
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