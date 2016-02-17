package edu.brown.cs32.lyzhang.crassus.indicators;

/**
 * @author lyzhang
 * 
 * A wrapper for indicator data points used for storing points calculated
 * from financial indicator formulae.
 */
public class IndicatorDatum {
	
	private String timeLabel;
	private double value;
	private long time;
	
	public IndicatorDatum(String timeLabel, long time, double value) {
		this.timeLabel = timeLabel;
		this.time = time;
		this.value = value;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getTimeLabel() {
		return timeLabel;
	}
	
	public double getValue() {
		return value;
	}
}
