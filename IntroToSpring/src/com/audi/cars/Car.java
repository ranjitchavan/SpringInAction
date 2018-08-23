package com.audi.cars;

public interface Car {
	public static final String COMPANY_NAME="AUDI";
	
	public String addCarName();
	public int addWheel();
	public String addColor();
    public boolean addManfacture();
	
    
    public default String Logo(){
		
		return COMPANY_NAME;
		
	}	
}
