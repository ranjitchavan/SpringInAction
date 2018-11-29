package com.spring.ambi;

public class AmbiBean {
	private Jack jack;

	public Jack getJack() {
		return jack;
	}

	public void setJack(Jack jack) {
		this.jack = jack;
	}

	@Override
	public String toString() {
		return "AmbiBean [jack=" + jack.hashCode() + "]";
	}
	
	
	
	
}
