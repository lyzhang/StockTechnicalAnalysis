/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

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
public class StockListImplTest {
    
    public StockListImplTest() {
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
     * Test of getTickerSuggestion method, of class StockListImpl.
     */
    @Test
    public void testGetTickerSuggestion() {
        System.out.println("getTickerSuggestion");
        String tickerPrefix = "msf";
        StockList instance = new StockListImpl();
        List<String> result = instance.getTickerSuggestion(tickerPrefix);
        for(String t : result) {
            System.out.println(t);
        }
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
