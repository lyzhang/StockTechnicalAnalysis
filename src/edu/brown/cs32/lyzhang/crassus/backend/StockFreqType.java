/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

/**
 *
 * @author lyzhang
 */
public enum StockFreqType {
	MINUTELY, DAILY, WEEKLY, MONTHLY;
	
	@Override
	public String toString() {
		String toReturn = "";
		switch (this) {
			case MINUTELY: toReturn = "MINUTELY"; break;
			case DAILY: toReturn = "DAILY"; break;
			case WEEKLY: toReturn = "WEEKLY"; break;
                        case MONTHLY: toReturn = "MONTHLY"; break;                            
		}
		return toReturn;
	}
}

