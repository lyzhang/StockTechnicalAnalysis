/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import edu.brown.cs32.lyzhang.crassus.gui.PlotWrapper;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;
import java.util.ArrayList;
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
public class StockImplTest {
    
    public StockImplTest() {
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


    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetCompanyName() {
        System.out.println("getCompanyName");
        
        Stock googleStock = new StockImpl("GOOG");
        //googleStock.initialize();
        String result = googleStock.getCompanyName();
    }

    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetDailyStockData() {
        System.out.println("get daily StockData");
        
        Stock s = new StockImpl("MSFT");
        s.initialize();
        List<StockTimeFrameData> stockPriceData = s.getStockPriceData(StockFreqType.DAILY);
        if(stockPriceData.size() > 0) {
            // print the first timeStamp
            System.out.println(stockPriceData.get(0).getTime());       
            System.out.println(stockPriceData.get(0).getTimeInNumber());    
            // print the second timeStamp
            if(stockPriceData.size() > 1) {
                System.out.println(stockPriceData.get(1).getTime());
                System.out.println(stockPriceData.get(1).getTimeInNumber());                   
            }
            // print the third timeStamp
            if(stockPriceData.size() > 2) {
                System.out.println(stockPriceData.get(2).getTime());
                System.out.println(stockPriceData.get(2).getTimeInNumber());                   
            }            
            // print the fourth timeStamp
            if(stockPriceData.size() > 3) {
                System.out.println(stockPriceData.get(3).getTime());
                System.out.println(stockPriceData.get(3).getTimeInNumber());                   
            }             
            // print the last timeStamp
            System.out.println(stockPriceData.get(stockPriceData.size()-1).getTime());
            System.out.println(stockPriceData.get(stockPriceData.size()-1).getTimeInNumber());        
        }
    }
    

    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetWeeklyStockData() {
        System.out.println("get weekly tockData");
        
        Stock s = new StockImpl("MSFT");
        s.initialize();
        List<StockTimeFrameData> stockPriceData = s.getStockPriceData(StockFreqType.WEEKLY);
        if(stockPriceData.size() > 0) {
            // print the first timeStamp
            System.out.println(stockPriceData.get(0).getTime());            
            // print the second timeStamp
            if(stockPriceData.size() > 1) {
                System.out.println(stockPriceData.get(1).getTime());
            }
            // print the third timeStamp
            if(stockPriceData.size() > 2) {
                System.out.println(stockPriceData.get(2).getTime());
            }     
            // print the fourth timeStamp
            if(stockPriceData.size() > 3) {
                System.out.println(stockPriceData.get(3).getTime());
            }            
            // print the last timeStamp
            System.out.println(stockPriceData.get(stockPriceData.size()-1).getTime());
        }
    }
    

    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetMinutelyStockData() {
        System.out.println("get minutely stockData");
        
        Stock s = new StockImpl("GOOG");
        s.initialize();
        List<StockTimeFrameData> stockPriceData = s.getStockPriceData(StockFreqType.MINUTELY);
        if(stockPriceData.size() > 0) {
            // print the first timeStamp
            System.out.println(stockPriceData.get(0).getTime());            
            // print the second timeStamp
            if(stockPriceData.size() > 1) {
                System.out.println(stockPriceData.get(1).getTime());
            }
            // print the third timeStamp
            if(stockPriceData.size() > 2) {
                System.out.println(stockPriceData.get(2).getTime());
            }     
            // print the fourth timeStamp
            if(stockPriceData.size() > 3) {
                System.out.println(stockPriceData.get(3).getTime());
            }            
            // print the last timeStamp
            System.out.println(stockPriceData.get(stockPriceData.size()-1).getTime());
        }
    }    

    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetMonthlyStockData() {
        System.out.println("get monthly tockData");
        
        Stock s = new StockImpl("GOOG");
        s.initialize();
        List<StockTimeFrameData> stockPriceData = s.getStockPriceData(StockFreqType.MONTHLY);
        if(stockPriceData.size() > 0) {
            // print the first timeStamp
            System.out.println(stockPriceData.get(0).getTime());            
            // print the second timeStamp
            if(stockPriceData.size() > 1) {
                System.out.println(stockPriceData.get(1).getTime());
            }
            // print the third timeStamp
            if(stockPriceData.size() > 2) {
                System.out.println(stockPriceData.get(2).getTime());
            }     
            // print the fourth timeStamp
            if(stockPriceData.size() > 3) {
                System.out.println(stockPriceData.get(3).getTime());
            }            
            // print the last timeStamp
            System.out.println(stockPriceData.get(stockPriceData.size()-1).getTime());
        }
    }    
    

    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetPriceOpen() {
        System.out.println("get open price");
        
        Stock s = new StockImpl("GOOG");
        s.initialize();
        String open = s.getStockRealTimeData().getOpenPrice();
        System.out.println("Open: " + open);
        String price = s.getStockRealTimeData().getCurrPrice();
        System.out.println("price: " + price); 
        
        s.refresh();
        open = s.getStockRealTimeData().getOpenPrice();
        System.out.println("Open: " + open);
        price = s.getStockRealTimeData().getCurrPrice();
        System.out.println("price: " + price);         
        
    }        
//
//    /**
//     * Test of getCompanyName method, of class StockImpl.
//     */
//    @Test
//    public void testGetPlotData() {
//        System.out.println("get stock price Plot Data");
//        
//        Stock s = new StockImpl("MSFT"); 
//        s.initialize();        
//        s.setCurrFreq("daily");
//        
//        StockPlot pw = new PlotWrapper("MSFT");        
//        s.addToPlot(pw);         
//        
//        // after 2 minutes
//        s.refresh();
//        
//        // clear series in pw here
//        
//        // re-add plot
//        s.addToPlot(pw);       
//
//        
//    } 
}
