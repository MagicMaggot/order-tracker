package com.babooin.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private OrderController orderController;
	
	@GetMapping("/home")
	public String getHomePage() {
		//System.out.println(productController.getProducts());
		return "home";
	}
	
	@GetMapping("/")
	public String getOrderList(Model m) {
		m.addAttribute("orders", orderController.getOrders());
		return "order-list";
	}
	
	

}
