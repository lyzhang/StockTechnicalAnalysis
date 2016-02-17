package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class BollingerBandsTest {

	private static BollingerBands bb; 
	
	private static List<StockTimeFrameData> data;
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		data = new ArrayList<>();
		data.add(new StockTimeFrameData("11", 0, 0, 0, 0, 0, 88.71, false)); 
		data.add(new StockTimeFrameData("12", 0, 0, 0, 0, 0, 89.05, false)); 
		data.add(new StockTimeFrameData("13", 0, 0, 0, 0, 0, 89.24, false)); 
		data.add(new StockTimeFrameData("14", 0, 0, 0, 0, 0, 89.39, false));	
		data.add(new StockTimeFrameData("15", 0, 0, 0, 0, 0, 89.51, false)); 
		data.add(new StockTimeFrameData("16", 0, 0, 0, 0, 0, 89.69, false)); 
		data.add(new StockTimeFrameData("17", 0, 0, 0, 0, 0, 89.75, false)); 	
		data.add(new StockTimeFrameData("18", 0, 0, 0, 0, 0, 89.91, false)); 
		data.add(new StockTimeFrameData("19", 0, 0, 0, 0, 0, 90.08, false)); 
		data.add(new StockTimeFrameData("20", 0, 0, 0, 0, 0, 90.38, false)); 
		data.add(new StockTimeFrameData("21", 0, 0, 0, 0, 0, 90.66, false)); 
		data.add(new StockTimeFrameData("22", 0, 0, 0, 0, 0, 90.86, false)); 
		data.add(new StockTimeFrameData("23", 0, 0, 0, 0, 0, 90.88, false)); 
		data.add(new StockTimeFrameData("24", 0, 0, 0, 0, 0, 90.91, false)); 
		data.add(new StockTimeFrameData("25", 0, 0, 0, 0, 0, 90.99, false)); 
		data.add(new StockTimeFrameData("26", 0, 0, 0, 0, 0, 91.15, false));
		data.add(new StockTimeFrameData("27", 0, 0, 0, 0, 0, 91.19, false)); 
		data.add(new StockTimeFrameData("28", 0, 0, 0, 0, 0, 91.12, false)); 
		data.add(new StockTimeFrameData("29", 0, 0, 0, 0, 0, 91.17, false)); 
		data.add(new StockTimeFrameData("1", 0, 0, 0, 0, 0, 91.25, false)); 
		data.add(new StockTimeFrameData("2", 0, 0, 0, 0, 0, 91.24, false)); 
		data.add(new StockTimeFrameData("3", 0, 0, 0, 0, 0, 91.17, false)); 
		//data.add(new StockTimeFrameData("4", 0, 0, 0, 0, 0, 91.05, false)); 
		bb = new BollingerBands(data, 20, 2);
	}
	
	@Test
	public void testCalcSMA() {
		assertTrue(bb.calcSMA(0, 0) == 88.71);									// 1 day SMA
		assertTrue(bb.calcSMA(0, 1) == 88.88);									// 2 day SMA
		assertTrue(bb.calcSMA(0, 2) == 89);										// 3 day SMA
		assertTrue(bb.calcSMA(0, 3) == 89.0975);								// 4 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 4) - 89.18)) < 0.00001);				// 5 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 5) - 89.265)) < 0.00001);			// 6 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 6) - 89.33428571)) < 0.00001);		// 7 day SMA
		assertTrue(bb.calcSMA(0, 19) == 90.2945);								// 20 day SMA
		//assertTrue(Math.abs((bb.calcSMA(18, 22) - 91.176)) < 0.00001);			// 5 day SMA at the end
	}
	
	@Test
	public void testCalcStdDev() {
		assertTrue(bb.calcStdDev(0, 0, bb.calcSMA(0, 0)) == 0);
		assertTrue(Math.abs(bb.calcStdDev(0, 1, bb.calcSMA(0, 1)) - 0.17) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 2, bb.calcSMA(0, 2)) - 0.21924) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 3, bb.calcSMA(0, 3)) - 0.254103) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 4, bb.calcSMA(0, 4)) - 0.280855) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 5, bb.calcSMA(0, 5)) - 0.319152) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 6, bb.calcSMA(0, 6)) - 0.340749) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 19, bb.calcSMA(0, 19)) - 0.806892) < 0.00001);
		//assertTrue(Math.abs(bb.calcStdDev(18,22, bb.calcSMA(18, 22)) - 0.0714422) < 0.00001);
	}
	
	@Test
	public void testGetBands() {

		List<IndicatorDatum> upperBand = bb.getUpperBand();
		List<IndicatorDatum> middleBand = bb.getMiddleBand();
		List<IndicatorDatum> lowerBand = bb.getLowerBand();
		
		assertTrue(upperBand.size() == 3);
		assertTrue(middleBand.size() == 3);
		assertTrue(lowerBand.size() == 3);
		
		assertTrue(Math.abs(upperBand.get(0).getValue()) - 91.908284 < 0.00001);
		assertTrue(upperBand.get(0).getTimeLabel().equals("1"));
		assertTrue(Math.abs(middleBand.get(0).getValue()) - 90.2945 < 0.00001);
		assertTrue(middleBand.get(0).getTimeLabel().equals("1"));
		assertTrue(Math.abs(lowerBand.get(0).getValue()) - 88.680716 < 0.00001);
		assertTrue(lowerBand.get(0).getTimeLabel().equals("1"));

		assertTrue(Math.abs(upperBand.get(1).getValue()) - 91.90994 < 0.00001);
		assertTrue(upperBand.get(1).getTimeLabel().equals("2"));
		assertTrue(Math.abs(middleBand.get(1).getValue()) - 90.421 < 0.00001);
		assertTrue(middleBand.get(1).getTimeLabel().equals("2"));
		assertTrue(Math.abs(lowerBand.get(1).getValue()) - 88.93506 < 0.00001);
		assertTrue(lowerBand.get(1).getTimeLabel().equals("2"));

		assertTrue(Math.abs(upperBand.get(2).getValue() - 91.9084) < 0.00001);
		assertTrue(upperBand.get(2).getTimeLabel().equals("3"));
		assertTrue(Math.abs(middleBand.get(2).getValue() - 90.5270) < 0.00001);
		assertTrue(middleBand.get(2).getTimeLabel().equals("3"));
		assertTrue(Math.abs(lowerBand.get(2).getValue() - 89.145594) < 0.00001);
		assertTrue(lowerBand.get(2).getTimeLabel().equals("3"));

		bb.incrementalUpdate(new StockTimeFrameData("4", 0, 0, 0, 0, 0, 91.05, false));
		upperBand = bb.getUpperBand();
		middleBand = bb.getMiddleBand();
		lowerBand = bb.getLowerBand();
		
		assertTrue(Math.abs(upperBand.get(3).getValue()) - 91.88199 < 0.00001);
		assertTrue(upperBand.get(3).getTimeLabel().equals("4"));
		assertTrue(Math.abs(middleBand.get(3).getValue()) - 90.6175 < 0.00001);
		assertTrue(middleBand.get(3).getTimeLabel().equals("4"));
		assertTrue(Math.abs(lowerBand.get(3).getValue()) - 89.3530 < 0.00001);
		assertTrue(lowerBand.get(3).getTimeLabel().equals("4"));
	}

	
}