package edu.brown.cs32.lyzhang.crassus.indicators;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.lyzhang.crassus.gui.SeriesWrapper;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;

/**
 * @author lyzhang
 * 
 * For N-period price channel, the upper channel is N-period
 * high (highest high in period) and lower channel is N-period low (lowest
 * low in period). The centre line is the midpoint of the two.
 * 
 * Price channels can be used to identify upward thrusts that signal
 * start of uptrend or downtrend.
 * 
 * Price channels are based on prior data and do not use current period.
 * 
 * Typical is 20-day lookback period. Lowering this value produces
 * tighter channel lines. Does not include current day in the lookback.
 * 
 * Upper Channel Line: lockBackPeriod high
 * Lower Channel Line: lockBackPeriod low
 * Centerline: (lockBackPeriod high + lockBackPeriod low)/2 
 * 
 * source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:price_channels
 *
 */
public class PriceChannel implements Indicator {

	private List<IndicatorDatum> upperChannel;
	private List<IndicatorDatum> lowerChannel;
	private List<IndicatorDatum> centreLine;
	private List<StockTimeFrameData> data;
	private int lookBackPeriod;
	private boolean isActive;
	private boolean isVisible;
	private final double EPSILON = 0.1;
	private final double START_AMT = 10000;
	private double percentMade;
	private StockEventType currentEvent;
	
	public PriceChannel(List<StockTimeFrameData> data, int lookBackPeriod) {
		this.data = data;
		this.lookBackPeriod = lookBackPeriod;
		upperChannel = new ArrayList<IndicatorDatum>();
		lowerChannel = new ArrayList<IndicatorDatum>();
		centreLine = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	// Indicator parameters
	public int getLookBackPeriod() {
		return lookBackPeriod;
	}
	//
	
	List<IndicatorDatum> getUpperChannel() {
		return upperChannel;
	}
	
	List<IndicatorDatum> getLowerChannel() {
		return lowerChannel;
	}
	
	List<IndicatorDatum> getCentreLine() {
		return centreLine;
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
		return "Price Channel";
	}
	
	public void incrementalUpdate(StockTimeFrameData datum) {
		int lastIndex = data.size() - 1;
		double[] periodHighLow = getHighLow(lastIndex - (lookBackPeriod - 1), lastIndex);
		double periodHigh = periodHighLow[0];
		double periodLow = periodHighLow[1];
		double centreVal = (periodHigh + periodLow) / 2;
		String timeLabel = datum.getTime();
		long currTime = datum.getTimeInNumber();
		upperChannel.add(new IndicatorDatum(timeLabel, currTime, periodHigh));
		lowerChannel.add(new IndicatorDatum(timeLabel, currTime, periodLow));
		centreLine.add(new IndicatorDatum(timeLabel, currTime, centreVal));
	}
	
	/**
	 * Updates the price channel bands and centre line.
	 */
	private void updatePriceChannel() {
		
		StockEventType currEvent = StockEventType.NONE;
		double currAmt = START_AMT;
		double numStocks = 0;
		
		for (int i = 0; i + lookBackPeriod < data.size(); i++) {
			double[] periodHighLow = getHighLow(i, i + lookBackPeriod - 1);
			double periodHigh = periodHighLow[0];
			double periodLow = periodHighLow[1];
			double centreVal = (periodHigh + periodLow) / 2;
			
			String timeLabel = data.get(i + lookBackPeriod).getTime();
			long currTime = data.get(i + lookBackPeriod).getTimeInNumber();
			upperChannel.add(new IndicatorDatum(timeLabel, currTime, periodHigh));
			lowerChannel.add(new IndicatorDatum(timeLabel, currTime, periodLow));
			centreLine.add(new IndicatorDatum(timeLabel, currTime, centreVal));
			
			double currClose = data.get(i).getAdjustedClose();
			
			if (((currClose > periodHigh - EPSILON) && (currClose < periodHigh + EPSILON)) || (i == data.size() - 1)) {
				if (currEvent.equals(StockEventType.BUY)) {		// if we have already bought then sell now or sell at whatever price is 
					currAmt += numStocks * currClose;			// last price
					numStocks = 0;								// sold all the stocks
					currEvent = StockEventType.SELL;
				}
			}
			
			if (((currClose > periodLow - EPSILON) && (currClose < periodLow + EPSILON))) {
				numStocks += Math.floor(currAmt/currClose);		// buy whole number of stocks
				currAmt = currAmt%currClose;					// keep amount left over
				currEvent = StockEventType.BUY;
			}
		}
		
		percentMade = ((currAmt - START_AMT) / START_AMT);
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
	
	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {

		SeriesWrapper upperSeries = stockPlot.getTimeSeries(upperChannel, "Upper Channel", startTime, endTime, Color.pink);
		SeriesWrapper middleSeries = stockPlot.getTimeSeries(centreLine, "Centre Line", startTime, endTime, Color.cyan);
		SeriesWrapper lowerSeries = stockPlot.getTimeSeries(lowerChannel, "Lower Channel", startTime, endTime, Color.green);
		
		stockPlot.addSeries(upperSeries);
		stockPlot.addSeries(middleSeries);
		stockPlot.addSeries(lowerSeries);
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updatePriceChannel();
	}

	// assumes that the indicator data has already been refreshed.
	@Override
	public StockEventType isTriggered() {
		
		double currClose = data.get(data.size() - 1).getAdjustedClose();
		double upperBandValue = upperChannel.get(upperChannel.size() - 1).getValue();
		double lowerBandValue = lowerChannel.get(upperChannel.size() - 1).getValue();
		
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