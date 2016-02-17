package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class MACDTest {
	
	private static MACD macd;
	private static List<StockTimeFrameData> data;
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		data = new ArrayList<>();
		data.add(new StockTimeFrameData("1", 0, 0, 0, 0, 0, 22.27, false)); 
		data.add(new StockTimeFrameData("2", 0, 0, 0, 0, 0, 22.19, false)); 
		data.add(new StockTimeFrameData("3", 0, 0, 0, 0, 0, 22.08, false)); 
		data.add(new StockTimeFrameData("4", 0, 0, 0, 0, 0, 22.17, false));	
		data.add(new StockTimeFrameData("5", 0, 0, 0, 0, 0, 22.18, false)); 
		data.add(new StockTimeFrameData("6", 0, 0, 0, 0, 0, 22.13, false)); 
		data.add(new StockTimeFrameData("7", 0, 0, 0, 0, 0, 22.23, false)); 	
		data.add(new StockTimeFrameData("8", 0, 0, 0, 0, 0, 22.43, false)); 
		data.add(new StockTimeFrameData("9", 0, 0, 0, 0, 0, 22.24, false)); 
		data.add(new StockTimeFrameData("10", 0, 0, 0, 0, 0, 22.29, false)); 
		data.add(new StockTimeFrameData("11", 0, 0, 0, 0, 0, 22.15, false)); 
		data.add(new StockTimeFrameData("12", 0, 0, 0, 0, 0, 22.39, false)); 
		data.add(new StockTimeFrameData("13", 0, 0, 0, 0, 0, 22.38, false)); 
		data.add(new StockTimeFrameData("14", 0, 0, 0, 0, 0, 22.61, false)); 
		//data.add(new StockTimeFrameData("15", 0, 0, 0, 0, 0, 23.36, false)); 

		macd = new MACD(data, 10, 4, 6);
	}

	@Test
	public void testSignalLine() {
		assertTrue(macd.getSignalLine().size() == 5);
		assertTrue(Math.abs(macd.getSignalLine().get(0).getValue() - 22.22099) < 0.00001);
		assertTrue(Math.abs(macd.getSignalLine().get(1).getValue() - 22.2080909090) < 0.00001);
		assertTrue(Math.abs(macd.getSignalLine().get(2).getValue() - 22.241165289) < 0.00001);
		assertTrue(Math.abs(macd.getSignalLine().get(3).getValue() - 22.266407963936) < 0.00001);
		assertTrue(Math.abs(macd.getSignalLine().get(4).getValue() - 22.32887924322) < 0.00001);
		
		macd.incrementalUpdate(new StockTimeFrameData("15", 0, 0, 0, 0, 0, 23.36, false));
		assertTrue(Math.abs(macd.getSignalLine().get(5).getValue() - 22.5163557444) < 0.00001);
	}
	
	@Test
	public void testMACDLine() {
		macd = new MACD(data, 7, 11, 12);
		assertTrue((macd.getMACDLine().size()) == 4);
		assertTrue(Math.abs(macd.getMACDLine().get(0).getValue() - 0.0146212) < 0.00001);
		assertTrue(Math.abs(macd.getMACDLine().get(1).getValue() - 0.0141181) < 0.00001);
		assertTrue(Math.abs(macd.getMACDLine().get(2).getValue() - 0.016350) < 0.00001);
		assertTrue(Math.abs(macd.getMACDLine().get(3).getValue() - 0.02712003) < 0.00001);
	}

}