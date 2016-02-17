package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class RSITest {
	
	private static RSI rsi;

	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("1", 0, 0, 0, 0, 0, 88.71, false)); 
		data.add(new StockTimeFrameData("2", 0, 0, 0, 0, 0, 89.05, false)); 
		data.add(new StockTimeFrameData("3", 0, 0, 0, 0, 0, 89.24, false)); 
		data.add(new StockTimeFrameData("4", 0, 0, 0, 0, 0, 89.19, false));	
		data.add(new StockTimeFrameData("5", 0, 0, 0, 0, 0, 89.51, false)); 
		data.add(new StockTimeFrameData("6", 0, 0, 0, 0, 0, 88.69, false)); 
		data.add(new StockTimeFrameData("7", 0, 0, 0, 0, 0, 88.9, false)); 	
		//data.add(new StockTimeFrameData("8", 0, 0, 0, 0, 0, 89.2, false)); 
		rsi = new RSI(data, 4);
	}
	
	@Test
	public void test() {
		List<IndicatorDatum> RSIPoints = rsi.getRSIPoints();
		assertTrue(RSIPoints.size() == 3);
		assertTrue(Math.abs(RSIPoints.get(0).getValue() - 91.37931) < 0.00001);
		assertTrue(Math.abs(RSIPoints.get(1).getValue() - 95.0331125) < 0.00001);
		assertTrue(Math.abs(RSIPoints.get(2).getValue() - 38.81875) < 0.00001);
		
		rsi.incrementalUpdate(new StockTimeFrameData("8", 0, 0, 0, 0, 0, 89.2, false));
		assertTrue(Math.abs(RSIPoints.get(3).getValue() - 49.09977) < 0.00001);
	}

}
