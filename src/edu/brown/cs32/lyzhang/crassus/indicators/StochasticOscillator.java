package edu.brown.cs32.lyzhang.crassus.indicators;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.lyzhang.crassus.gui.CantTurnRsOnAfterChartsRetreivedException;
import edu.brown.cs32.lyzhang.crassus.gui.SeriesWrapper;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;

/**
 * @author lyzhang
 *
 * A momentum indicator which shows the location of the close relative
 * to the high-low range over a set number of periods.
 * 
 * It follows speed or momentum of price.
 * 
 * It is useful for identifying overbought and oversold levels because
 * it is range bound.
 * 
 * Typical period is 14 periods, which can be days, weeks, months, or
 * an intraday time frame.
 * 
 * %K = (Current Close - Lowest Low)/(Highest High - Lowest Low) * 100
 * %D = 3-day SMA of %K (signal or trigger line)
 * 
 * Lowest low and highest high is for the look-back period
 *  
 * source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:stochastic_oscillator 
 */
public class StochasticOscillator implements Indicator {

	private List<StockTimeFrameData> data;
	private int period;
	private List<IndicatorDatum> stocOscillator;
	private List<IndicatorDatum> signalLine;
    private boolean isActive;
    private boolean isVisible;
	
	public StochasticOscillator(List<StockTimeFrameData> data, int period) {
		if (period == 0) throw new IllegalArgumentException("ERROR: " + period + " is not a valid period");
		this.data = data;
		this.period = period;
		this.stocOscillator = new ArrayList<>();
		this.signalLine  = new ArrayList<>();
		refresh(data);
	}
	
	// Indicator parameters
	public int getPeriod() {
		return period;
	}
	//
	
	public List<IndicatorDatum> getStocOscillator() {
		return stocOscillator;
	}
	
	@Override
	public boolean getVisible() {
		return isVisible;
	}
	@Override
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	@Override
	public boolean getActive() {
		return isActive;
	}
	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * Returns an array of high and low values from start index to end index
	 * of data List.
	 * 
	 * @param startIndex		int start index
	 * @param endIndex			int end index
	 * @return					double array; [0] - high, [1] - low
	 */
	private double[] getHighLow(int startIndex, int endIndex) {
		
		double currHigh = Double.MIN_VALUE;
		double currLow = Double.MAX_VALUE;
		for (int i = startIndex; i <= endIndex; i++) {
			if (data.get(i).getHigh() > currHigh) {
				currHigh = data.get(i).getHigh();
			}
			
			if (data.get(i).getLow() < currLow) {
				currLow = data.get(i).getLow();
			}
		}
		
		double[] highLow = new double[2];
		highLow[0] = currHigh;
		highLow[1] = currLow;
		
		return highLow;
	}
	
	public void incrementalUpdate(StockTimeFrameData datum) {

		int lastIndex = data.size() - 1;
		double[] highLow = getHighLow(lastIndex - period + 1, lastIndex);
		double high = highLow[0];
		double low = highLow[1];
		
		double K = (datum.getAdjustedClose() - low) / (high - low) * 100;
		stocOscillator.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), K));
	
		int i = stocOscillator.size() - 1;
		double D = (stocOscillator.get(i).getValue() + stocOscillator.get(i-1).getValue() + stocOscillator.get(i-2).getValue()) / 3;
		signalLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), D));
	}
	
	private void updateStochasticOscillator() {
		
		for (int i = (period - 1); i < data.size(); i++) {
			
			double[] highLow = getHighLow(i - period + 1, i);
			double high = highLow[0];
			double low = highLow[1];
			
			double K = (data.get(i).getAdjustedClose() - low) / (high - low) * 100;
			stocOscillator.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), K));
		}
		
		for (int i = 2; i < stocOscillator.size(); i++) {		// calc SMA of K
			double D = (stocOscillator.get(i).getValue() + stocOscillator.get(i-1).getValue() + stocOscillator.get(i-2).getValue()) / 3;
			signalLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), D));
		}
		
	}
	
	@Override
	public String getName() {
		return "Stochastic Oscillator";
	}
	
	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {
		
		SeriesWrapper stochasticOscillator = stockPlot.getTimeSeries(stocOscillator, "Stochastic Oscillator", startTime, endTime, Color.red);
		SeriesWrapper signalline = stockPlot.getTimeSeries(stocOscillator, "Signal Line", startTime, endTime, Color.black);
			
		try {
			stockPlot.setRS(true);
		} catch (CantTurnRsOnAfterChartsRetreivedException e) {
			e.printStackTrace();
		}
		stockPlot.addRsSeries(stochasticOscillator);     // red line
		stockPlot.addRsSeries(signalline);               // black line
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateStochasticOscillator();
	}

	@Override
	public StockEventType isTriggered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTestResults() {
		// TODO Auto-generated method stub
		return 0;
	}
}