package com.pnrpu.store.service;

import com.pnrpu.store.model.ShoppingCart;

public interface OrderService {
    void createOrder(ShoppingCart shoppingCart, String city);
}
