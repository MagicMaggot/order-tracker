package com.babooin.testapp.dao;

import java.util.List;
import java.util.Optional;

import com.babooin.testapp.entity.OrderedItem;

public interface OrderedItemDAO extends CrudRepository<OrderedItem, Long> {
	
	Optional<OrderedItem> findByOrderIdAndItemId(long orderId, long itemId);
}
