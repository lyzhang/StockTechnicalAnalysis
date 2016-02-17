package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class StochasticOscillatorTest {

	private static StochasticOscillator so;
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("1", 0, 127.009, 125.3574, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("2", 0, 127.6159, 126.1633, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("3", 0, 126.5911, 124.9296, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("4", 0, 127.3472, 126.0937, 0, 0, 0, false));	
		data.add(new StockTimeFrameData("5", 0, 128.173, 126.8199, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("6", 0, 128.4317, 126.4817, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("7", 0, 127.3671, 126.034, 0, 0, 0, false)); 	
		data.add(new StockTimeFrameData("8", 0, 126.422, 124.8301, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("9", 0, 126.8995, 126.3921, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("10", 0, 126.8498, 125.7156, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("11", 0, 125.646, 124.5615, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("12", 0, 125.7156, 124.5715, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("13", 0, 127.1582, 125.0689, 0, 0, 0, false)); 
		data.add(new StockTimeFrameData("14", 0, 127.7154, 126.8597, 0, 0, 127.2876, false)); 
		data.add(new StockTimeFrameData("15", 0, 127.6855, 126.6309, 0, 0, 127.1781, false)); 
		
		data.add(new StockTimeFrameData("16", 0, 128.2228, 126.8001, 0, 0, 128.0138, false)); 
		data.add(new StockTimeFrameData("17", 0, 128.2725, 126.7105, 0, 0, 127.1085, false)); 
		data.add(new StockTimeFrameData("18", 0, 128.0934, 126.8001, 0, 0, 127.7253, false)); 
		data.add(new StockTimeFrameData("19", 0, 128.2725, 126.1335, 0, 0, 127.0587, false)); 
		data.add(new StockTimeFrameData("20", 0, 127.7353, 125.9245, 0, 0, 127.3273, false)); 
		//data.add(new StockTimeFrameData("21", 0, 128.77, 126.9891, 0, 0, 128.7103, false)); 
		

		so = new StochasticOscillator(data, 14);
	}
	
	@Test
	public void testStocOscillator() {
		
		List<IndicatorDatum> stocOscillator = so.getStocOscillator();
		assertTrue(stocOscillator.size() == 7);
	
		assertTrue(Math.abs(stocOscillator.get(0).getValue() - 70.43822) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(1).getValue() - 67.6089091) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(2).getValue() - 89.202108) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(3).getValue() - 65.81055) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(4).getValue() - 81.7477132) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(5).getValue() - 64.52379721) < 0.00001);
		assertTrue(Math.abs(stocOscillator.get(6).getValue() - 74.5297763) < 0.00001);
		
		so.incrementalUpdate(new StockTimeFrameData("21", 0, 128.77, 126.9891, 0, 0, 128.7103, false));
		stocOscillator = so.getStocOscillator();
		assertTrue(Math.abs(stocOscillator.get(7).getValue() - 98.58144231) < 0.00001);
	
	}

}