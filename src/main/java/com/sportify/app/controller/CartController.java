package com.sportify.app.controller;

import com.sportify.app.dto.response.ApiResponse;
import com.sportify.app.entity.Cart;
import com.sportify.app.exception.ResourceNotFoundException;
import com.sportify.app.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Paras Dongre
 * Date Created:30-01-2025
 * Time Created:12:40
 */
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCart(id);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id){

        try {
            cartService.clearCart(id);
            return ResponseEntity.ok(new ApiResponse("Cart Cleared", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));
        }
    }

    @GetMapping("/{id}/total-price")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long id){

        try {
            return ResponseEntity.ok(new ApiResponse("Total Price", cartService.getTotalPrice(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));
        }
    }
}
