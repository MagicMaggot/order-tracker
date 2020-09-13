package com.babooin.testapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_address")
	private String customerAddress;
	
	private double total;
	
	@Column(name = "placed")
	//private String orderDate;
	private LocalDate orderDate;
	
	@JsonProperty("orderedItem")
	@JacksonXmlElementWrapper(localName =  "orderedItems")
	@OneToMany(mappedBy = "order" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderedItem> orderedItems = new ArrayList<>();
	
	public Order() {
	}

	public Order(String customerName, String customerAddress, double total, LocalDate orderDate) {
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.total = total;
		this.orderDate = orderDate;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	public void addItem(OrderedItem item) {
		item.setOrder(this);
		getOrderedItems().add(item);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", customerAddress=" + customerAddress
				+ ", total=" + total + ", orderDate=" + orderDate + ", orderedItems=" + orderedItems + "]";
	}
	
	
}
