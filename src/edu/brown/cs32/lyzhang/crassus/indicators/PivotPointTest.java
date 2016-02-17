package edu.brown.cs32.lyzhang.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;

public class PivotPointTest {

	private static PivotPoints ppStandard;
	private static PivotPoints ppDemark;
	private static PivotPoints ppFibonacci;
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("1", 434.15, 434.15, 429.09, 0, 0, 429.8, false)); 
		data.add(new StockTimeFrameData("2", 433.72, 437.99, 431.2, 0, 0, 434.33, false)); 
		data.add(new StockTimeFrameData("3", 428.1, 437.06, 426.01, 0, 0, 435.69, false)); 
		data.add(new StockTimeFrameData("4", 426.36, 428.5, 422.75, 0, 0, 426.98, false));	
		data.add(new StockTimeFrameData("5", 424.85, 427.95, 422.49, 0, 0, 426.21, false)); 
		data.add(new StockTimeFrameData("6", 424.5, 424.95, 419.68, 0, 0, 423.2, false)); 

		
		ppStandard = new PivotPoints(data, "standard");
		ppDemark = new PivotPoints(data, "demark");
		ppFibonacci = new PivotPoints(data, "fibonacci");
	}

	@Test
	public void testPivotPointStandard() {
		
		List<IndicatorDatum> support1 = ppStandard.getSupport1(); 
		List<IndicatorDatum> support2 = ppStandard.getSupport2(); 
		List<IndicatorDatum> support3 = ppStandard.getSupport3(); 
		List<IndicatorDatum> resistance1 = ppStandard.getResistance1(); 
		List<IndicatorDatum> resistance2 = ppStandard.getResistance2(); 
		List<IndicatorDatum> resistance3 = ppStandard.getResistance3(); 	
		List<IndicatorDatum> pivotPoints = ppStandard.getPivotPoints(); 

		assertTrue(support1.size() == 5);
		assertTrue(support2.size() == 5);
		assertTrue(support3.size() == 5);
		assertTrue(resistance1.size() == 5);
		assertTrue(resistance2.size() == 5);
		assertTrue(resistance3.size() == 5);
		assertTrue(pivotPoints.size() == 5);
		
		assertTrue(Math.abs(pivotPoints.get(0).getValue() - 431.0133333) < 0.00001);
		assertTrue(pivotPoints.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(support1.get(0).getValue() - 427.87666) < 0.00001);
		assertTrue(support1.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(support2.get(0).getValue() - 425.95333) < 0.00001);
		assertTrue(support2.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(support3.get(0).getValue() - 422.816666) < 0.00001);
		assertTrue(support3.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(resistance1.get(0).getValue() - 432.93666) < 0.00001);
		assertTrue(resistance1.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(resistance2.get(0).getValue() - 436.073333) < 0.00001);
		assertTrue(resistance2.get(0).getTimeLabel().equals("2"));
		
		assertTrue(Math.abs(resistance3.get(0).getValue() - 437.996666) < 0.00001);
		assertTrue(resistance3.get(0).getTimeLabel().equals("2"));
	}
	
	@Test
	public void testPivotPointDemark() {

		List<IndicatorDatum> support1 = ppDemark.getSupport1(); 
		List<IndicatorDatum> support2 = ppDemark.getSupport2(); 
		List<IndicatorDatum> support3 = ppDemark.getSupport3(); 
		List<IndicatorDatum> resistance1 = ppDemark.getResistance1(); 
		List<IndicatorDatum> resistance2 = ppDemark.getResistance2(); 
		List<IndicatorDatum> resistance3 = ppDemark.getResistance3(); 
		List<IndicatorDatum> pivotPoints = ppDemark.getPivotPoints(); 
		
		assertTrue(support1.size() == 5);
		assertTrue(support2.size() == 0);
		assertTrue(support3.size() == 0);
		assertTrue(resistance1.size() == 5);
		assertTrue(resistance2.size() == 0);
		assertTrue(resistance3.size() == 0);
		assertTrue(pivotPoints.size() == 5);
		
		assertTrue(Math.abs(pivotPoints.get(2).getValue() - 433.955) < 0.00001);
		assertTrue(pivotPoints.get(2).getTimeLabel().equals("4"));
		
		assertTrue(Math.abs(support1.get(2).getValue() - 430.85) < 0.00001);
		assertTrue(support1.get(2).getTimeLabel().equals("4"));
		
		assertTrue(Math.abs(resistance1.get(2).getValue() - 441.9) < 0.00001);
		assertTrue(resistance1.get(2).getTimeLabel().equals("4"));
	}
	@Test
	public void testPivotPointFibonacci() {
		
		List<IndicatorDatum> support1 = ppFibonacci.getSupport1(); 
		List<IndicatorDatum> support2 = ppFibonacci.getSupport2(); 
		List<IndicatorDatum> support3 = ppFibonacci.getSupport3();
		List<IndicatorDatum> resistance1 = ppFibonacci.getResistance1(); 
		List<IndicatorDatum> resistance2 = ppFibonacci.getResistance2(); 
		List<IndicatorDatum> resistance3 = ppFibonacci.getResistance3(); 
		List<IndicatorDatum> pivotPoints = ppFibonacci.getPivotPoints(); 
		
		assertTrue(support1.size() == 5);
		assertTrue(support2.size() == 5);
		assertTrue(support3.size() == 5);
		assertTrue(resistance1.size() == 5);
		assertTrue(resistance2.size() == 5);
		assertTrue(resistance3.size() == 5);
		assertTrue(pivotPoints.size() == 5);
		
		assertTrue(Math.abs(pivotPoints.get(4).getValue() - 425.550) < 0.00001);
		assertTrue(pivotPoints.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(support1.get(4).getValue() - 423.464280) < 0.00001);
		assertTrue(support1.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(support2.get(4).getValue() - 422.17572) < 0.00001);
		assertTrue(support2.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(support3.get(4).getValue() - 420.0900) < 0.00001);
		assertTrue(support3.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(resistance1.get(4).getValue() - 427.63572) < 0.00001);
		assertTrue(resistance1.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(resistance2.get(4).getValue() - 428.92428) < 0.00001);
		assertTrue(resistance2.get(4).getTimeLabel().equals("6"));
		
		assertTrue(Math.abs(resistance3.get(4).getValue() - 431.0100) < 0.00001);
		assertTrue(resistance3.get(4).getTimeLabel().equals("6"));
	}

}
