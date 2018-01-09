package com.xp.assignment.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StockMarketProcessor {
	private static final String BASE_URL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=";
    static final List<Double> priceList = new ArrayList<Double>();
    public enum Stocks {
    	AMZN,
    	YHOO,
    	FB,
    	GOOGL,
    	MSFT;
    }

	public static void main(String[] args) {    
        final List<String> stockOptionList = Stream.of(Stocks.values()).map(Enum::name).collect(Collectors.toList());
        final Object[] urisToGet = stockOptionList.stream().map( l -> (BASE_URL + l)).toArray();
     // Create an HttpClient with the MultiThreadedHttpConnectionManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        HttpClient httpClient = new HttpClient(
                new MultiThreadedHttpConnectionManager());
         
        // Set the default host/protocol for the methods to connect to.
        // This value will only be used if the methods are not given 
        // an absolute URI
        httpClient.getHostConfiguration().setHost(
                "jakarta.apache.org", 80, "http");
         
         
        // create a thread for each URI
        GetThread[] threads = new GetThread[urisToGet.length];
         
        for (int i = 0; i < threads.length; i++) {
            GetMethod get = new GetMethod(urisToGet[i].toString());
            get.setFollowRedirects(true);
            threads[i] = new GetThread(httpClient, get, i + 1);
        }
        
         
        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
        
        
        
        try
        {
          Thread.sleep(2000);
          OptionalDouble max = priceList.stream().mapToDouble(v->v).max();
          System.out.println(max.getAsDouble());
        }catch(Exception e){
        	
        }
	}
	
	/**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread {
         
        private HttpClient httpClient;
        private GetMethod method;
        private int id;
         
        public GetThread(HttpClient httpClient, GetMethod method, 
                int id) {
             
            this.httpClient = httpClient;
            this.method = method;
            this.id = id;
             
        }
         
        /**
         * Executes the GetMethod and prints some satus information.
         */
        public void run() {
             
            try {
                 
                System.out.println(id + 
                        " - about to get something from " + 
                        method.getURI());
                 
                // execute the method
                httpClient.executeMethod(method);
                 
                System.out.println(id + " - get executed");
                 
                // get the response body as an array of bytes
                byte[] bytes = method.getResponseBody();
                
                
                JSONParser parser = new JSONParser(); 
                JSONObject json = (JSONObject) parser.parse(new String(bytes));
                priceList.add(Double.parseDouble(json.get("LastPrice").toString()));
                
                
                System.out.println(id + " - " + bytes.length + 
                        " bytes read");
                 
            } catch (Exception e) {
                System.out.println(id + " - error: " + e);
            } finally {
                // always release the connection after we're done
                method.releaseConnection();
                System.out.println(id + " - connection released");
            }
        }
         
    }
     
}	