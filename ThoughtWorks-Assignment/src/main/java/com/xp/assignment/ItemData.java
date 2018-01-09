package com.xp.assignment;

public class ItemData {

	String itemName;
	int itemQty;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public String toString() {
		return itemName + "#" + itemQty;
	}
}
