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
 *
 * @author lyzhang
 * 
 * Pivot points are calculated on past data and are predictive as
 * opposed to lagging. 
 * 
 * This class can take in data of varying time
 * divisions e.g. daily, weekly, monthly, etc. 
 * 
 * Once pivot points and resistance and support lines are calculated,
 * they remain in effect for the duration of the time division; e.g.
 * the pivot point calculated for one day based on the previous day
 * remains active for the duration of that day.
 *
 * Note: demark has pivot point, support 1 and resistance 1 only
 *       fib has up to 3 support and resistance lines
 *       standard has up to 3 as well
 */
public class PivotPoints implements Indicator {
	
	private List<IndicatorDatum> pivotPoints;
	private List<IndicatorDatum> support1;
	private List<IndicatorDatum> support2;
	private List<IndicatorDatum> support3;
	private List<IndicatorDatum> resistance1;
	private List<IndicatorDatum> resistance2;
	private List<IndicatorDatum> resistance3;
	private List<StockTimeFrameData> data;
	private int pivotType;
	private boolean isVisible;
	private boolean isActive;
	private String pivotOption;
	
	public PivotPoints(List<StockTimeFrameData> data, String pivotOption) {
		this.data = data;
		this.pivotOption = pivotOption;
		pivotPoints = new ArrayList<IndicatorDatum>();
		support1 = new ArrayList<IndicatorDatum>();
		support2 = new ArrayList<IndicatorDatum>();
		support3 = new ArrayList<IndicatorDatum>();
		resistance1 = new ArrayList<IndicatorDatum>();
		resistance2 = new ArrayList<IndicatorDatum>();
		resistance3 = new ArrayList<IndicatorDatum>();
		if(pivotOption.equals("standard")) {
			pivotType = 0;
        } else if(pivotOption.equals("fibonacci")) {
            pivotType = 1;
        } else if(pivotOption.equals("demark")) {
            pivotType = 2;
        } else  {
            pivotType = 0;
        }
		refresh(data);
	}
	
	// Indicator parameters
	public String getPivotOption() {
		return pivotOption;
	}
	//

	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {
		
		SeriesWrapper p = stockPlot.getTimeSeries(pivotPoints, "Pivot Line", startTime, endTime, Color.magenta);

		SeriesWrapper s1 = stockPlot.getTimeSeries(support1, "Support Line 1", startTime, endTime, Color.red);
		SeriesWrapper s2 = stockPlot.getTimeSeries(support2, "Support Line 2", startTime, endTime, Color.blue);
		SeriesWrapper s3 = stockPlot.getTimeSeries(support3, "Support Line 3", startTime, endTime, Color.GREEN);
		
		SeriesWrapper r1 = stockPlot.getTimeSeries(resistance1, "Resistance Line 1", startTime, endTime, Color.pink);
		SeriesWrapper r2 = stockPlot.getTimeSeries(resistance2, "Resistance Line 2", startTime, endTime, Color.cyan);
		SeriesWrapper r3 = stockPlot.getTimeSeries(resistance3, "Resistance Line 3", startTime, endTime, Color.green);
		
		stockPlot.addSeries(p);
		stockPlot.addSeries(s1);
		stockPlot.addSeries(s2);
		stockPlot.addSeries(s3);
		stockPlot.addSeries(r1);
		stockPlot.addSeries(r2);
		stockPlot.addSeries(r3);
	}
	
	List<IndicatorDatum> getPivotPoints() {
		return pivotPoints;
	}
	
	List<IndicatorDatum> getResistance1() {
		return resistance1;
	}
	
	List<IndicatorDatum> getResistance2() {
		return resistance2;
	}
	
	List<IndicatorDatum> getResistance3() {
		return resistance3;
	}
	
	List<IndicatorDatum> getSupport1() {
		return support1;
	}
	
	List<IndicatorDatum> getSupport2() {
		return support2;
	}
	
	List<IndicatorDatum> getSupport3() {
		return support3;
	}
	
