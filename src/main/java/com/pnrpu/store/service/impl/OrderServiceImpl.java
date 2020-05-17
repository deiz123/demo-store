package com.pnrpu.store.service.impl;

import com.pnrpu.store.model.ShoppingCart;
import com.pnrpu.store.persistence.entity.Order;
import com.pnrpu.store.persistence.entity.OrderItem;
import com.pnrpu.store.persistence.repository.OrderRepository;
import com.pnrpu.store.service.OrderItemService;
import com.pnrpu.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void createOrder(final ShoppingCart shoppingCart, final String city) {
        final Order order = new Order();
        order.setCity(city);
        final Order createdOrder = orderRepository.save(order);

        final List<OrderItem> orderItems = new ArrayList<>();
        shoppingCart.getProductMap().forEach((phone, count) -> {
            final OrderItem orderItem = new OrderItem();
            orderItem.setPhone(phone);
            orderItem.setCount(count);
            orderItem.setOrder(createdOrder);
            orderItems.add(orderItem);
        });
        orderItemService.saveAll(orderItems);
    }
}
