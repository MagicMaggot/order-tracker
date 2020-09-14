package com.babooin.testapp.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@Column(name = "serial_no")
	private String serialNo;
	
	private String name;
	
	private String description;
	
	@Column(name = "production_date")
	private String productionDate;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderedItem> orderedItems = new ArrayList<>();
	
	public Product() {
	}

	public Product(String serialNo, String name, String description, String date) {
		this.serialNo = serialNo;
		this.name = name;
		this.description = description;
		this.productionDate = date;
	}


	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}


	@Override
	public String toString() {
		return "Product [serialNo=" + serialNo + ", name=" + name + ", description=" + description + ", productionDate="
				+ productionDate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null || this.getClass() != obj.getClass())
			return false;

		Product otherItem = (Product) obj;

		return Objects.equals(serialNo, otherItem.serialNo) 
				&& Objects.equals(name, otherItem.name)
				&& Objects.equals(description, otherItem.description);
		
	}
	
	

}
