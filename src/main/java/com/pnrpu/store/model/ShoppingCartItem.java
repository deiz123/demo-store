package com.pnrpu.store.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class ShoppingCartItem {
    private final int phoneId;
    private final String model;
    private final int price;
    private final int count;
}
