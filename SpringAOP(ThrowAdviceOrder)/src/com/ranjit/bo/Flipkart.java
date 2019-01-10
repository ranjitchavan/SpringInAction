package com.ranjit.bo;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component("flip")
public class Flipkart {
	public double purchaseProduct(int prdID)throws RuntimeException {
			if (prdID<1)
					throw new RuntimeException("Product Id is invalid");
				
		return new Random(200000).nextDouble();
	}
}
