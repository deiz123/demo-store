package com.pnrpu.store.controller;

import com.pnrpu.store.model.PhoneIdRequest;
import com.pnrpu.store.model.ShoppingCart;
import com.pnrpu.store.model.ShoppingCartItem;
import com.pnrpu.store.persistence.entity.Phone;
import com.pnrpu.store.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.pnrpu.store.common.HttpServletUtils.getShoppingCartFromSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public String cart(
            final Model model,
            final HttpServletRequest request
    ) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);

        model.addAttribute("shoppingCart", shoppingCart);

        return "cart";
    }

    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.OK)
    public void add(
            @RequestBody final PhoneIdRequest phoneIdRequest,
            final HttpServletRequest request
    ) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        final Phone phone = phoneService.findById(phoneIdRequest.getPhoneId());
        if (phone != null) {
            shoppingCart.addProduct(phone);
        }
    }

    @PostMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(
            @RequestBody final PhoneIdRequest phoneIdRequest,
            final HttpServletRequest request
    ) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        final Phone phone = phoneService.findById(phoneIdRequest.getPhoneId());
        if (phone != null) {
            shoppingCart.removeFromCart(phone);
        }
    }

    @ResponseBody
    @PostMapping(value = "/size")
    public String size(
            final HttpServletRequest request
    ) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        return shoppingCart.getTotalCount();
    }

    @ResponseBody
    @GetMapping("/items")
    public List<ShoppingCartItem> items(
            final HttpServletRequest request
    ) {
        final ShoppingCart shoppingCart = getShoppingCartFromSession(request);
        return shoppingCart.getCartItems();
    }
}
