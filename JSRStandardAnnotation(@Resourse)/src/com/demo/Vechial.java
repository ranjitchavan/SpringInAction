package com.demo;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
@Named("v1")
@Lazy(value=true)
public  class Vechial {
	@Resource
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

	public Vechial() {
		super();
		// TODO Auto-generated constructor stub
	}
}