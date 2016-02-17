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
 * MACD subtracts longer moving average from shorter moving average
 * to a momentum oscillator (offers trend following and momentum).
 * 
 * MACD fluctuates above and below zero line as moving averages converge,
 * cross zero, and diverge.
 * 
 * As MACD is unbounded, it is not very useful for identifying overbought
 * and oversold levels.
 * 
 * Typical moving average period is 9, 12, 26 day; the shorter moving average period
 * is faster; longer is slower and less reactive to price changes. These three values
 * can be changed.
 *  
 * MACD Line: (shorterPeriod EMA - longerPeriod EMA) 
 * Signal Line: signalPeriod EMA of MACD Line
 * MACD Histogram: MACD Line - Signal Line
 * 
 * Source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_average_conve
 */
public class MACD implements Indicator {
	
	private List<StockTimeFrameData> data;
	private int signalPeriod;
	private int shortPeriod;
	private int longPeriod;
	private List<IndicatorDatum> MACDLine;
	private List<IndicatorDatum> signalLine;
	private boolean isActive;
	private boolean isVisible;
	double prevSigEMA = 0;
	double prevShortEMA = 0;
	double prevLongEMA = 0;
	
	public MACD(List<StockTimeFrameData> data, int signalPeriod, int shorterPeriod, int longerPeriod
			) throws IllegalArgumentException {
		if (shorterPeriod > longerPeriod) throw new IllegalArgumentException("ERROR: shorter period must not be greater than longer period");
		
		this.data = data;
		this.signalPeriod = signalPeriod;
		this.shortPeriod = shorterPeriod;
		this.longPeriod = longerPeriod;
		
		MACDLine = new ArrayList<IndicatorDatum>();
		signalLine = new ArrayList<IndicatorDatum>();
		
		refresh(data);
	}
	
	// Indicator parameters
	public int getSignalPeriod() {
		return signalPeriod;
	}
	
	public int getShortPeriod() {
		return shortPeriod;
	}
	
	public int getLongPeriod() {
		return longPeriod;
	}
	//
	
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
	
	List<IndicatorDatum> getSignalLine() {
		return signalLine;
	}
	
	List<IndicatorDatum> getMACDLine() {
		return MACDLine;
	}
	
	@Override
	public String getName() {
		return "MACD";
	}

	/**
	 * Calculates the SMA from start index to end index of data
	 * inclusively.
	 * 
	 * @param startIndex	int start index 
	 * @param endIndex		int end index
	 * @return				double simple moving average of given close values
	 * @throws ArrayIndexOutOfBoundsException	if array index of data is out of bounds
	 */
	private double calcSMA(int startIndex, int endIndex) throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			sum += data.get(i).getAdjustedClose();
		}
		
		return sum / (endIndex - startIndex + 1);
	}
	
	/**
	 * Calculates EMA
	 * 
	 * @param prevEMA		double previous EMA value
	 * @param period		double period of EMA 
	 * @param close			double close value
	 * @return
	 */
	private double calcEMA(double prevEMA, double period, double close) {

		double multiplier = 2 / (period + 1);
		return (close - prevEMA) * multiplier + prevEMA;
	}
	
	/**
	 * Returns the max of three values.
	 * 
	 * @param a		int
	 * @param b		int
	 * @param c		int
	 * @return
	 */
	private int min(int a, int b, int c) {
		int currMin = a;
		if (b < currMin) {
			currMin = b;
		}
		
		if (c < currMin) {
			currMin = c;
		}
		return currMin;
	}
	
	public void incrementalUpdate(StockTimeFrameData datum) {
		
		int lastIndex = data.size() - 1;
		int i = lastIndex;
		
		double currSigEMA = calcEMA(prevSigEMA, signalPeriod, data.get(i).getAdjustedClose());
		signalLine.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), currSigEMA));
		prevSigEMA = currSigEMA;		// save prev EMA values for next EMA calculation
	
		double currShortEMA = calcEMA(prevShortEMA, shortPeriod, data.get(i).getAdjustedClose());
		prevShortEMA = currShortEMA;
		
		double currLongEMA = calcEMA(prevLongEMA, longPeriod, data.get(i).getAdjustedClose());
		MACDLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), currShortEMA - currLongEMA));
		prevLongEMA = currLongEMA;
		
	}
	
	/**
	 * Updates the MACD points.
	 */
	private void updateMACD() {

		int startIndex = (min(signalPeriod, shortPeriod, longPeriod) - 1);
		
		double currShortEMA = 0;

		double currLongEMA;
		for (int i = startIndex; i < data.size(); i++) {
			
			if (i >= signalPeriod - 1) {
				if (i == signalPeriod - 1) {
					double firstSignalEMA = calcSMA(i - signalPeriod + 1, i);
					signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), firstSignalEMA));
					prevSigEMA = firstSignalEMA;
				} else {
					double currSigEMA = calcEMA(prevSigEMA, signalPeriod, data.get(i).getAdjustedClose());
					signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), currSigEMA));
					prevSigEMA = currSigEMA;		// save prev EMA values for next EMA calculation
				}
			}
			
			if (i >= shortPeriod - 1) {
				if (i == shortPeriod - 1) {
					double firstShortEMA = calcSMA(i - shortPeriod + 1, i);
					prevShortEMA = firstShortEMA;
					currShortEMA = firstShortEMA;
				} else {
					currShortEMA = calcEMA(prevShortEMA, shortPeriod, data.get(i).getAdjustedClose());
					prevShortEMA = currShortEMA;
				}
			}
			
			if (i >= longPeriod - 1) {
				if (i == longPeriod - 1) {
					double firstLongEMA = calcSMA(i - longPeriod + 1, i);
					prevLongEMA = firstLongEMA;
					currLongEMA = firstLongEMA;
					MACDLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), currShortEMA - currLongEMA));
				} else {
					currLongEMA = calcEMA(prevLongEMA, longPeriod, data.get(i).getAdjustedClose());
					MACDLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), currShortEMA - currLongEMA));
					prevLongEMA = currLongEMA;
				}
			}

			
			//System.out.println(String.format("currshortEMA=[%s], currLongEMA=[%s]", currShortEMA,currLongEMA));

		}
	}
	
	
	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {

		SeriesWrapper macd = stockPlot.getTimeSeries(MACDLine, "Moving Average Convergence Divergence", startTime, endTime, Color.red);
		
		try {
			stockPlot.setRS(true);
		} catch (CantTurnRsOnAfterChartsRetreivedException e) {
			e.printStackTrace();
		}
		stockPlot.addRsSeries(macd);

	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateMACD();
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