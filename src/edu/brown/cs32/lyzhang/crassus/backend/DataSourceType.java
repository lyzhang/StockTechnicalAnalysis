/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

/**
 *
 * @author lyzhang
 */
public enum DataSourceType {
    	YAHOOFINANCE,DEMODATA,IB;
	
	@Override
	public String toString() {
		String toReturn = "";
		switch (this) {
			case YAHOOFINANCE: toReturn = "YAHOOFINANCE"; break;
			case DEMODATA: toReturn = "DEMODATA"; break;
			case IB: toReturn = "IB"; break;
		}
		return toReturn;
	}
}
