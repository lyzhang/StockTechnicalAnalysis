/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface StockRealTimeData {
    boolean Init();
    // return up to days real time data (every row corresponds to about a minute) including the latest
    List<StockTimeFrameData> getRealTimeData();   // latest data last

    
    String getChgAndPertChg();    
    String getOpenPrice();
    String getCurrPrice();
    String getTodayLow();
    String getTodayHigh();
    String getWeek52Low();
    String getWeek52High(); 
    
    void refresh();  
}
