package com.babooin.testapp.model;

public class OrderedItemForm {
	
	private String serialNo;
	private long orderId;
	private int quantity;
	
	public OrderedItemForm() {
	}
	
	public OrderedItemForm(int quantity) {
		this.quantity = quantity;
	}
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serial) {
		this.serialNo = serial;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderedItemForm [serialNo=" + serialNo + ", orderId=" + orderId + ", quantity=" + quantity + "]";
	}
	
	
	
}
