package com.sportify.app.service.cart;

import com.sportify.app.entity.Cart;

public interface CartService {

    Cart getCart(long id);
    void clearCart(long id);
    int getTotalPrice(long id);

    // Increment counter and return to initialize new Cart (In non-user case)
    Long initializeNewCart();

    Cart getCartByUserId(long id);
}
