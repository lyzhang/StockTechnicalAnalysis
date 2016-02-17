package edu.brown.cs32.lyzhang.crassus.indicators;

import java.awt.Color;
import java.util.*;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.lyzhang.crassus.gui.SeriesWrapper;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;

/**
 * @author lyzhang
 * 
 * Bollinger Bands can be used to measure price action volatility.
 * 
 * Typical value of period is 20 days with bands 2 * standard deviations
 * above and below the moving average.
 * 
 * Bandwidth is the number of standard deviations above and below SMA; 
 * typical value is 2.
 *
 * Bands are calculated using close prices.
 */
public class BollingerBands implements Indicator {

	private List<IndicatorDatum> middleBand;		// oldest data first
	private List<IndicatorDatum> lowerBand;			
	private List<IndicatorDatum> upperBand;			
	private int period;
	private List<StockTimeFrameData> data;
	private int bandWidth;
	private boolean isActive;
	private boolean isVisible;
	private final double START_AMT = 10000;
	private final double EPSILON = 0.1;
	private double percentMade;
	private StockEventType currentEvent = StockEventType.NONE;
	
	public BollingerBands(List<StockTimeFrameData> data, int period, int bandWidth) throws IllegalArgumentException {
		if (period == 0) throw new IllegalArgumentException("ERROR: " + period + " is not a valid period");
		
		this.data = data;
		this.period = period;
		this.bandWidth = bandWidth;
		middleBand = new ArrayList<IndicatorDatum>();
		upperBand = new ArrayList<IndicatorDatum>();
		lowerBand = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	// Indicator parameters
	public int getPeriod() {
		return period;
	}
	
	public int getBandWidth() {
		return bandWidth;
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
	
	@Override
	public String getName() {
		return "Bollinger Bands";
	}
	
	/**
	 * Calculates the standard deviation given start and end index of data inclusive.
	 * 
	 * Uses formula stdDev = sqrt(1/N * sum((x(i) - movingAvg))^2) for i = 1:N 
	 * 
	 * @param startIndex						int start index
	 * @param endIndex							int end index
	 * @param movingAvg							double moving average
	 * @return									double standard deviation of given close values
	 * @throws ArrayIndexOutOfBoundsException	if array index of data is out of bounds
	 */
	double calcStdDev(int startIndex, int endIndex, double movingAvg) 
			throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			double diff = data.get(i).getAdjustedClose() - movingAvg;
			sum +=  (diff * diff);
		}
		
		return Math.sqrt(sum / (endIndex - startIndex + 1));
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
	double calcSMA(int startIndex, int endIndex) throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			sum += data.get(i).getAdjustedClose();
		}
		
		return sum / (endIndex - startIndex + 1);
	}

	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {

		System.out.println("Bollinger Indicator size of points=" + middleBand.size());
		SeriesWrapper upperSeries = stockPlot.getTimeSeries(upperBand, "Upper Band", startTime, endTime, Color.red);
		SeriesWrapper middleSeries = stockPlot.getTimeSeries(middleBand, "Middle Band", startTime, endTime, Color.blue);
		SeriesWrapper lowerSeries = stockPlot.getTimeSeries(lowerBand, "Lower Band", startTime, endTime, Color.GREEN);
		
		stockPlot.addSeries(upperSeries);
		stockPlot.addSeries(middleSeries);
		stockPlot.addSeries(lowerSeries);
	}
	
	/**
	 * Updates the Bollinger Bands.
	 */
	private void updateBollingerBands() {
		
		System.out.println("UPDATE CALLED!!!");
		System.out.println("data size=" + data.size());
		
		StockEventType currEvent = StockEventType.NONE;
		double currAmt = START_AMT;
		double numStocks = 0;
		for (int i = 0; (i + period - 1) < data.size(); i++) {
			double avg = calcSMA(i, i + period - 1);
			double stdDev = calcStdDev(i, i + period - 1, avg);
			
			double upperBandValue = avg + (bandWidth * stdDev);
			double lowerBandValue = avg - (bandWidth * stdDev);
			double currClose = data.get(i).getAdjustedClose();
			
			if (((currClose > upperBandValue - EPSILON) && (currClose < upperBandValue + EPSILON)) || (i == data.size() - 1)) {
				if (currEvent.equals(StockEventType.BUY)) {			// if we have already bought then sell now or sell at whatever price is 
					currAmt += numStocks * currClose;				// last price
					numStocks = 0;									// sold all the stocks
					currEvent = StockEventType.SELL;
					System.out.println("currEvent=[" + currEvent + "], currPrice=["+ currClose + "], currTime=" + data.get(i + period - 1).getTime());
				}
			}
			
			else if (((currClose > lowerBandValue - EPSILON) && (currClose < lowerBandValue + EPSILON))) {
				if (currEvent.equals(StockEventType.SELL) || currEvent.equals(StockEventType.NONE)) {
					numStocks += Math.floor(currAmt/currClose);		// buy whole number of stocks
					currAmt = currAmt%currClose;					// keep amount left over
					currEvent = StockEventType.BUY;
					System.out.println("currEvent=[" + currEvent + "], currPrice=["+ currClose + "], currTime=" + data.get(i + period - 1).getTime());
				}
			}
			
			middleBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), data.get(i + period - 1).getTimeInNumber(), avg));
			upperBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), data.get(i + period - 1).getTimeInNumber(), upperBandValue));
			lowerBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), data.get(i + period - 1).getTimeInNumber(), lowerBandValue));
		}
		
		percentMade = ((currAmt - START_AMT) / START_AMT);

	}
	
	
	List<IndicatorDatum> getUpperBand() {
		return upperBand;
	}
	
	List<IndicatorDatum> getMiddleBand() {
		return middleBand;
	}

	List<IndicatorDatum> getLowerBand() {
		return lowerBand;
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateBollingerBands();
	}
	
	public void incrementalUpdate(StockTimeFrameData datum) {

		
		int lastIndex = data.size() - 1;
		
		double avg = calcSMA(lastIndex - (period - 1), lastIndex);
		double stdDev = calcStdDev(lastIndex - (period - 1), lastIndex, avg);
		
		double upperBandValue = avg + (bandWidth * stdDev);
		double lowerBandValue = avg - (bandWidth * stdDev);

		middleBand.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), avg));
		upperBand.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), upperBandValue));
		lowerBand.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), lowerBandValue));
	}

	
	// assumes that the indicator data has already been refreshed.
	@Override
	public StockEventType isTriggered() {
		
		double currClose = data.get(data.size() - 1).getAdjustedClose();
		double upperBandValue = upperBand.get(upperBand.size() - 1).getValue();
		double lowerBandValue = lowerBand.get(upperBand.size() - 1).getValue();
		
		if (((currClose > upperBandValue - EPSILON) && (currClose < upperBandValue + EPSILON))) {
			currentEvent = StockEventType.SELL;
		}
		
		else if (((currClose > lowerBandValue - EPSILON) && (currClose < lowerBandValue + EPSILON))) {
			currentEvent = StockEventType.BUY;
		}
		
		else {
			currentEvent = StockEventType.NONE;
		}
		
		return currentEvent;
	}

	@Override
	public double getTestResults() {
		return percentMade;
	}
}