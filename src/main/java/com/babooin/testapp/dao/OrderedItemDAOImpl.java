package com.babooin.testapp.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.babooin.testapp.entity.OrderedItem;

@Repository
public class OrderedItemDAOImpl implements OrderedItemDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Optional<OrderedItem> findById(Long id) {
		OrderedItem orderDetails = getSession().get(OrderedItem.class, id);
		return Optional.ofNullable(orderDetails);
	}

	@Override
	public List<OrderedItem> findAll() {
		return getSession().createQuery("from OrderDetails").getResultList();
	}

	@Override
	public void save(OrderedItem orderDetails) {
		getSession().persist(orderDetails);
		
	}
	
	@Override
	public void saveOrUpdate(OrderedItem orderDetails) {
		getSession().merge(orderDetails);
		
	}

	@Override
	public void deleteById(Long id) {
		Optional<OrderedItem> result = findById(id);
		result.ifPresent(e -> delete(e));
		
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void delete(OrderedItem entity) {
		getSession().remove(entity);
		
	}

	@Override
	public Optional<OrderedItem> findByOrderIdAndItemId(long orderId, long itemId) {
		Query<OrderedItem> query = getSession().createQuery("from OrderedItem o where o.order.id = :orderId and o.id = :itemId");
		query.setParameter("orderId", orderId);
		query.setParameter("itemId", itemId);
		return executeQueryFindOne(query);
	}

	@Override
	public Optional<OrderedItem> findByOrderIdAndSerialNo(long orderId, String serial) {
		Query<OrderedItem> query = getSession().createQuery("from OrderedItem o where o.order.id = :orderId and o.product.serialNo = :serial");
		query.setParameter("orderId", orderId);
		query.setParameter("serial", serial);
		return executeQueryFindOne(query);
	}
	
	private Optional<OrderedItem> executeQueryFindOne(Query<OrderedItem> query) {
		List<OrderedItem> item = query.getResultList();
		OrderedItem result = null;
		if (item.size() > 0)
			result = item.get(0);
		return Optional.ofNullable(result);
	}


}
