package com.audi;

import com.audi.cars.AudiA4;
import com.audi.cars.Car;

public class AssmbleCar {

	/**
	 * @param args
	 */
	
	public static Car assmbledCar(Car c){
		
		
		return c;
	}
	
	public static void main(String[] args) throws Exception{
		
		
		Car c=(Car)Class.forName(args[0]).newInstance();
		Car obj=assmbledCar(c);
		
		
		System.out.println("----------------------- Car ------------------------------------");
		System.out.println("Company :: "+obj.Logo());
	    System.out.println("Name :: "+obj.addCarName());
	    System.out.println("Manfacurer :: "+obj.addManfacture());
	    System.out.println("Wheel :: "+obj.addWheel());
	    System.out.println("Color :: "+obj.addColor());
		
		
		
		

	}

}
