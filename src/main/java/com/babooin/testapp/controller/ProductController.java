package com.babooin.testapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babooin.testapp.entity.Product;
import com.babooin.testapp.exception.ProductAlreadyExistsException;
import com.babooin.testapp.exception.ProductNotFoundException;
import com.babooin.testapp.service.ProductService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Value("${xmlProducts.upload.location}")
	private String uploadProductsLocation;
	
	@Value("${xmlProducts.save.location}")
	private String saveProductsLocation;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private XmlMapper xmlMapper;
	
	@GetMapping
	public List<Product> getProducts() {
		return productService.findAll();
	}
	
	@GetMapping("/{serial}")
	public Product getProduct(@PathVariable String serial) {
		Optional<Product> result = productService.findBySerial(serial);
		result.orElseThrow(() -> new ProductNotFoundException(serial));
		return result.get();
	}
	
	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		Optional<Product> exists = productService.findBySerial(product.getSerialNo());
		if (exists.isPresent())
			throw new ProductAlreadyExistsException(product.getSerialNo());
		productService.save(product);
		return product;
	}
	
	@PutMapping
	public Product saveOrUpdateProduct(@RequestBody Product product) {
		productService.saveOrUpdate(product);
		return product;
	}
	
	@DeleteMapping("/{serial}")
	public String deleteProduct(@PathVariable String serial) {
		productService.deleteBySerial(serial);
		return "Product deleted. Serial No.: " + serial;
	}
	
	@GetMapping("/list/save")
	public String saveToXml(HttpServletRequest req) {
		List<Product> products = productService.findAll();
		String path = saveProductsLocation.isEmpty() ? "productList.xml" : saveProductsLocation; 
		File productsXml = new File(path);
		try {
			xmlMapper.writeValue(productsXml, products);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Products saved to: " + productsXml.getAbsolutePath();
	}
	
	@PostMapping("/list/refresh")
	public List<Product> refreshProducts(HttpServletRequest req) {
		String defaultPath = req.getServletContext().getRealPath("/WEB-INF") + "/products.xml";
		String path = uploadProductsLocation.isEmpty() ? defaultPath : uploadProductsLocation;
		File productsXml = new File(path);
		List<Product> products = new ArrayList<>();
		try {
			products = xmlMapper.readerForListOf(Product.class).readValue(productsXml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		products.stream().forEach(e -> productService.saveOrUpdate(e));
		return products;
	}
	
}
