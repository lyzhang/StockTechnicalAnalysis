/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lyzhang
 */
public class StockTimeFrameData {

    private String _time;
    private boolean _isHist;   // is the data retrieved from yahoo stock historical data or yahoo realtime data?
                               // true means  _time has format like "2013-04-26" 
                               // false means _time has format like "1367006400" (a second value  that is an offset from the Epoch, January 1, 1970 00:00:00.000 GMT)
    private double _open;
    private double _high;
    private double _low;
    private double _close;
    private int _volume;
    private double _adjustedClose;

    public StockTimeFrameData(String time, double open, double high, double low,
            double close, int volume, double adjustedClose, boolean isHist) {

    	_time = time;
        _open = open;
        _high = high;
        _low = low;
        _close = close;
        _volume = volume;
        _adjustedClose = adjustedClose;
        _isHist = isHist;
    }

    public StockTimeFrameData(StockTimeFrameData cp) {
    	_time = cp._time;
        _open = cp._open;
        _high = cp._high;
        _low = cp._low;
        _close = cp._close;
        _volume = cp._volume;
        _adjustedClose = cp._adjustedClose;
        _isHist = cp._isHist;
    }
    
    public boolean getIsHist() {
        return _isHist;
    }
    
    public void setIsHist(boolean isHist) {
        _isHist = isHist;
    }
    
    public String getTime() {
        return _time;
    }
    
    // change all the time format to real time format like "1367006400",  so it can be used in XYSeries
    public long getTimeInNumber() {  
        if(_isHist) {
            String[] splitted = _time.split("-");
            if(splitted.length != 3) {
                return 0;  //?? throw exception?
            } else {
                int year = Integer.parseInt(splitted[0]);
                int month = Integer.parseInt(splitted[1]);
                int day = Integer.parseInt(splitted[2]);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date d = calendar.getTime();
                return d.getTime()/1000;   // in second
            }
        } else {
            return Long.parseLong(_time);
        }
    }
    
    public void setTime(String time) {
        _time = time;
    }    

    
    public double getOpen() {
        return _open;
    }

    public double getHigh() {
        return _high;
    }

    public double getLow() {
        return _low;
    }

    public double getClose() {
        return _close;
    }

    public int getVolume() {
        return _volume;
    }

    public double getAdjustedClose() {
        return _adjustedClose;
    }
}