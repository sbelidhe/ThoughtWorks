package com.xp.assignment;

import java.util.List;
import java.util.Map;

public class Inventory {

String locName;
Map<String,Integer> data;
private List<TransactionLog> transList;
List<ItemData> iDataList;

public String getLocName() {
	return locName;
}
public void setLocName(String locName) {
	this.locName = locName;
}
public Map<String, Integer> getData() {
	return data;
}
public void setData(Map<String, Integer> data) {
	this.data = data;
}

public String toString() {
	return locName + "=" + data;
}

public List<TransactionLog> getTransList() {
	return transList;
}

public void setTransList(List<TransactionLog> transList) {
	this.transList = transList;
}

public List<ItemData> getIDataList() {
    return iDataList;
}
public void setIDataList(List<ItemData> idata) {
    this.iDataList = idata;
}


}
