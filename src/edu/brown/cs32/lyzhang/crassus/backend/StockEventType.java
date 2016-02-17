package edu.brown.cs32.lyzhang.crassus.backend;

/**
 * @author lyzhang
 *
 * Each Indicator will implement isTriggered() method which 
 * will return this type.
 */
public enum StockEventType {
	BUY, SELL, NONE, CONFLICT;
	
	@Override
	public String toString() {
		String toReturn = "";
		switch (this) {
			case BUY: toReturn = "BUY"; break;
			case SELL: toReturn = "SELL"; break;
			case NONE: toReturn = "NONE"; break;
			case CONFLICT: toReturn = "CONFLICT"; break;
		}
		return toReturn;
	}
}
