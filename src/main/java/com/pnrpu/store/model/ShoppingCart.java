package com.pnrpu.store.model;


import com.pnrpu.store.persistence.entity.Phone;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode
@Getter
@Setter
public class ShoppingCart {
    private Map<Phone, Integer> productMap = new HashMap<>();
    private int totalCount = 0;

    public void addProduct(final Phone phone) {
        if (productMap.isEmpty()) {
            final Map<Phone, Integer> products = new HashMap<>();
            products.put(phone, 1);
            productMap = products;
        } else {
            if (productMap.containsKey(phone)) {
                final int productCount = productMap.get(phone) + 1;
                productMap.put(phone, productCount);
            } else {
                productMap.put(phone, 1);
            }
        }
        totalCount = totalCount + 1;
    }

    public void removeFromCart(final Phone phone) {
        if (!productMap.isEmpty()) {
            if (productMap.containsKey(phone)) {
                final int productCount = productMap.get(phone) - 1;
                if (productCount < 1) {
                    productMap.remove(phone);
                } else {
                    productMap.put(phone, productCount);
                }
                totalCount = totalCount - 1;
            }
        }
    }

    public String getTotalCount() {
        return String.valueOf(totalCount);
    }

    public String getTotalSum() {
        final AtomicInteger sum = new AtomicInteger();
        if (totalCount > 0) {
            productMap.forEach((phone, count) -> sum.addAndGet(phone.getPrice() * count));
        }
        return String.valueOf(sum.get());
    }

    public boolean isEmpty() {
        return totalCount < 1;
    }

    public void clear() {
        productMap.clear();
        totalCount = 0;
    }

    public List<ShoppingCartItem> getCartItems() {
        final List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        productMap.forEach((phone, count) -> {
            shoppingCartItems.add(new ShoppingCartItem(phone.getId(), phone.getModel(), phone.getPrice(), count));
        });
        return shoppingCartItems;
    }
}
