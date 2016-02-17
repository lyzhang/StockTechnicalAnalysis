/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class StockHistDataDailyTest {
    
    public StockHistDataDailyTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
    


    /**
     * Test of getHistData method, of class StockHistDataDaily.
     */
    @org.junit.Test
    public void testGetHistData() {
        System.out.println("getHistData");

        StockHistDataDaily instance = new StockHistDataDaily("msft");
        instance.Init();

        List<StockTimeFrameData> allHistData = instance.getHistData();
        StockTimeFrameData firstDay = allHistData.get(0);
        assert(firstDay.getTime().equals("1986-03-13"));        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
  
        String today = dateFormat.format(cal.getTime());     
        
        cal.add(Calendar.DATE, -1);        
        String yesterday = dateFormat.format(cal.getTime());              
        
        String tmp = allHistData.get(allHistData.size()-1).getTime();
        assert(tmp.equals(today) || tmp.equals(yesterday) ); // verify the last entry in allHistData is either today or yesterday

    }

}
