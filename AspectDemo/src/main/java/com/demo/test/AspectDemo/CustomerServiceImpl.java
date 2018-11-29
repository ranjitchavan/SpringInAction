package com.demo.test.AspectDemo;

public class CustomerServiceImpl implements CustomerService {
	private String name;
	private String url;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String printName() {
		System.out.println("Busness Method NAME:"+name);
		return name;
	}

	@Override
	public String printUrl() {
		// TODO Auto-generated method stub
		System.out.println("Bussness Method URL:"+url);
		return url;
	}

	@Override
	public void printException() {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

}
