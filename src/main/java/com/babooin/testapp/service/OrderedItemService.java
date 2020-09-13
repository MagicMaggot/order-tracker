package com.babooin.testapp.service;

import java.util.Optional;

import com.babooin.testapp.entity.OrderedItem;

public interface OrderedItemService extends CrudService<OrderedItem, Long> {
	
	Optional<OrderedItem> findByOrderIdAndItemId(long orderId, long itemId);

}
