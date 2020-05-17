package com.pnrpu.store.common;

import com.pnrpu.store.model.ShoppingCart;
import org.springframework.lang.NonNull;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpServletUtils {
    public static ShoppingCart getShoppingCartFromSession(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final ShoppingCart shoppingCartSession = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCartSession == null) {
            final ShoppingCart shoppingCart = new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);
            return shoppingCart;
        } else {
            return shoppingCartSession;
        }
    }

    public static String getCookieValueByName(final HttpServletRequest request, @NonNull final String cookieName) {
        for (final Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
