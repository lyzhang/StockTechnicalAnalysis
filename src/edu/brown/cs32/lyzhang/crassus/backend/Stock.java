/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;
import edu.brown.cs32.lyzhang.crassus.gui.TimeFrame;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface Stock {
    //void setTicker(String ticker);

    boolean initialize();   // false mean it fails to get data from data source

    String getTicker();

    String getCompanyName();
    
    void setCurrFreq(StockFreqType currFreq);   // MINUTELY, DAILY, WEEKLY, MONTHLY
    void setTimeFrame(TimeFrame timeFrame);
    
    void setTimeFrame(Date beginTime, Date endTime);
    
    StockFreqType getCurrFreq();   // MINUTELY, DAILY, WEEKLY, MONTHLY
    TimeFrame getTimeFrame(); 
    
    String getChgAndPertChg();    
    String getOpenPrice();
    String getCurrPrice();
    String getTodayLow();
    String getTodayHigh();
    String getWeek52Low();
    String getWeek52High();  
    List<StockTimeFrameData> getStockPriceData(StockFreqType freq);
    
    ArrayList<Indicator> getEventList();
    void removeEventList();
    void addEvent(Indicator event);
    void deleteEvent(Indicator event);   
    
    void refresh();
    
    void refreshPriceDataOnly();   // won't refresh indicator
    
    void addToPlot(StockPlot stockPlot);   
    
    Date getStartTime();
    Date getEndTime();    
    
    StockEventType isTriggered();
    void setSelectedIndicatorIndex(int i);
    int getSelectedIndicatorIndex();
}
