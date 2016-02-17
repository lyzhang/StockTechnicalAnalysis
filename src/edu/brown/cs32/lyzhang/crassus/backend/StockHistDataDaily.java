/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.lyzhang.crassus.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */
public class StockHistDataDaily implements StockHistData {

    private String _ticker;
    private ArrayList<StockTimeFrameData> _histData;
    private String _currDate;
    DateFormat _dateFormat;
    public StockHistDataDaily(String ticker) {
        _ticker = ticker;
        _histData = new ArrayList<StockTimeFrameData>();
        _currDate = null;
        _dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public boolean Init() { 
        
        if(_currDate == null) {            
            Date date = new Date();
            this._currDate = _dateFormat.format(date);            
        } else {
    
            Date date = new Date();
            String today = _dateFormat.format(date); 
            // when the data does not change, the daily, monthly, weekly data won't change
            // if date does not change, there is no need to retrive the hist data again because they will be same.
            if(today.equals(this._currDate)) {   
                return true;
            }
        }        
        
        _histData.clear();
        String begYear = "1900";
        String urlString = "http://ichart.finance.yahoo.com/table.csv?s=" + _ticker + "&c=" + begYear;
        HttpURLConnection connection = null;
        URL serverAddress = null;
        //OutputStreamWriter wr = null;
        BufferedReader reader = null;
        //StringBuilder sb = null;
        String line = null;

        try {
            serverAddress = new URL(urlString);
            //set up out communications stuff
            connection = null;

            //Set up the initial connection
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            connection.connect();
            //read the result from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //sb = new StringBuilder();

            line = reader.readLine();  // skip the header line
            while ((line = reader.readLine()) != null) {

                String[] splitted = line.split(",");
                if (splitted.length < 7) {
                    System.err.println("ERROR: wrong data:" + _ticker + ":" + line);
                    System.exit(1);

                }

                StockTimeFrameData newTFData = new StockTimeFrameData(splitted[0], //Time
                        Double.parseDouble(splitted[1]),   //Open				
                        Double.parseDouble(splitted[2]),   //High				
                        Double.parseDouble(splitted[3]),   //Low
                        Double.parseDouble(splitted[4]),   //Close	
                        Integer.parseInt(splitted[5]),     //Volume
                        Double.parseDouble(splitted[6]),
                        true);  //Adjusted Close
                _histData.add(0, newTFData);   // from the earliest to the latest
            }

            return true;
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            //System.exit(1);
            return false;
        } catch (ProtocolException e) {
            //e.printStackTrace();
            //System.exit(1);
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error: Cannot connect to data server");
            //System.exit(1);
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //System.exit(1);
            return false;
        }
    }

    @Override
    public List<StockTimeFrameData> getHistData() {
        return _histData;
    }

    @Override
    public String getFreq() {
        return "Daily";
    }
}
