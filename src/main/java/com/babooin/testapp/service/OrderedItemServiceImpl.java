package com.babooin.testapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babooin.testapp.dao.OrderedItemDAO;
import com.babooin.testapp.entity.OrderedItem;

@Service
public class OrderedItemServiceImpl implements OrderedItemService {
	
	@Autowired
	private OrderedItemDAO dao;

	@Override
	@Transactional
	public Optional<OrderedItem> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public List<OrderedItem> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public void save(OrderedItem orderDetails) {
		dao.save(orderDetails);
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void saveOrUpdate(OrderedItem entity) {
		dao.saveOrUpdate(entity);
		
	}

	@Override
	@Transactional
	public void delete(OrderedItem entity) {
		dao.delete(entity);
		
	}

	@Override
	@Transactional
	public Optional<OrderedItem> findByOrderIdAndItemId(long orderId, long itemId) {
		return dao.findByOrderIdAndItemId(orderId, itemId);
	}

	@Override
	@Transactional
	public Optional<OrderedItem> findByOrderIdAndSerialNo(long orderId, String serial) {
		return dao.findByOrderIdAndSerialNo(orderId, serial);
	}

}
