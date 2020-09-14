package com.babooin.testapp.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.babooin.testapp.entity.Product;
import com.babooin.testapp.exception.ProductNotFoundException;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Optional<Product> findById(String serial) {
		Product product = getSession().get(Product.class, serial.toUpperCase());
		return Optional.ofNullable(product);
	}

	@Override
	public List<Product> findAll() {
		return getSession().createQuery("from Product").getResultList();
	}

	@Override
	public void save(Product product) {
		getSession().persist(product);
	}
	
	@Override
	public void saveOrUpdate(Product product) {
		getSession().merge(product);
	}

	@Override
	public void deleteById(String serial) {
		throw new NotYetImplementedException();
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Optional<Product> findBySerial(String serial) {
		return findById(serial.toUpperCase());
	}

	@Override
	public void deleteBySerial(String serial) {
		throw new NotYetImplementedException();
	}

	@Override
	public void delete(Product entity) {
		throw new NotYetImplementedException();
	}

	@Override
	public Product findBySerialOrThrow(String serial) throws ProductNotFoundException {
		Optional<Product> result = findBySerial(serial.toUpperCase());
		return result.orElseThrow(() -> new ProductNotFoundException(serial));
		
	}

	
}
