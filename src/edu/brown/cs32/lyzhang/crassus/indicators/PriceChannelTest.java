package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class PriceChannelTest {

	private static PriceChannel pc;
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("a", 0, 1, 3, 88.71, 0, 0, false)); 
		data.add(new StockTimeFrameData("b", 0, 2, 32, 89.05, 0, 0, false)); 
		data.add(new StockTimeFrameData("c", 0, 3, 5, 89.24, 0, 0, false)); 
		data.add(new StockTimeFrameData("d", 0, 4, 2, 89.39, 0, 0, false));	
		data.add(new StockTimeFrameData("e", 0, 5, 56, 89.51, 0, 0, false)); 
		data.add(new StockTimeFrameData("f", 0, 6, 4, 89.69, 0, 0, false)); 
		data.add(new StockTimeFrameData("g", 0, 7, 1, 89.75, 0, 0, false)); 	
		data.add(new StockTimeFrameData("h", 0, 8, 1, 89.91, 0, 0, false)); 
		data.add(new StockTimeFrameData("i", 0, 9, 1, 90.08, 0, 0, false)); 
		data.add(new StockTimeFrameData("j", 0, 10, 6, 90.38, 0, 0, false)); 
		data.add(new StockTimeFrameData("k", 0, 15, 4, 90.66, 0, 0, false)); 
		data.add(new StockTimeFrameData("l", 0, 52, 36, 90.86, 0, 0, false)); 
		data.add(new StockTimeFrameData("m", 0, 8, 3, 90.88, 0, 0, false)); 
		data.add(new StockTimeFrameData("n", 0, 2, 2, 90.91, 0, 0, false)); 
		data.add(new StockTimeFrameData("o", 0, 92, 5, 90.99, 0, 0, false)); 
		data.add(new StockTimeFrameData("p", 0, 3, 1, 91.15, 0, 0, false));
		data.add(new StockTimeFrameData("q", 0, 2, 6, 91.19, 0, 0, false)); 
		data.add(new StockTimeFrameData("r", 0, 5, 1, 91.12, 0, 0, false)); 
		data.add(new StockTimeFrameData("s", 0, 99, 9, 91.17, 0, 0, false)); 
		data.add(new StockTimeFrameData("t", 0, 1, 1, 91.25, 0, 0, false)); 
		data.add(new StockTimeFrameData("1", 0, 673, 0.5, 91.24, 0, 0, false)); // 0
		data.add(new StockTimeFrameData("2", 0, 6, 0.2, 91.17, 0, 0, false)); 
		//data.add(new StockTimeFrameData("3", 0, 2, 2, 91.05, 0, 0, false)); 
		pc = new PriceChannel(data, 20);
	}

	@Test
	public void test() {
		List<IndicatorDatum> upperChannel = pc.getUpperChannel(); 
		List<IndicatorDatum> lowerChannel = pc.getLowerChannel(); 
		List<IndicatorDatum> centreLine = pc.getCentreLine(); 
		
		assertTrue(upperChannel.size() == 2);
		assertTrue(lowerChannel.size() == 2);
		assertTrue(centreLine.size() == 2);
		
		assertTrue(upperChannel.get(0).getValue() == 99);
		assertTrue(upperChannel.get(0).getTimeLabel().equals("1"));
		
		assertTrue(upperChannel.get(1).getValue() == 673);
		assertTrue(upperChannel.get(1).getTimeLabel().equals("2"));
		
		assertTrue(lowerChannel.get(0).getValue() == 1);
		assertTrue(lowerChannel.get(0).getTimeLabel().equals("1"));
		
		assertTrue(lowerChannel.get(1).getValue() == 0.5);
		assertTrue(lowerChannel.get(1).getTimeLabel().equals("2"));
		
		assertTrue(centreLine.get(0).getValue() == 50);
		assertTrue(centreLine.get(0).getTimeLabel().equals("1"));
		
		assertTrue(centreLine.get(1).getValue() == 336.75);
		assertTrue(centreLine.get(1).getTimeLabel().equals("2"));
		
		pc.incrementalUpdate(new StockTimeFrameData("3", 0, 2, 2, 91.05, 0, 0, false));
		upperChannel = pc.getUpperChannel(); 
		lowerChannel = pc.getLowerChannel(); 
		centreLine = pc.getCentreLine(); 
		
		assertTrue(upperChannel.get(2).getValue() == 673);
		assertTrue(upperChannel.get(2).getTimeLabel().equals("3"));
		
		assertTrue(lowerChannel.get(2).getValue() == 0.2);
		assertTrue(lowerChannel.get(2).getTimeLabel().equals("3"));
		
		assertTrue(centreLine.get(2).getValue() == 336.6);
		assertTrue(centreLine.get(2).getTimeLabel().equals("3"));
		
	}
}
