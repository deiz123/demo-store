package com.pnrpu.store.controller;

import com.pnrpu.store.model.ShoppingCart;
import com.pnrpu.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.pnrpu.store.common.HttpServletUtils.getCookieValueByName;
import static com.pnrpu.store.common.HttpServletUtils.getShoppingCartFromSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String create(final HttpServletRequest request) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        final String city = getCookieValueByName(request, "city");

        orderService.createOrder(shoppingCart, city);
        shoppingCart.clear();

        return "redirect:/catalog";
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.OK)
    public void createPost(final HttpServletRequest request) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        final String city = getCookieValueByName(request, "city");

        orderService.createOrder(shoppingCart, city);
        shoppingCart.clear();
    }
}
