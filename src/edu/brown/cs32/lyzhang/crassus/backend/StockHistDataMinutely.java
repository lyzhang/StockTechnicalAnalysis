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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author
 */
public class StockHistDataMinutely implements StockHistData {
    private String _ticker;
    private List<StockTimeFrameData> _histData;   // 15 days data at 3-min
    private List<StockTimeFrameData> _histData2;   // 1 day data at 1-min
    private Date latestCheckTime = null;
    private Date latestCheckTime2 = null;    
   
    
    public StockHistDataMinutely(String ticker) {
        _ticker = ticker;
        _histData = new ArrayList<StockTimeFrameData>();
        _histData2 = new ArrayList<StockTimeFrameData>();
    }

    public boolean Init() {
        //return Init2() && Init1();
        return Init2();   // ONE-WEEK has been removed.  no need to support 3-minute data.
    }    
    
    private boolean Init1() {
        
        Date now = new Date();        
        
        if( latestCheckTime != null && now.getTime() < latestCheckTime.getTime() + 300000) {   // minutely time only update once every 300 seond
            return true;  
        }
        
        _histData.clear();

        String urlString = "http://chartapi.finance.yahoo.com/instrument/1.0/" + _ticker + "/chartdata;type=quote;range=15d/csv/";
        // http://chartapi.finance.yahoo.com/instrument/1.0/msft/chartdata;type=quote;range=15d/csv/        
        HttpURLConnection connection = null;
        URL serverAddress = null;
        BufferedReader reader = null;
        String line = null;

        try {
            latestCheckTime = now;
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
            
            String[] sep_list = {",", ":"};
            StringBuilder regexp = new StringBuilder("");
            regexp.append("[");
            for (String s : sep_list) {
                regexp.append(Pattern.quote(s));;
            }
            regexp.append("]");
        
            line = reader.readLine();  // skip the header line
            while ((line = reader.readLine()) != null) {

                String[] splitted = line.split(regexp.toString());
                
                if (splitted.length != 6) {
                    continue;
                }
                if(splitted[5].equals("volume") || splitted[0].equals("labels")) {
                    continue;
                }
//                long tmp = (Long.parseLong(splitted[0]));
//                tmp = tmp*1000;
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(tmp);
//                String timeStamp = calendar.getTime().toString();
                
                StockTimeFrameData newTFData = new StockTimeFrameData(splitted[0], //Time
                        Double.parseDouble(splitted[4]),   //Open				
                        Double.parseDouble(splitted[2]),   //High				
                        Double.parseDouble(splitted[3]),   //Low
                        Double.parseDouble(splitted[1]),   //Close	
                        Integer.parseInt(splitted[5]),     //Volume
                        Double.parseDouble(splitted[1]),  // realtime data from yahoo has not adjusted close, we set it equal to Close
                        false);  
                    
                _histData.add(newTFData);   // from the earliest to the latest
            }
            
            return true;
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return false;
        } catch (ProtocolException e) {
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error: Cannot connect to data server");
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    private boolean Init2() {
        
        Date now = new Date();        
        
        if( latestCheckTime2 != null && now.getTime() < latestCheckTime2.getTime() + 60000) {   // minutely time only update once every 300 seond
            return true;  
        }
        
        _histData2.clear();

        String urlString = "http://chartapi.finance.yahoo.com/instrument/1.0/" + _ticker + "/chartdata;type=quote;range=1d/csv/";
        // http://chartapi.finance.yahoo.com/instrument/1.0/msft/chartdata;type=quote;range=1d/csv/        
        HttpURLConnection connection = null;
        URL serverAddress = null;
        BufferedReader reader = null;
        String line = null;

        try {
            latestCheckTime2 = now;
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
            
            String[] sep_list = {",", ":"};
            StringBuilder regexp = new StringBuilder("");
            regexp.append("[");
            for (String s : sep_list) {
                regexp.append(Pattern.quote(s));;
            }
            regexp.append("]");
        
            line = reader.readLine();  // skip the header line
            while ((line = reader.readLine()) != null) {

                String[] splitted = line.split(regexp.toString());
                
                if (splitted.length != 6) {
                    continue;
                }
                if(splitted[5].equals("volume") || splitted[0].equals("labels")) {
                    continue;
                }
//                long tmp = (Long.parseLong(splitted[0]));
//                tmp = tmp*1000;
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(tmp);
//                String timeStamp = calendar.getTime().toString();
                
                StockTimeFrameData newTFData = new StockTimeFrameData(splitted[0], //Time
                        Double.parseDouble(splitted[4]),   //Open				
                        Double.parseDouble(splitted[2]),   //High				
                        Double.parseDouble(splitted[3]),   //Low
                        Double.parseDouble(splitted[1]),   //Close	
                        Integer.parseInt(splitted[5]),     //Volume
                        Double.parseDouble(splitted[1]),  // realtime data from yahoo has not adjusted close, we set it equal to Close
                        false);  
                    
                _histData2.add(newTFData);   // from the earliest to the latest
            }
            
            return true;
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return false;
        } catch (ProtocolException e) {
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error: Cannot connect to data server");
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<StockTimeFrameData> getHistData() {
        return _histData;
    }    

    public List<StockTimeFrameData> getHistData2() {
        return _histData2;
    }
    @Override
    public String getFreq() {
        return "Minutely";
    }
    
}
