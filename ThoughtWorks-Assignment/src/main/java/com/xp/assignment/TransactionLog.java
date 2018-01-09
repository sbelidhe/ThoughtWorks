package com.xp.assignment;

public class TransactionLog {
	
	private String fromLocation;
	private String toLocation;
	private String itemName;
	private int quantity;

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return this.getFromLocation() + ":" + this.getToLocation() + ":"+ this.getItemName()+":"+this.getQuantity();
	}
}
