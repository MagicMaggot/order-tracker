package com.babooin.testapp.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.babooin.testapp.entity.Order;
import com.babooin.testapp.entity.OrderedItem;
import com.babooin.testapp.model.OrderedItemForm;
import com.babooin.testapp.service.OrderedItemService;

@Controller
public class FrontController {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private OrderController orderController;
	
	@Autowired
	private OrderedItemService orderedItemService;
	
	@ModelAttribute("serverDate")
	public LocalDate getDate() {
		return orderController.getServerDate();
	}
	
	@GetMapping("/")
	public String getOrderList(Model m) {
		m.addAttribute("orders", orderController.getOrders());
		return "order-list";
	}
	
	@GetMapping("/orderDetails")
	public String getOrderDetails(@RequestParam long id, Model m) {
		m.addAttribute("order", orderController.getOrder(id));
		return "order-details";
		
	}
	
	@GetMapping("/order/deleteItem")
	public RedirectView deleteOrderedItem(@RequestParam("orderId") long id, @RequestParam long itemId, RedirectAttributes attributes) {
		orderController.deleteItem(id, itemId);
		attributes.addAttribute("id", id);
		return new RedirectView("/orderDetails", true);
	}
	
	@GetMapping("/order/addItem")
	public String addItem(@RequestParam("orderId") long id, Model m) {
		m.addAttribute("order", orderController.getOrder(id));
		m.addAttribute("products", productController.getProducts());
		m.addAttribute("addItemForm", new OrderedItemForm());
		return "product-list";
	}
	
	@GetMapping("/deleteOrder")
	public String deleteOrder(@RequestParam long id) {
		orderController.deleteOrder(id);
		return "redirect:/";
	}
	
	@PostMapping("/order/addItem")
	public RedirectView addItem(@ModelAttribute OrderedItemForm form, RedirectAttributes attributes) {
		long orderId = form.getOrderId();
		Order order = orderController.getOrder(orderId);
		OrderedItem item = new OrderedItem();
		item.setOrder(order);
		item.setQuantity(form.getQuantity());
		item.setProduct(productController.getProduct(form.getSerialNo()));
		if ( form.getQuantity() > 0) 
			orderController.updateOrderedItem(orderId, item);
		else {
			orderedItemService.findByOrderIdAndSerialNo(orderId, form.getSerialNo())
					.ifPresent(e -> orderController.deleteItem(orderId, e.getId()));
		}
		attributes.addAttribute("id", form.getOrderId());
		return new RedirectView("/orderDetails", true);
	}
	
	@GetMapping("/products/list")
	public String getProductList(Model m) {
		m.addAttribute("products", productController.getProducts());
		return "product-list";
	}
	
	@GetMapping("/orders/addForm")
	public String showAddForm(Model m) {
		m.addAttribute("order", new Order());
		return "add-order";
	}
	
	@PostMapping("/orders/addOrder")
	public String addOrder(@ModelAttribute Order order) {
		orderController.addOrder(order);
		return "redirect:/";
	}
	
	@GetMapping("/products/refresh")
	public RedirectView refreshProductList(@RequestParam(required = false) Long orderId, 
			HttpServletRequest req, RedirectAttributes attributes) {
		productController.refreshProducts(req);
		if (orderId != null) {
			attributes.addAttribute("orderId", orderId);
			return new RedirectView("/order/addItem", true);
		}
		return new RedirectView("/products/list", true);
			
	}

}
