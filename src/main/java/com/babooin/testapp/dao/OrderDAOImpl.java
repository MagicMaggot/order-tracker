package com.babooin.testapp.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.babooin.testapp.entity.Order;

@Repository
public class OrderDAOImpl implements OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Optional<Order> findById(Long id) {
		Order order = getSession().get(Order.class, id);
		return Optional.ofNullable(order);
	}

	@Override
	public List<Order> findAll() {
		return getSession().createQuery("from Order").getResultList();
	}

	@Override
	public void save(Order order) {
		getSession().persist(order);

	}

	@Override
	public void deleteById(Long id) {
		Optional<Order> result = findById(id);
		result.ifPresent(e -> delete(e));
	}
	
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void saveOrUpdate(Order order) {
		getSession().merge(order);
		
	}

	@Override
	public void delete(Order entity) {
		getSession().remove(entity);
		
	}


}
