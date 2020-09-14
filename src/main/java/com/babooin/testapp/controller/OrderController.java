package com.babooin.testapp.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babooin.testapp.entity.Order;
import com.babooin.testapp.entity.OrderedItem;
import com.babooin.testapp.exception.OrderNotFoundException;
import com.babooin.testapp.exception.OrderedItemNotFoundException;
import com.babooin.testapp.service.OrderService;
import com.babooin.testapp.service.OrderedItemService;
import com.babooin.testapp.service.ProductService;
import com.babooin.ws.datetime.ServerDateTime;
import com.babooin.ws.datetime.ServerDateTimeService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderedItemService orderedItemService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Order> getOrders() {
		return orderService.findAll();
	}
	
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable long id) {
		Optional<Order> result = orderService.findById(id);
		return result.orElseThrow(() -> new OrderNotFoundException(id));
	}
	
	@PostMapping
	public Order addOrder(@RequestBody Order order) {
		order.setId(0);
		order.setOrderDate(getDate());
		orderService.save(order);
		return order;
	}
	
	@PutMapping
	public Order updateOrder(@RequestBody Order order) {
		orderService.saveOrUpdate(order);
		return order;
	}
	
	@DeleteMapping("/{id}")
	public String deleteOrder(@PathVariable long id) {
		Order order = getOrder(id);
		orderService.delete(order);
		return "Order deleted. Id: " + id;
	}
	
	@GetMapping("/{id}/items")
	public List<OrderedItem> getOrderedItems(@PathVariable long id) {
		Order order = getOrder(id);
		return order.getOrderedItems();
	}
	
	@GetMapping("/{id}/items/{itemId}")
	public OrderedItem getOrderedItem(@PathVariable long id, @PathVariable long itemId) {
		Optional<OrderedItem> result = orderedItemService.findByOrderIdAndItemId(id, itemId);
		return result.orElseThrow(() -> new OrderedItemNotFoundException(itemId));
	}
	
	@PostMapping("/{id}/items")
	public String addItem(@PathVariable long id, @RequestBody OrderedItem item) {
		Order order = getOrder(id);
		item.setProduct(productService.findBySerialOrThrow(item.getProduct().getSerialNo()));
		item.setOrder(order);
		order.addItem(item);
		updateOrder(order);
		return "Item '" + item.getProduct().getName() + "' added to the order number " + order.getId() + ".";
	}
	
	@PutMapping("/{id}/items")
	public String updateOrderedItem(@PathVariable long id, @RequestBody OrderedItem item) {
		Optional<OrderedItem> originalResult = Optional.ofNullable(null);
		if (item.isIdSet()) {
			originalResult = orderedItemService.findByOrderIdAndItemId(id, item.getId());
		} else if (item.getProduct() != null && item.getProduct().getSerialNo() != null) {
			originalResult = orderedItemService.findByOrderIdAndSerialNo(id, item.getProduct().getSerialNo());
			originalResult.ifPresent(e -> item.setId(e.getId()));
		} else {
			throw new RuntimeException("Bad request. You need to supply orderedItem id or product serialNo.");
		}
		OrderedItem original = null;
		if (item.isIdSet()) {
			original = originalResult.orElseThrow(() -> new OrderedItemNotFoundException());
		} else {
			original = originalResult.orElse(new OrderedItem());
			original.setOrder(getOrder(id));
		}
		if (item.getProduct() != null && item.getProduct().getSerialNo() != null)
			original.setProduct(productService.findBySerialOrThrow(item.getProduct().getSerialNo()));
		original.setQuantity(item.getQuantity());
		orderedItemService.saveOrUpdate(original);
		return "Item updated. Order id: " + id + ", Item: " + original.getProduct().getName();
	}
	
	@DeleteMapping("/{id}/items/{itemId}")
	public String deleteItem(@PathVariable long id, @PathVariable long itemId) {
		OrderedItem item = getOrderedItem(id, itemId);
		orderedItemService.delete(item);
		return "Deleted OrderedItem id: " + itemId;
	}
	
	private LocalDate getDate() {
		ServerDateTimeService wsdl = new ServerDateTimeService();	
		ServerDateTime serverDate = wsdl.getServerDateTime();
		Instant instant = serverDate.getServerDate().toGregorianCalendar().toInstant();
		LocalDate date = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
		return date;
	}

}
