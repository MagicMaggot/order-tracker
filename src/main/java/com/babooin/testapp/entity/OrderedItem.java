package com.babooin.testapp.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_details")
public class OrderedItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private long id;
	
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH  })
	@JoinColumn(name = "serial_no")
	private Product product;
	
	@Column(name = "qty")
	private int quantity;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;
	
	public OrderedItem() {
	}

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "OrderDetails [id=" + id + ", product=" + product + ", quantity=" + quantity + ", order=" + order.getId() + "]";
	}
	
	public boolean isIdSet() {
		return id > 0;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null || this.getClass() != obj.getClass())
			return false;

		OrderedItem otherItem = (OrderedItem) obj;

		return Objects.equals(id, otherItem.id) 
				&& Objects.equals(product, otherItem.product)
				&& Objects.equals(quantity, otherItem.quantity)
				&& Objects.equals(order.getId(), otherItem.order.getId());
		
	}
	
}
