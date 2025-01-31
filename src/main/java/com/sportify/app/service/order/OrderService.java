package com.sportify.app.service.order;

import com.sportify.app.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(long userId);
    Order getOrder(long orderId);

    List<Order> getOrdersByUser(long userId);
}
