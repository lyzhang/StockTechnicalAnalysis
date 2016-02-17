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
 * RSI is a momentum oscillator that measures speed and change
 * of price movements.
 * 
 * RSI oscillates between 0 and 100
 * 
 * RSI is considered overbought when above 70 and oversold when below 30.
 * 
 * Source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:relative_strength_index_rsi
 * 
 */

public class RSI implements Indicator {

	private List<StockTimeFrameData> data;
	private List<IndicatorDatum> RSIPoints;
	private int period;
	private boolean isActive;
	private boolean isVisible;
	private final double EPSILON = 0.01;
	private final double START_AMT = 10000;
	private double percentMade;
	double avgGain = 0;
	double avgLoss = 0;
	private StockEventType currentEvent = StockEventType.NONE;
	
	public RSI(List<StockTimeFrameData> data, int period) {
		if (period == 0) throw new IllegalArgumentException("ERROR: " + period + " is not a valid period");
		this.data = data;
		this.period = period;
		this.RSIPoints = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	// Indicator parameters
	public int getPeriod() {
		return period;
	}
	//
	
	List<IndicatorDatum> getRSIPoints() {
		return RSIPoints;
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
	
	@Override
	public String getName() {
		return "Relative Strength Index";
	}
	
	/**
	 * Incrementally updates the RSI points given new datum.
	 * 
	 * @param datum		StockTimeFrameData
	 */
	public void incrementalUpdate(StockTimeFrameData datum) {
		
		int lastIndex = data.size() - 1;
		double currChange = data.get(lastIndex - 1).getAdjustedClose() - data.get(lastIndex - 2).getAdjustedClose();
		// smoothing technique similar to exponential moving average calculation
		if (currChange < 0) {														// currently a loss
			avgLoss = ((avgLoss * (period - 1)) + Math.abs(currChange)) / period;
			avgGain = (avgGain * (period - 1)) / period; 
		} else {																	// currently a gain
			avgLoss = (avgLoss * (period - 1)) / period;
			avgGain = ((avgGain * (period - 1)) + currChange) / period;
		}

		double rs = avgGain / avgLoss;
		double rsi = 100 - (100 / (1 + rs));
		RSIPoints.add(new IndicatorDatum(datum.getTime(), datum.getTimeInNumber(), rsi));
		
	}
	
	/**
	 * Updates the RSI data points
	 */
	private void updateRSI() {
		
		StockEventType currEvent = StockEventType.NONE;
		double currAmt = START_AMT;
		double numStocks = 0;
		
		double rs;
		double rsi;
		for (int i = 0; i + period < data.size(); i++) {

			if (i == 0) {
				double[] avgGainLoss = calculateAvgGainLoss(0, period - 1);				// first average (simple period average)
				avgGain = avgGainLoss[0];
				avgLoss = avgGainLoss[1];
				rs = avgGain / avgLoss;
				rsi = 100 - (100 / (1 + rs));
				RSIPoints.add(new IndicatorDatum(data.get(period).getTime(), data.get(period).getTimeInNumber(), rsi));
				continue;
			}
			
			double currChange = data.get(i + period - 1).getAdjustedClose() - data.get(i + period - 2).getAdjustedClose();
																						// smoothing technique similar to exponential moving average calculation
			if (currChange < 0) {														// currently a loss
				avgLoss = ((avgLoss * (period - 1)) + Math.abs(currChange)) / period;
				avgGain = (avgGain * (period - 1)) / period; 
			} else {																	// currently a gain
				avgLoss = (avgLoss * (period - 1)) / period;
				avgGain = ((avgGain * (period - 1)) + currChange) / period;
			}

			rs = avgGain / avgLoss;
			rsi = 100 - (100 / (1 + rs));
			RSIPoints.add(new IndicatorDatum(data.get(i + period).getTime(), data.get(i + period).getTimeInNumber(), rsi));
			
			double currClose = data.get(i).getAdjustedClose();
			
			if (((rsi > 0.7 - EPSILON) && (currClose < 0.7 + EPSILON)) || (i == data.size() - 1)) {
				if (currEvent.equals(StockEventType.BUY)) {		// if we have already bought then sell now or sell at whatever price is 
					currAmt += numStocks * currClose;			// last price
					numStocks = 0;								// sold all the stocks
					currEvent = StockEventType.SELL;
				}
			}
			
			if (((currClose > 0.3 - EPSILON) && (currClose < 0.3 + EPSILON))) {
				numStocks += Math.floor(currAmt/currClose);		// buy whole number of stocks
				currAmt = currAmt%currClose;					// keep amount left over
				currEvent = StockEventType.BUY;
			}
			
		}
		
		percentMade = ((currAmt - START_AMT) / START_AMT);
	}
	
	/**
	 * Calculates the average gain loss from starting index to end index of data inclusive
	 * 
	 * @param startIndex		int start index
	 * @param endIndex			int end index
	 * @return					double array of size 2. [0] - gain; [1] - loss (absolute value)
	 */
	private double[] calculateAvgGainLoss(int startIndex, int endIndex) {
		
		double gain = 0;
		double loss = 0;
		double[] avgGainLoss = new double[2];
		for (int i = startIndex; i <= endIndex; i++) {
			
			if (i == 0) continue;
			
			double currClose = data.get(i).getAdjustedClose();
			double prevClose = data.get(i-1).getAdjustedClose();
			double change = currClose - prevClose;
			
			if (change < 0) {
				loss += change;
			} else if (change >= 0) {
				gain += change;
			}
		}
		
		avgGainLoss[0] = gain / period;				// gain
		avgGainLoss[1] = Math.abs(loss) / period;	// loss
		
		return avgGainLoss;
	}

	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {

		SeriesWrapper upperSeries = stockPlot.getTimeSeries(RSIPoints, "Relative Strength Index", startTime, endTime, Color.red);

		try {
			stockPlot.setRS(true);
		} catch (CantTurnRsOnAfterChartsRetreivedException e) {
			e.printStackTrace();
		}
		stockPlot.addRsSeries(upperSeries);

	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateRSI();
	}

	@Override
	public StockEventType isTriggered() {
		
		double rsi = RSIPoints.get(RSIPoints.size() - 1).getValue();
		double currClose = data.get(data.size() - 1).getAdjustedClose();
		
		if (((rsi > 0.7 - EPSILON) && (currClose < 0.7 + EPSILON))) {
			currentEvent = StockEventType.SELL;
		
		}
		
		if (((currClose > 0.3 - EPSILON) && (currClose < 0.3 + EPSILON))) {
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