package com.pnrpu.store.service.impl;

import com.pnrpu.store.persistence.entity.OrderItem;
import com.pnrpu.store.persistence.repository.OrderItemRepository;
import com.pnrpu.store.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void saveAll(final List<OrderItem> orders) {
        orderItemRepository.saveAll(orders);
    }
}
