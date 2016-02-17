/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import edu.brown.cs32.lyzhang.crassus.backend.AutoComplete.AutoCorrect;
import edu.brown.cs32.lyzhang.crassus.backend.demo.DemoStockImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lyzhang
 */
public class StockListImpl implements StockList {
    
    private ArrayList<Stock> _stocks;
    String _stockListFile = "StockList-byTicker.tsv";
    AutoCorrect _autoCorrectTicker = null;
    AutoCorrect _autoCorrectCompName = null;
    DataSourceType _dataSourceType = DataSourceType.YAHOOFINANCE;
    
    public StockListImpl() {
        _stocks = new ArrayList<Stock>();
        _autoCorrectTicker = new AutoCorrect();
        _autoCorrectTicker.initializer(_stockListFile, "Symbol");       
    }
    
    public StockListImpl(DataSourceType dataSourceType ) {
        _stocks = new ArrayList<Stock>();
        _autoCorrectTicker = new AutoCorrect();
        _autoCorrectTicker.initializer(_stockListFile, "Symbol");
        
        _dataSourceType = dataSourceType;
    }
    
    @Override    
    public List<String> getTickerSuggestion(String tickerPrefix) {
        List<String> result = _autoCorrectTicker.Searcher(tickerPrefix);
        return result;
    }     

//    @Override    
//    public List<String> getCompanyNameSuggestion(String tickerPrefix) {
//        return _autoCorrectCompName.Searcher(tickerPrefix);
//    }
    
    @Override
    public ArrayList<Stock> getStockList() {
        return _stocks;
    }
    

    @Override
    public Stock getStock(String ticker) {
        for(Stock s : _stocks) {
            if(s.getTicker().equalsIgnoreCase(ticker)) {
                return s;
            }
        }
        return null;
    }
    //

    @Override
    public void add(Stock s) {
        _stocks.add(s);
    }
    

    @Override
    public void delete(String ticker) {
         Stock s = this.getStock(ticker);
         if(s!=null) {
            _stocks.remove(s);
         }
     }
    
    @Override 
    public void refreshAll() {
        for(Stock s : _stocks) {
            s.refresh();
        }         
    }
    
    @Override 
    public void refreshPriceDataOnly() {   // won't refresh indicators    
        for(Stock s : _stocks) {
            s.refreshPriceDataOnly();
        }        
    }
    
    @Override    
    public Stock createStock(String ticker) {
        if(_dataSourceType == DataSourceType.YAHOOFINANCE) {
            return new StockImpl(ticker,_dataSourceType);
        } else {
            return new DemoStockImpl(ticker,_dataSourceType);
        }
    }
    
    @Override
    public int getDataRefreshCycle() {    // in Milisecond    
        return 10000;
    }
}
