package com.xp.assignment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InventoryJsonRead {

	public InventoryJsonRead() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	  public Map<String, Inventory> readInventoryData() {
		 Map<String, Inventory> inventories = null;
	        File file = getCustomerFileReader.apply("inventory.json");
	        JSONParser parser = new JSONParser();
	        try (Reader is = new FileReader(file)) {
	            JSONArray jsonArray = (JSONArray) parser.parse(is);
	            inventories = (Map<String, Inventory>) jsonArray.stream().collect(
	                    Collectors.toMap(key_location, value_requestItems));
	 
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }
	        return inventories;
	  }
	 
	 @SuppressWarnings("unchecked")
	  public List<Inventory> readTransactionData() {
		 List<Inventory> tl = null;
	        File file = getCustomerFileReader.apply("transactions.json");
	        JSONParser parser = new JSONParser();
	        try (Reader is = new FileReader(file)) {
	            JSONArray jsonArray = (JSONArray) parser.parse(is);
	            tl = (List<Inventory>) jsonArray.stream().map(value_requestTransactions).collect(Collectors.toList());
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }
	        return tl;
	  }

	 
	 /**
	     * Read the JSON entry and return Inventory Id
	     */
	    private Function<JSONObject, String> key_location = c -> (String) ((JSONObject) c)
	            .get("location_id");
	 
	 
	 public Function<String, File> getCustomerFileReader = filename -> {
	        ClassLoader cl = getClass().getClassLoader();
	        File file = new File(cl.getResource(filename).getFile());
	        return file;
	    };
	    
	    /**
	     * Read the JSON entry and return the request items
	     */
	    @SuppressWarnings("unchecked")
	    private Function<JSONObject, Inventory> value_requestItems = json -> {
	        Inventory items = new Inventory();
	    	ArrayList<ItemData> iDataList = new ArrayList<ItemData>();
	        JSONArray dataJsonArray = (JSONArray) json.get("items");
	        Iterator<JSONObject> itr = dataJsonArray.iterator();
	        while (itr.hasNext()) {
	            final JSONObject data = itr.next();
	            ItemData idata = new ItemData();
	            idata.setItemName ((String) data.get("name"));
		        idata.setItemQty(Integer.parseInt(((String) data.get("quantity"))));
		        iDataList.add(idata);
		        idata = null;
		    }
	        items.setIDataList(iDataList);
	        return items;
	    };

	    
	    /**
	     * return the request transactions
	     */
	    @SuppressWarnings("unchecked")
	    private Function<JSONObject,Inventory> value_requestTransactions = json -> {
	    	Inventory t = new Inventory();
	        List<TransactionLog> ts = new ArrayList<TransactionLog>();
	        JSONArray dataJsonArray = (JSONArray) json.get("transactions");
	        Iterator<JSONObject> itr = dataJsonArray.iterator();
	        while (itr.hasNext()) {
	            final JSONObject data = itr.next();
		        TransactionLog tl = new TransactionLog();
	            tl.setFromLocation((String) data.get("from"));
	            tl.setToLocation((String) data.get("to"));
	            tl.setItemName((String) data.get("item"));
		        tl.setQuantity(Integer.parseInt(((String) data.get("quantity"))));
		        ts.add(tl);
		        tl = null;
		    }
	        t.setTransList(ts);
	        return t;
	    };

}
