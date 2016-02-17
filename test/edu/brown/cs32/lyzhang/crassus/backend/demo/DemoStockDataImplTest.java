/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend.demo;

import edu.brown.cs32.lyzhang.crassus.backend.StockRealTimeData;
import edu.brown.cs32.lyzhang.crassus.backend.StockRealTimeDataImpl;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lyzhang
 */
public class DemoStockDataImplTest {
    
    public DemoStockDataImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private void printTimeFrameData(StockTimeFrameData data) {
        Calendar calendar = Calendar.getInstance();            
        long tmp = (Long.parseLong(data.getTime()));
        tmp = tmp * 1000;
        calendar.setTimeInMillis(tmp);
        String timeStamp = calendar.getTime().toString();
        System.out.println("First data timestamp: " + timeStamp + "," +  tmp +  "close price = " + data.getClose() );        
    }
    /**
     * Test of getRealTimeData method, of class StockRealTimeDataDemoImpl.
     */
    @Test
    public void testGetRealTimeData() {
        System.out.println("getRealTimeData");
        //System.out.println(System.currentTimeMillis());
        DemoStockDataImpl instance = new DemoStockDataImpl("goog");
        instance.Init();
        
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        String timeStampNow = calendar.getTime().toString();
        System.out.println("Current time: " + timeStampNow);

        List<StockTimeFrameData> allRealTimeData = instance.getHistData();
        
        String open = instance.getOpenPrice();
        System.out.println("Open: " + open);        
        String price = instance.getCurrPrice();
        System.out.println("price: " + price); 
        
        int length = allRealTimeData.size();
        printTimeFrameData(allRealTimeData.get(0));
        printTimeFrameData(allRealTimeData.get(1));        
        printTimeFrameData(allRealTimeData.get(length-3));        
        printTimeFrameData(allRealTimeData.get(length-2));
        printTimeFrameData(allRealTimeData.get(length-1));  
    
    }

}
