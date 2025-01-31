package com.sportify.app.controller;

import com.sportify.app.dto.response.ApiResponse;
import com.sportify.app.exception.ResourceNotFoundException;
import com.sportify.app.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Paras Dongre
 * Date Created:31-01-2025
 * Time Created:00:11
 */
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderByOrderId(@PathVariable long orderId){

        try {
            return ResponseEntity.ok(new ApiResponse("Success", orderService.getOrder(orderId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable long userId){

        try {
            return ResponseEntity.ok(new ApiResponse("Success", orderService.getOrdersByUser(userId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> placeOrderForUser(@PathVariable long userId){

        try {
            return ResponseEntity.ok(new ApiResponse("Success", orderService.placeOrder(userId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
