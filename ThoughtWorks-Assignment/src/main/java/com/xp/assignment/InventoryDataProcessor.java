package com.xp.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class InventoryDataProcessor {

	private final List<Inventory> invData ;
	private final Inventory inventory = new Inventory();
	private static InventoryDataProcessor dataProcessor = new InventoryDataProcessor();

	 
	public InventoryDataProcessor() {
		this(Collections.emptyList());
	}

	 private InventoryDataProcessor(List<Inventory> delegate) {
	        this.invData = Collections.unmodifiableList(delegate);
	 }
	 
	 
	 
	 public List<Inventory> getInvData() {
		 List<Inventory> newInvData = new ArrayList<Inventory>(invData);
	        return newInvData;
	 }
	 
	 public InventoryDataProcessor add(String loc,String item,Integer quantity) {
		 List<Inventory> newInvData = new ArrayList<Inventory>(invData);
		 List<Inventory> list = newInvData.stream()
	               .filter(l -> l.getLocName().equals(loc))
	               .collect(Collectors.toList());
		 if (!list.isEmpty()) {
	               list.stream().findAny()
	               .ifPresent(l -> l.getData().put(item, quantity));
		 }else {
			 inventory.setLocName(loc);
			 Map<String,Integer> x = new HashMap<String,Integer>();
			 x.put(item, quantity);
			 inventory.setData(x);
			 x = null;
			 newInvData.add(inventory);
		 }
	        return new InventoryDataProcessor(newInvData);
	  }
	 
	     
	    
	public InventoryDataProcessor applyTransanctions(String fromLoc, String toLoc,
			String Item, int quantity) {
		List<Inventory> newInvData = new ArrayList<Inventory>(invData);

		newInvData
				.stream()
				.filter(l -> l.getLocName().equals(fromLoc))
				.findAny()
				.ifPresent(
						l -> l.getData().put(Item,
								l.getData().get(Item).intValue() - quantity));

		newInvData
				.stream()
				.filter(l -> l.getLocName().equals(toLoc))
				.findAny()
				.ifPresent(
						l -> l.getData().put(Item,
								l.getData().get(Item).intValue() + quantity));

		return new InventoryDataProcessor(newInvData);
	}	 
	
    private Consumer<Map.Entry<String, Inventory>> populateData = inventory -> {
        final Inventory items = inventory.getValue();
        final List<ItemData> idataList = items.getIDataList();
        idataList.stream().forEach(idata -> {
        	dataProcessor = dataProcessor.add(inventory.getKey(),idata.getItemName(),idata.getItemQty());
        });
    };

    
    private void populateTransData(List<Inventory> data) {
        data.stream().forEach(i -> {
        	i.getTransList().stream().forEach(x -> {
        		dataProcessor = dataProcessor.applyTransanctions(x.getFromLocation(), x.getToLocation(), x.getItemName(), x.getQuantity());
        	  });
        	});
    }

	 
	public static void main(String[] args) {
		InventoryJsonRead inventoryJsonRead = new InventoryJsonRead();
		Map<String, Inventory> items = inventoryJsonRead.readInventoryData();
		items.entrySet().stream().forEach(dataProcessor.populateData);
		System.out.println(dataProcessor.getInvData());
		List<Inventory> tl = inventoryJsonRead.readTransactionData();
		dataProcessor.populateTransData(tl);
		System.out.println(dataProcessor.getInvData());

	}

}
