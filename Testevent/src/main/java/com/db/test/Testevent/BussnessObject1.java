package com.db.test.Testevent;

public abstract class BussnessObject1 {
	private BussnessObject2 bo2;

	public abstract BussnessObject2 getObject();
	public void myObject() {
		
		bo2=getObject();
	}
}