	@Override
	public String getName() {
		switch (pivotType) {
			case 0: return "Standard Pivot Points"; 
			case 1: return "Fibonacci Pivot Points";
			case 2: return "Demark Pivot Points";
			default: return "";
		}
	}

	
	public void incrementalUpdate(StockTimeFrameData datum) {

		int lastIndex = data.size() - 1;
		StockTimeFrameData previous = data.get(lastIndex - 1);
		String currTimeLabel = datum.getTime();
		long currTime = datum.getTimeInNumber();
		double pivot = (previous.getHigh() + previous.getAdjustedClose() + previous.getLow()) / 3;
		double high = previous.getHigh();
		double low = previous.getLow();
		
		double s1, s2, s3, r1 = 0, r2, r3;
		
		if (pivotType == 0 || pivotType == 1) {
			if (pivotType == 1) {								// fibonacci
				s1 = pivot - (0.382 * (high - low));
				s2 = pivot - (0.618 * (high - low));
				s3 = pivot - (high - low);
				r1 = pivot + (0.382 * (high - low));
				r2 = pivot + (0.618 * (high - low));
				r3 = pivot + (high - low);
			
			} else {											// standard pivot
			
				r1 = 2 * pivot - low;
				s1 = 2 * pivot - high;
				r2 = pivot + r1 - s1;
				s2 = pivot - (r1 - s1);
				s3 = low - 2 * (high - pivot);
				r3 = high + 2 * (pivot - low);
			
			}
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, currTime, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, currTime, s1));
			support2.add(new IndicatorDatum(currTimeLabel, currTime, s2));
			support3.add(new IndicatorDatum(currTimeLabel, currTime, s3));
			
			resistance1.add(new IndicatorDatum(currTimeLabel, currTime, r1));
			resistance2.add(new IndicatorDatum(currTimeLabel, currTime, r2));
			resistance3.add(new IndicatorDatum(currTimeLabel, currTime, r3));
		} else {	
			double close = previous.getAdjustedClose();
			double open = previous.getOpen();
			double x;
			if (close < open) {
				x = high + (2 * low) + close;
			} else if (close > open) {
				x = (2 * high) + low + close;
			} else {
				x = high + low + (2 * close);
			}
			
			pivot = x / 4;
			s1 = x/2 - high;
			r1 = x/2 - low;
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, currTime, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, currTime, s1));
			resistance1.add(new IndicatorDatum(currTimeLabel, currTime, r1));
			
		}
	
	}
	
	
	/**
	 * Updates the standard pivot points and resistance and support lines. 
	 */
	private void updatePivot() {
		
		// first value has no previous
		for (int i = 1; i < data.size(); i++) {
			StockTimeFrameData previous = data.get(i - 1);
			String currTimeLabel = data.get(i).getTime();
			long currTime = data.get(i).getTimeInNumber();
			double pivot = (previous.getHigh() + previous.getAdjustedClose() + previous.getLow()) / 3;
			double high = previous.getHigh();
			double low = previous.getLow();
			
			double s1, s2, s3, r1 = 0, r2, r3;
			if (pivotType == 1) {								// fibonacci
				s1 = pivot - (0.382 * (high - low));
				s2 = pivot - (0.618 * (high - low));
				s3 = pivot - (high - low);
				r1 = pivot + (0.382 * (high - low));
				r2 = pivot + (0.618 * (high - low));
				r3 = pivot + (high - low);
			
			} else {											// standard pivot
			
				r1 = 2 * pivot - low;
				s1 = 2 * pivot - high;
				r2 = pivot + r1 - s1;
				s2 = pivot - (r1 - s1);
				s3 = low - 2 * (high - pivot);
				r3 = high + 2 * (pivot - low);
			
			}
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, currTime, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, currTime, s1));
			support2.add(new IndicatorDatum(currTimeLabel, currTime, s2));
			support3.add(new IndicatorDatum(currTimeLabel, currTime, s3));
			
			resistance1.add(new IndicatorDatum(currTimeLabel, currTime, r1));
			resistance2.add(new IndicatorDatum(currTimeLabel, currTime, r2));
			resistance3.add(new IndicatorDatum(currTimeLabel, currTime, r3));
		}
	}
	
	/**
	 * Updates demark pivot points; differ from above in that demark only
	 * has one resistance and one support line from the pivot.
	 */
	private void updateDemark() {
		
		// first value has no previous
		for (int i = 1; i < data.size(); i++) {
			StockTimeFrameData previous = data.get(i - 1);
			String currTimeLabel = data.get(i).getTime();
			long currTime = data.get(i).getTimeInNumber();
			
			double pivot = (previous.getHigh() + previous.getAdjustedClose() + previous.getLow()) / 3;
			double high = previous.getHigh();
			double low = previous.getLow();
			
			double close = previous.getAdjustedClose();
			double open = previous.getOpen();
			double x;
			if (close < open) {
				x = high + (2 * low) + close;
			} else if (close > open) {
				x = (2 * high) + low + close;
			} else {
				x = high + low + (2 * close);
			}
			
			pivot = x / 4;
			double s1 = x/2 - high;
			double r1 = x/2 - low;
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, currTime, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, currTime, s1));
			resistance1.add(new IndicatorDatum(currTimeLabel, currTime, r1));
		}
	}
	

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		if (pivotType == 2) updateDemark();
		else updatePivot();
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