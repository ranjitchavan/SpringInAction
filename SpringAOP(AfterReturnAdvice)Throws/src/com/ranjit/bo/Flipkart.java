package com.ranjit.bo;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component("flip")
public class Flipkart {
	public double purchaseProduct(int prdID) {
		
		return new Random(200000).nextDouble();
	}
}
