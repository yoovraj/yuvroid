package com.my.siggen.logic.math;

public class SineLookUpTable {

	static int precision = 4;
	
	static int multiplyingFactor = (int) Math.pow(10, precision);
	static int arraySize = (int) (2*Math.PI*multiplyingFactor);
	static double[] value = new double[arraySize];
	
	private static SineLookUpTable instance = null;
	private SineLookUpTable() {
		System.out.println("---GENERATING SINE TABLE ---");
		for (int i=0; i < arraySize; i++) {
			value[i] = Math.sin(i*1.0/multiplyingFactor);
		}
		System.out.println("---FINISHED SINE TABLE ---");
	}
	
	public static double sineOf(double number) {
		if (instance == null ) {
			instance = new SineLookUpTable();
		}
		
		int rangeNum =  (int) (number*multiplyingFactor) - arraySize*((int) (number*multiplyingFactor)/arraySize);
		if (rangeNum < 0 || rangeNum >= arraySize) {
			System.out.println("ERROR in SINE CALCULATION so return 0");
			return 0;
		}
		return value[rangeNum];
	}
}
