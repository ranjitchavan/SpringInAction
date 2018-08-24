package com.springinaction.springdoi;
public class Stage {
	
	
	private Stage() {
		System.out.println("SSSS");
	}
	
	private static class StageInstance{
		static Stage sobj=new Stage();
		 
	}
	
	public  static Stage getInstance() {
		System.out.println("function");
		return StageInstance.sobj;
	}
	

}
