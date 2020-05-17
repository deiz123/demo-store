package com.pnrpu.store.service;

import com.pnrpu.store.persistence.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    void saveAll(List<OrderItem> orders);
}
