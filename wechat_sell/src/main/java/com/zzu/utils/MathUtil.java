package com.zzu.utils;

public class MathUtil {
	public static final Double MONEY_RANGE=0.01;
    public static Boolean equals(Double d1,Double d2){
    	Double a=Math.abs(d1-d2);
    	if(a<MONEY_RANGE){
    		return true;
    	}
    	return false;
    }
}
